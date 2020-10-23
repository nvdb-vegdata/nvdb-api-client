package no.vegvesen.nvdbapi.client.gson;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

import static no.vegvesen.nvdbapi.client.gson.Helper.parsePlainList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

class AreaParserTest {
    @Test
    void parseFylker() throws IOException {
        doParse("omrader/fylker.json", AreaParser::parseCounty);
    }

    @Test
    void parseKommuner() throws IOException {
        doParse("omrader/kommuner.json", AreaParser::parseMun);
    }

    @Test
    void parseKontraktsomrader() throws IOException {
        doParse("omrader/kontraktsomrader.json", AreaParser::parseContractArea);
    }

    @Test
    void parseRiksvegruter() throws IOException {
        doParse("omrader/riksvegruter.json", AreaParser::parseRoute);
    }

    @Test
    void parseGater() throws IOException {
        doParse("omrader/gater.json", AreaParser::parseStreet);
    }

    private <T> void doParse(String file, Function<JsonObject, T> mapper) throws IOException {
        List<T> list = parsePlainList(file, mapper);
        long count = list.size();
        assertThat(count, is(not(0)));
    }

}

