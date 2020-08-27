package no.vegvesen.nvdbapi.client.gson;

import no.vegvesen.nvdbapi.client.model.roadnet.route.RouteOnRoadNet;
import no.vegvesen.nvdbapi.client.model.roadnet.route.RouteSegment;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static no.vegvesen.nvdbapi.client.gson.Helper.parseObject;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class RouteParserTest {
    @Test
    void parseDeatiledRouteSegments() throws IOException {
        RouteOnRoadNet route = parseObject("rute/rute_langtformat.json", RouteParser::parseDetailed);
        List<RouteSegment> detailedRouteSegments = route.getSegments();
        assertThat(detailedRouteSegments.size(), is(2));
    }

    @Test
    void parseBriefRouteSegments() throws IOException {
        RouteOnRoadNet route = parseObject("rute/rute_kortformat.json", RouteParser::parseBrief);
        List<RouteSegment> routeSegmentDetaileds = route.getSegments();
        assertThat(routeSegmentDetaileds.size(), is(2));
    }

}
