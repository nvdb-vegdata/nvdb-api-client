package no.vegvesen.nvdbapi.client.gson;

import no.vegvesen.nvdbapi.client.model.*;
import no.vegvesen.nvdbapi.client.model.datakatalog.DataType;
import no.vegvesen.nvdbapi.client.model.datakatalog.Unit;
import no.vegvesen.nvdbapi.client.model.roadobjects.Placement;
import no.vegvesen.nvdbapi.client.model.roadobjects.RoadObject;
import no.vegvesen.nvdbapi.client.model.roadobjects.attribute.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.MonthDay;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toMap;
import static no.vegvesen.nvdbapi.client.gson.Helper.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class VegobjekterParserTest {
    @ParameterizedTest
    @CsvSource({"95","105"})
    void parseVegobjekter(String file) throws IOException {
        Map<String, DataType> datatyper = parsePlainList("vegobjekttyper/datatyper.json", AttributeTypeParser::parseDataType)
            .stream()
            .collect(toMap(DataType::getName, Function.identity()));
        List<RoadObject> roadObjects = parseObjekterList("vegobjekter/" + file + ".json", e -> RoadObjectParser.parse(datatyper, e));
        assertThat(roadObjects.size(), is(not(0)));

        for (RoadObject roadObject : roadObjects) {
            assertThat(   roadObject.getLocation().getRoadSysRefs(), is(not(empty())));
        }
    }

    @Test
    void parseEgenskaper() throws IOException {
        List<Attribute> attributes = parsePlainList("vegobjekter/egenskaper.json", RoadObjectParser::parseAttribute);
        assertThat(attributes, contains(
            new BlobAttribute(1, 213, "bah", "https://bah"),
            new BooleanAttribute(2, false),
            new DateAttribute(3, LocalDate.of(2019, 10, 12)),
            new ShortDateAttribute(4, MonthDay.of(10, 12)),
            new TimeAttribute(5, LocalTime.of(12, 12)),
            new SpatialAttribute(6,
                new Geometry(
                    "POINT Z(88206.76856 6783844.94758 259.86262)",
                    Projection.UTM33,
                    new Quality(95, 5, 1, 1, 2, 2),
                    false, true,
                    new GeometryAttributes(
                        LocalDate.of(1012, 12, 12),
                        LocalDate.of(1012, 12, 13),
                        LocalDate.of(1012, 12, 14),
                        "blabal",
                        1847,
                        "papir",
                        "Schmosi",
                        2012,
                        false,
                        0.0,
                        123
                    )
                )),
            new IntegerAttribute(7, 1, new Unit(1, "Meter", "m")),
            new EnumAttribute(8, 1),
            new RealAttribute(9, 1.213),
            new StructAttribute(10,
                asList(
                    new EnumAttribute(10001, 1),
                    new RealAttribute(10002, 1.213)
                )),
            new StringAttribute(11, "øl"),
            new EnumAttribute(12, 1),
            new ReflinkExtentAttribute(
                13,
                384011,
                Direction.WITH,
                SidePosition.MIDDLE_RIGHT,
                singletonList("1"),
                0.07178555,
                0.07178555
            ),
            new ReflinkExtentAttribute(
                14,
                384011,
                Direction.WITH,
                SidePosition.MIDDLE_RIGHT,
                singletonList("1"),
                0.07178555,
                0.15652675
            ),
            new TurnExtent(15,
                384011,
                new Placement(
                    942450,
                    0.23585116,
                    0.23585116,
                    Direction.WITH,
                    SidePosition.MIDDLE,
                    emptyList()
                ),
                new Placement(
                    942694,
                    0.64558392,
                    0.64558392,
                    Direction.WITH,
                    SidePosition.MIDDLE,
                    emptyList()
                )
            ),
            new ListAttribute(
                220016,
                singletonList(
                    new AssociationAttribute(200016, 78735746L)
                ))
        ));
    }
}
