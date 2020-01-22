package no.vegvesen.nvdbapi.client.gson;

import static no.vegvesen.nvdbapi.client.gson.Helper.parseList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import no.vegvesen.nvdbapi.client.model.roadnet.RouteSegment;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;


public class RouteParserTest {
    @Test
    void parseRouteSegmentsLongFormat() throws IOException {
        List<RouteSegment> routeSegments = parseList("rute/rute_langtformat.json", RouteSegmentParser::parse);
        assertThat(routeSegments.size(), is(2));
    }

    @Test
    void parseRouteSegmentsShortFormat() throws IOException {

    }

}
