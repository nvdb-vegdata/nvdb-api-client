package no.vegvesen.nvdbapi.client.gson;

import no.vegvesen.nvdbapi.client.model.Status;
import org.junit.jupiter.api.Test;

import static no.vegvesen.nvdbapi.client.gson.Helper.parseObject;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class StatusParserTest {

    @Test
    void parseStatus() {
        Status status = parseObject("status.json", StatusParser::parseStatus);
        assertThat(status, is(notNullValue()));
    }
}
