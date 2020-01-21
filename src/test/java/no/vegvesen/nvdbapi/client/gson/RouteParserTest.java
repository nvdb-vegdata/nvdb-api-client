package no.vegvesen.nvdbapi.client.gson;

import no.vegvesen.nvdbapi.client.model.RouteOnRoadNet;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static no.vegvesen.nvdbapi.client.gson.Helper.parseObject;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class RouteParserTest {
    @Test
    void parsePosition() throws IOException {
        RouteOnRoadNet result = parseObject("rute/rute_langtformat.json", RouteParser::parseRoute);
        assertThat(result.getSegments(), is(notNullValue()));
    }

}
