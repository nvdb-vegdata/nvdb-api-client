package no.vegvesen.nvdbapi.client.gson;

import no.vegvesen.nvdbapi.client.model.datakatalog.DataType;
import no.vegvesen.nvdbapi.client.model.datakatalog.FeatureType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;
import static no.vegvesen.nvdbapi.client.gson.Helper.parseObject;
import static no.vegvesen.nvdbapi.client.gson.Helper.parsePlainList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.is;

public class VegobjekttyperParserTest {
    @ParameterizedTest
    @CsvSource({"14","95","105","581"})
    void parseVegobjekttyper(String file) throws IOException {
        Map<String, DataType> datatyper = parsePlainList("vegobjekttyper/datatyper.json", AttributeTypeParser::parseDataType)
            .stream()
            .collect(toMap(DataType::getName, Function.identity()));
        FeatureType featureType = parseObject("vegobjekttyper/" + file + ".json", e -> FeatureTypeParser.parse(datatyper, e));
        assertThat(featureType, is(notNullValue()));
    }
}
