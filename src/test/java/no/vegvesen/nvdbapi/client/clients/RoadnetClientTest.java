package no.vegvesen.nvdbapi.client.clients;

import no.vegvesen.nvdbapi.client.util.Stopwatch;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class RoadnetClientTest {

    @Test
    @Disabled("manual test")
    void downloadSegmentedRoadnet() {
        Stopwatch started = Stopwatch.createStarted();

        ClientFactory clientFactory = new ClientFactory("https://nvdbapiles-v3.atlas.vegvesen.no",
             "nvdbapi-client-test");
        SegmentedRoadNetClient segmentedRoadNetService = clientFactory.getSegmentedRoadNetService();

        SegmentedRoadNetClient.AsyncSegmentedLinkResult result = segmentedRoadNetService.getLinksAsync(
            RoadNetRequest
                .newBuilder()
                .withHistory(true)
                .build());
        result.get()
            .toStream()
            .forEach(element -> {
                System.out.println(element.getId());
            });
        Stopwatch stop = started.stop();
        System.out.println(stop.elapsedMillis());
    }
}
