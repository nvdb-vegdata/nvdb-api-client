package no.vegvesen.nvdbapi.client.gson;

import no.vegvesen.nvdbapi.client.model.datakatalog.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;
import static no.vegvesen.nvdbapi.client.gson.Helper.parseObject;
import static no.vegvesen.nvdbapi.client.gson.Helper.parsePlainList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class VegobjekttyperParserTest {
    @ParameterizedTest
    @CsvSource({"14","95","105","581"})
    void parseVegobjekttyper(String file) throws IOException {
        Map<String, DataType> datatyper = parseDatatyper();
        FeatureType featureType = parseObject("vegobjekttyper/" + file + ".json", e -> FeatureTypeParser.parse(datatyper, e));
        assertThat(featureType, is(notNullValue()));
    }

    private Map<String, DataType> parseDatatyper() throws IOException {
        return parsePlainList("vegobjekttyper/datatyper.json", AttributeTypeParser::parseDataType)
                .stream()
                .collect(toMap(DataType::getName, Function.identity()));
    }

    @ParameterizedTest
    @MethodSource
    <T extends AttributeType> void parseAttributeType(String file,
                                                      int attributetypeId,
                                                      Class<T> expectedType,
                                                      Consumer<T> asserts) throws IOException {
        Map<String, DataType> datatyper = parseDatatyper();
        FeatureType featureType = parseObject("vegobjekttyper/" + file + ".json", e -> FeatureTypeParser.parse(datatyper, e));
        T attributeType = featureType.getAttributeType(attributetypeId, expectedType);
        assertThat(attributeType, notNullValue());
        asserts.accept(attributeType);
    }

    static Stream<Arguments> parseAttributeType() {
        Consumer<EnumAttributeType> enumAsserts = (EnumAttributeType a) -> {
            assertThat(a.size(), greaterThan(0));
        };
        return Stream.of(
          arguments("14", 10724, StringEnumAttributeType.class, enumAsserts),
          arguments("14", 4660, DoubleEnumAttributeType.class, enumAsserts),
          arguments("581", 3947, IntegerEnumAttributeType.class, enumAsserts)
        );
    }

}
