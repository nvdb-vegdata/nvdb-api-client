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

        RoadNetClient.AsyncLinkResult result = roadNetService.getLinkSequencesSync(
                RoadNetRequest
                        .newBuilder()
                        .build());
        result.get()
                .toStream()
                .forEach(linksequence -> System.out.println(linksequence.getId()));
        System.out.println(started.elapsedMillis());
    }
}
