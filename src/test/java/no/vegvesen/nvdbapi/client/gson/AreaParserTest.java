package no.vegvesen.nvdbapi.client.gson;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Function;

import static no.vegvesen.nvdbapi.client.gson.Helper.parseList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

class AreaParserTest {
    @Test
    void parseFylker() {
        doParse("omrader/fylker.json", AreaParser::parseCounty);
    }

    @Test
    void parseKommuner() {
        doParse("omrader/kommuner.json", AreaParser::parseMun);
    }

    @Test
    void parseKontraktsomrader() {
        doParse("omrader/kontraktsomrader.json", AreaParser::parseContractArea);
    }

    @Test
    void parseRegioner() {
        doParse("omrader/regioner.json", AreaParser::parseRegion);
    }

    @Test
    void parseRiksvegruter() {
        doParse("omrader/riksvegruter.json", AreaParser::parseRoute);
    }

    @Test
    void parseVegavdelinger() {
        doParse("omrader/vegavdelinger.json", AreaParser::parseDepartment);
    }

    private <T> void doParse(String file, Function<JsonObject, T> mapper) {
        List<T> list = parseList(file, mapper);
        long count = list.size();
        assertThat(count, is(not(0)));
    }

}

