package no.vegvesen.nvdbapi.client.gson;

import no.vegvesen.nvdbapi.client.model.Position;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static no.vegvesen.nvdbapi.client.gson.Helper.parseObject;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class PosisjonParserTest {
    @Test
    void parsePosition() throws IOException {
        Position.Result result = parseObject("posisjon/posisjon.json", PlacementParser::parsePosition);
        assertThat(result.getRoadRef(), is(notNullValue()));
    }

}
