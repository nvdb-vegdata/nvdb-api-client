package no.vegvesen.nvdbapi.client.clients;

import no.vegvesen.nvdbapi.client.util.Stopwatch;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@SuppressWarnings("WeakerAccess")
public class RoadobjectClientTest {

    @Test
    @Disabled("manual test")
    void downloadAll() {
        Stopwatch started = Stopwatch.createStarted();
        ClientFactory clientFactory = new ClientFactory("https://nvdbapiles-v3.atlas.vegvesen.no",
                 "nvdbapi-client-test");
        RoadObjectClient roadNetService = clientFactory.getRoadObjectClient();

        RoadObjectClient.AsyncRoadObjectsResult result = roadNetService.getRoadObjectsAsync(
                105,
                RoadObjectRequest
                        .newBuilder()
                        .includeAll()
                        .withAllVersions(Boolean.TRUE)
                        .build());
        result.get()
                .toStream()
                .forEach(element -> {
                        System.out.println("Object: " + element.getId() + " " + element.getVersion());
                });
        Stopwatch stop = started.stop();
        System.out.println(stop.elapsedMillis());
    }
}
