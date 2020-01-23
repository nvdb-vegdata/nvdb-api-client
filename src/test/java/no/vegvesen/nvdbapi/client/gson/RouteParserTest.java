package no.vegvesen.nvdbapi.client.gson;

import static no.vegvesen.nvdbapi.client.gson.Helper.parseList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import no.vegvesen.nvdbapi.client.model.roadnet.BriefRouteSegment;
import no.vegvesen.nvdbapi.client.model.roadnet.DetailedRouteSegment;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;


public class RouteParserTest {
    @Test
    void parseDeatiledRouteSegments() throws IOException {
        List<DetailedRouteSegment> detailedRouteSegments = parseList("rute/rute_langtformat.json", DetailedRouteSegmentParser::parse);
        assertThat(detailedRouteSegments.size(), is(2));
    }

    @Test
    void parseBriefRouteSegments() throws IOException {
        List<BriefRouteSegment> routeSegmentDetaileds = parseList("rute/rute_kortformat.json", BriefRouteSegmentParser::parse);
        assertThat(routeSegmentDetaileds.size(), is(2));
    }

}
