package no.vegvesen.nvdbapi.client.clients;

import no.vegvesen.nvdbapi.client.util.Stopwatch;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class TransactionsClientTest {

    @Test
    @Disabled("manual test")
    void downloadTransacyions() {
        Stopwatch started = Stopwatch.createStarted();
        ClientFactory clientFactory = new ClientFactory("https://nvdbapiles-v3.atlas.vegvesen.no",
                "nvdbapi-client-test");
        TransactionsClient roadNetService = clientFactory.getTransactionsClient();

        TransactionsClient.AsyncTransacionsResult result = roadNetService
                .getTransactionsAsync(TransactionsRequest.DEFAULT);
        result.get()
                .toStream()
                .forEach(element -> {
                    System.out.println(element.getTransactionId());
                });
        Stopwatch stop = started.stop();
        System.out.println(stop.elapsedMillis());
    }
}
