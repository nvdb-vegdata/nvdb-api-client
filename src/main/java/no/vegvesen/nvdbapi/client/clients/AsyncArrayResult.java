package no.vegvesen.nvdbapi.client.clients;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import no.vegvesen.nvdbapi.client.exceptions.ClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

public class AsyncArrayResult<T> {
    private static final Logger logger = LoggerFactory.getLogger(GenericResultSet.class);

    private final WebTarget baseTarget;
    private final Function<JsonObject, T> parser;
    private final ExecutorService executorService;
    private final Gson gson;

    public AsyncArrayResult(WebTarget baseTarget,
                            Function<JsonObject, T> parser) {
        this.baseTarget = baseTarget;
        this.parser = parser;
        executorService = Executors.newSingleThreadExecutor();
        gson = new Gson();
    }

    public Flux<T> get() {
        return Flux.create(sink -> executorService.execute(() -> {
            try {
                doPage(sink);
            } catch (Exception e) {
                sink.error(e);
            } finally {
                sink.complete();
                executorService.shutdown();
            }
        }));
    }

    private void doPage(FluxSink<T> sink) throws IOException {
        WebTarget actualTarget = baseTarget;

        logger.debug("Invoking {}", actualTarget.getUri());
        Invocation inv = actualTarget.request()
            .accept(JerseyHelper.MEDIA_TYPE)
            .buildGet();
        try(Response response = JerseyHelper.execute(inv, Response.class)) {

            if (!JerseyHelper.isSuccess(response)) {
                sink.error(JerseyHelper.parseError(response));
            }
            String requestId = response.getHeaderString("X-REQUEST-ID");

            try(JsonReader reader = gson.newJsonReader(
                new InputStreamReader(
                    new BufferedInputStream(
                        (InputStream) response.getEntity()), StandardCharsets.UTF_8))) {
                reader.beginArray();
                while (reader.hasNext()) {
                    sink.next(
                        parser.apply(
                            Streams.parse(reader).getAsJsonObject()));
                }
                reader.endArray();

                /*
                 Not sure why reader.skipValue() is needed.
                 If it's left out MalformedChunkCodingException: CRLF expected at end of chunk
                 some times occurs.
                 https://stackoverflow.com/questions/8635112/java-malformedchunkcodingexception
                 */
                reader.skipValue();
            } catch (Exception e) {
                throw new ClientException(response.getStatus(), requestId, Collections.emptyList(), e);
            }
        }
    }
}
