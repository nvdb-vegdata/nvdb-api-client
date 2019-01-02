package no.vegvesen.nvdbapi.client.gson;

import no.vegvesen.nvdbapi.client.model.datakatalog.DataType;
import no.vegvesen.nvdbapi.client.model.roadobjects.RoadObject;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;
import static no.vegvesen.nvdbapi.client.gson.Helper.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class VegobjekterParserTest {
    @ParameterizedTest
    @CsvSource({"95","105"})
    void parseVegobjekter(String file) throws IOException {
        Map<String, DataType> datatyper = parsePlainList("vegobjekttyper/datatyper.json", AttributeTypeParser::parseDataType)
            .stream()
            .collect(toMap(DataType::getName, Function.identity()));
        List<RoadObject> roadObjects = parseObjekterList("vegobjekter/" + file + ".json", e -> RoadObjectParser.parse(datatyper, e));
        assertThat(roadObjects.size(), is(not(0)));
    }
}
