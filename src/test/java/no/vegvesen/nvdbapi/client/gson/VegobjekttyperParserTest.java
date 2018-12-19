package no.vegvesen.nvdbapi.client.gson;

import no.vegvesen.nvdbapi.client.model.datakatalog.DataType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;
import static no.vegvesen.nvdbapi.client.gson.Helper.parseObject;
import static no.vegvesen.nvdbapi.client.gson.Helper.parsePlainList;

public class VegobjekttyperParserTest {
    @ParameterizedTest
    @CsvSource({"95","105"})
    void parseVegobjekttyper(String file) throws IOException {
        Map<String, DataType> datatyper = parsePlainList("vegobjekttyper/datatyper.json", AttributeTypeParser::parseDataType)
            .stream()
            .collect(toMap(DataType::getName, Function.identity()));
        parseObject("vegobjekttyper/" + file + ".json", e -> FeatureTypeParser.parse(datatyper, e));
    }
}
