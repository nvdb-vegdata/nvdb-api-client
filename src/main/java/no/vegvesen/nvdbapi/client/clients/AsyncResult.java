package no.vegvesen.nvdbapi.client.clients;

import com.google.gson.*;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import no.vegvesen.nvdbapi.client.exceptions.ClientException;
import no.vegvesen.nvdbapi.client.gson.GsonUtil;
import no.vegvesen.nvdbapi.client.model.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

import static no.vegvesen.nvdbapi.client.clients.GenericResultSet.applyPage;

public class AsyncResult<T> {
    private static final Logger logger = LoggerFactory.getLogger(GenericResultSet.class);

    private final WebTarget baseTarget;
    private final Function<JsonObject, T> parser;
    private final Page page;
    private final ExecutorService executorService;
    private final Gson gson;

    public AsyncResult(WebTarget baseTarget,
                       Page page,
                       Function<JsonObject, T> parser) {
        this.baseTarget = baseTarget;
        this.parser = parser;
        this.page = page;
        executorService = Executors.newSingleThreadExecutor();
        gson = new Gson();
    }

    public Flux<T> get() {
        return Flux.create(sink -> executorService.execute(() -> {
            try {
                PagingIndicator pagingIndicator = doPage(sink, page);
                while (pagingIndicator.hasNext) {
                    pagingIndicator = doPage(sink, pagingIndicator.currentPage);
                }
            } catch (Exception e) {
                sink.error(e);
            } finally {
                sink.complete();
                executorService.shutdown();
            }
        }));
    }

    private PagingIndicator doPage(FluxSink<T> sink, Page currentPage) throws IOException {
        WebTarget actualTarget = applyPage(currentPage, baseTarget);

        logger.debug("Invoking {}", actualTarget.getUri());
        Invocation inv = actualTarget.request()
                .accept(JerseyHelper.MEDIA_TYPE)
                .buildGet();
        try(Response response = JerseyHelper.execute(inv, Response.class)) {

            if (!JerseyHelper.isSuccess(response)) {
                sink.error(JerseyHelper.parseError(response));
                return new PagingIndicator(false, currentPage);
            }
            String requestId = response.getHeaderString("X-REQUEST-ID");

            try(JsonReader reader = gson.newJsonReader(
                    new InputStreamReader(
                            new BufferedInputStream(
                            (InputStream) response.getEntity()), StandardCharsets.UTF_8))) {
                reader.beginObject();
                reader.nextName();
                reader.beginArray();
                while (reader.hasNext()) {
                    sink.next(
                            parser.apply(
                                    Streams.parse(reader).getAsJsonObject()));
                }
                reader.endArray();
                reader.nextName();
                JsonObject metadata = Streams.parse(reader).getAsJsonObject();
                String nextToken = GsonUtil.getNode(metadata, "neste.start")
                        .map(JsonElement::getAsString)
                        .orElse(null);
                String token = currentPage.getStart().orElse(null);
                logger.debug("last token: {} next token: {}", token, nextToken);
                // no next page if last token and next token are equal
                boolean hasNext = nextToken != null && (!nextToken.equals(token));
                reader.endObject();

                /*
                 Not use why reader.skipValue() is needed.
                 If it's left out MalformedChunkCodingException: CRLF expected at end of chunk
                 some times occurs.
                 https://stackoverflow.com/questions/8635112/java-malformedchunkcodingexception
                 */
                reader.skipValue();
                return new PagingIndicator(hasNext, currentPage.withStart(nextToken));
            } catch (Exception e) {
                throw new ClientException(response.getStatus(), requestId, Collections.emptyList(), e);
            }
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
