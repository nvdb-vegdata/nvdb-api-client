package no.vegvesen.nvdbapi.client.clients;

import no.vegvesen.nvdbapi.client.util.Stopwatch;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class RoadnetClientTest {

    @Test
    @Disabled("manual test")
    void downloadRoadnet() {
        Stopwatch started = Stopwatch.createStarted();
        ClientFactory clientFactory = new ClientFactory("https://nvdbw01.kantega.no/nvdb/api/v3",
                "Jersey", "nvdbapi-client-test");
        RoadNetClient roadNetService = clientFactory.createRoadNetService();

        RoadNetClient.AsyncNetElementResult result = roadNetService.getNetElementsAsynk(
                RoadNetRequest
                        .newBuilder()
                        .build());
        result.get()
                .toStream()
                .forEach(element -> {
                    if(element.isLink()) {
                        System.out.println("Linksequence: " + element.link().getId());
                    } else {
                        System.out.println("Node: " + element.node().getId());
                    }
                });
        Stopwatch stop = started.stop();
        System.out.println(stop.elapsedMillis());
    }

    @Test
    @Disabled("manual test")
    void downloadSegmentedRoadnet() {
        Stopwatch started = Stopwatch.createStarted();
        ClientFactory clientFactory = new ClientFactory("https://nvdbw01.kantega.no/nvdb/api/v3",
            "Jersey", "nvdbapi-client-test");
        SegmentedRoadNetClient segmentedRoadNetService = clientFactory.createSegmentedRoadNetService();

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
