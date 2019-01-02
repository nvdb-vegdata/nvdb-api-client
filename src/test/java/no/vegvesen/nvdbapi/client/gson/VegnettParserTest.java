package no.vegvesen.nvdbapi.client.gson;

import no.vegvesen.nvdbapi.client.model.roadnet.LinkSequence;
import no.vegvesen.nvdbapi.client.model.roadnet.Node;
import no.vegvesen.nvdbapi.client.model.roadnet.SegmentedLink;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static no.vegvesen.nvdbapi.client.gson.Helper.parseObjekterList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class VegnettParserTest {
    @Test
    void parseNodes() throws IOException {
        List<Node> nodes = parseObjekterList("vegnett/noder.json", RoadNetParser::parseNode);
        assertThat(nodes.size(), is(not(0)));
    }

    @Test
    void parseLinksequences() throws IOException {
        List<LinkSequence> linkSequences = parseObjekterList("vegnett/veglenkesekvenser.json", RoadNetParser::parseLinkSequence);
        assertThat(linkSequences.size(), is(not(0)));
    }

    @Test
    void parseSegmentedLinksequences() throws IOException {
        List<SegmentedLink> segmentedLinks = parseObjekterList("vegnett/veglenkesekvenser_segmentert.json", SegmentedLinkParser::parse);
        assertThat(segmentedLinks.size(), is(not(0)));
    }
}
