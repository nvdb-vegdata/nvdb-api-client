package no.vegvesen.nvdbapi.client.clients;

import no.vegvesen.nvdbapi.client.util.Stopwatch;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class RoadobjectClientTest {

    @Test
    @Disabled("manual test")
    void downloadAll() {
        Stopwatch started = Stopwatch.createStarted();
        ClientFactory clientFactory = new ClientFactory("https://nvdbw01.kantega.no/nvdb/api/v3",
                 "nvdbapi-client-test");
        RoadObjectClient roadNetService = clientFactory.createRoadObjectClient();

        RoadObjectClient.AsyncRoadObjectsResult result = roadNetService.getRoadObjectsAsync(
                105,
                RoadObjectRequest
                        .newBuilder()
                        .includeAll()
                        .withAllVersions(true)
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
