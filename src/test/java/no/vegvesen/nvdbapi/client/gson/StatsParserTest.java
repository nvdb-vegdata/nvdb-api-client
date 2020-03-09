package no.vegvesen.nvdbapi.client.gson;

import no.vegvesen.nvdbapi.client.model.roadobjects.Statistics;
import org.junit.jupiter.api.Test;

import static no.vegvesen.nvdbapi.client.gson.Helper.parseObject;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StatsParserTest {

    @Test
    void parseStats() throws Exception {
        Statistics statistics = parseObject("vegobjekter/105_statistikk.json", RoadObjectParser::parseStatistics);
        assertNotNull(statistics);
    }
}
