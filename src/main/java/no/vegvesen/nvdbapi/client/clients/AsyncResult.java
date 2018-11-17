package no.vegvesen.nvdbapi.client.clients;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import no.vegvesen.nvdbapi.client.clients.util.JerseyHelper;
import no.vegvesen.nvdbapi.client.gson.GsonUtil;
import no.vegvesen.nvdbapi.client.model.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.StreamSupport;

import static no.vegvesen.nvdbapi.client.clients.GenericResultSet.applyPage;

public class AsyncResult<T> {
    private static final Logger logger = LoggerFactory.getLogger(GenericResultSet.class);

    private final WebTarget baseTarget;
    private final Function<JsonObject, T> parser;
    private final Page page;
    private final ExecutorService executorService;

    public AsyncResult(WebTarget baseTarget,
                       Page page,
                       Function<JsonObject, T> parser) {
        this.baseTarget = baseTarget;
        this.parser = parser;
        this.page = page;
        executorService = Executors.newSingleThreadExecutor();
    }

    public Flux<T> get() {
        return Flux.create(sink -> {
            executorService.execute(() -> {
                try {
                    PagingIndicator pagingIndicator = doPage(sink, page);
                    while (pagingIndicator.hasNext) {
                        pagingIndicator = doPage(sink, pagingIndicator.currentPage);
                    }
                } catch (Exception e) {
                    sink.error(e);
                } finally {
                    sink.complete();
                }
            });
        });
    }

    private PagingIndicator doPage(FluxSink<T> fluxSink, Page currentPage) {
        WebTarget actualTarget = applyPage(currentPage, baseTarget);

        logger.debug("Invoking {}", actualTarget.getUri());
        Invocation inv = actualTarget.request()
                                     .accept(JerseyHelper.MEDIA_TYPE)
                                     .buildGet();
        try(Response response = JerseyHelper.execute(inv, Response.class)) {

            if (!JerseyHelper.isSuccess(response)) {
                fluxSink.error(JerseyHelper.parseError(response));
            }

            JsonObject currentResponse =
                    new JsonParser()
                            .parse(new InputStreamReader((InputStream) response.getEntity()))
                            .getAsJsonObject();
            StreamSupport
                    .stream(currentResponse.getAsJsonArray("objekter").spliterator(), false)
                    .map(JsonElement::getAsJsonObject)
                    .map(parser)
                    .forEach(fluxSink::next);
            int numTotal = GsonUtil.parseIntMember(currentResponse, "metadata.antall");
            int numReturned = GsonUtil.parseIntMember(currentResponse, "metadata.returnert");
            logger.debug("Result size returned was {}.", numTotal);
            logger.debug("Page size returned was {}.", numReturned);

            if (logger.isTraceEnabled()) {
                logger.trace("Response: {}", currentResponse.toString());
            }

            // Prepare next request
            String nextToken = GsonUtil.getNode(currentResponse, "metadata.neste.start")
                    .map(JsonElement::getAsString)
                    .orElse(null);
            String token = currentPage.getStart().orElse(null);
            logger.debug("last token: {} next token: {}", token, nextToken);
            // no next page if last token and next token are equal
            boolean hasNext = nextToken != null && (!nextToken.equals(token));
            return new PagingIndicator(hasNext, currentPage.withStart(nextToken));
        }
    }

    private static class PagingIndicator {
        final boolean hasNext;
        final Page currentPage;

        PagingIndicator(boolean hasNext, Page currentPage) {
            this.hasNext = hasNext;
            this.currentPage = currentPage;
        }
    }
}
