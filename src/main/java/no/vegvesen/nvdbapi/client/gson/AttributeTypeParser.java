/*
 * Copyright (c) 2015-2017, Statens vegvesen
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package no.vegvesen.nvdbapi.client.gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import no.vegvesen.nvdbapi.client.model.LocationalType;
import no.vegvesen.nvdbapi.client.model.SpatialType;
import no.vegvesen.nvdbapi.client.model.datakatalog.*;

import java.time.LocalDate;
import java.util.*;

import static no.vegvesen.nvdbapi.client.gson.GsonUtil.*;

public final class AttributeTypeParser {
    private AttributeTypeParser() {  }

    public static AttributeType parse(Map<Integer, DataType> typeMap, JsonObject object) {
        int typeId = parseIntMember(object, "datatype");
        DataType type = typeMap.get(typeId);

        Integer id = parseIntMember(object, "id");
        Integer category = parseIntMember(object, "kategori");
        String name = parseStringMember(object, "navn");
        String shortname = parseStringMember(object, "kortnavn");
        String description = parseStringMember(object, "beskrivelse");
        Integer sortNumber = parseIntMember(object, "sorteringsnummer");
        String requirementComment = parseStringMember(object, "veiledning");
        String sosiName = parseStringMember(object, "sosinavn");
        String sosiNvdbName = parseStringMember(object, "sosinvdbnavn");
        AttributeType.Importance importance = AttributeType.Importance.from(parseIntMember(object, "viktighet"));
        Integer sensitivityLevel = parseIntMember(object, "sensitivitet");
        LocalDate validFrom = parseDateMember(object, "objektliste_dato");
        AttributeTypeParameters parameters = GuidanceParametersParser.parseAttributeType(object.getAsJsonObject("styringsparametere"));

        AttributeCommonProperties props = new AttributeCommonProperties(
                id,
                category,
                name,
                shortname,
                description,
                type,
                sortNumber,
                requirementComment,
                importance,
                sosiName,
                sosiNvdbName,
                sensitivityLevel,
                validFrom);

        switch (type.getJavaType()) {
            case TEXT:
                return parseStringAttributeType(object, parameters, props);
            case CHARACTER:
                return new CharacterAttributeType(props,
                        parameters,
                        Optional.ofNullable(parseStringMember(object, "standardverdi"))
                                .map(s -> s.charAt(0))
                                .orElse(null));
            case BOOLEAN:
                return new BooleanAttributeType(props, parameters, parseBooleanMember(object, "standardverdi"));
            case NUMBER:
                boolean isDouble = object.has("desimaler")
                        && parseIntMember(object, "desimaler") > 0;

                if (!isDouble) {
                    return parseIntegerAttributeType(object, parameters, props);
                } else {
                    return parseDoubleAttributeType(object, parameters, props);
                }
            case GEOMETRY:
                //spatial or locational
                if(parseStringMember(object, "type").equalsIgnoreCase("stedfesting")){
                    return new LocationalAttributeType(props, parameters, determineLocationalType(object));
                }
                return new SpatialAttributeType(props,
                        parameters,
                        determineSpatialType(object),
                        parseIntMember(object, "dimensjoner"));
            case LOCAL_DATE:
                return new DateAttributeType(props,
                        parameters,
                        parseDateMember(object, "standardverdi"),
                        parseDateMember(object, "min"),
                        parseDateMember(object, "maks"));
            case SHORT_DATE:
                return new ShortDateAttributeType(props,
                        parameters,
                        parseIntMember(object, "standardverdi"),
                        parseIntMember(object, "min_anbefalt"),
                        parseIntMember(object, "maks_anbefalt"));
            case LOCAL_TIME:
                return new TimeAttributeType(props,
                        parameters,
                        parseTimeMember(object, "standardverdi"),
                        parseTimeMember(object, "min_anbefalt"),
                        parseTimeMember(object, "maks_anbefalt"));
            case BINARY:
                return new BinaryObjectAttributeType(props, parameters);
            case STRUCTURE:
                return new StructureAttributeType(props, parameters);
            case LIST:
                AttributeType content = parse(typeMap, object.getAsJsonObject("innhold"));
                return new ListAttributeType(props,
                        parameters,
                        content,
                        parseIntMember(object, "maksimalt_antall_verdier"),
                        parseIntMember(object, "minimalt_antall_verdier"));
            default:
                throw new UnsupportedOperationException("Unrecognized data type" + type);
        }
    }

    private static AttributeType parseDoubleAttributeType(JsonObject object, AttributeTypeParameters parameters, AttributeCommonProperties props) {
        Integer decimalCount = parseIntMember(object, "desimaler");
        Double doubleDefValue = parseDoubleMember(object, "standardverdi");
        Double doubleMinValue = parseDoubleMember(object, "min_anbefalt"), doubleMaxValue = parseDoubleMember(object, "maks_anbefalt");
        Double doubleAbsMinValue = parseDoubleMember(object, "min"), doubleAbsMaxValue = parseDoubleMember(object, "maks");
        Unit unit = object.has("enhet") ? parseUnit(object.getAsJsonObject("enhet")) : null;

        return new DoubleAttributeType(props, parameters, doubleDefValue, doubleMinValue, doubleMaxValue,
                doubleAbsMinValue, doubleAbsMaxValue, parseIntMember(object, "feltlengde"), decimalCount, unit, parseEnumValues(object));
    }

    private static AttributeType parseIntegerAttributeType(JsonObject object, AttributeTypeParameters parameters, AttributeCommonProperties props) {
        Integer intDefValue = parseIntMember(object, "standardverdi");
        Integer intMinValue = parseIntMember(object, "min_anbefalt"), intMaxValue = parseIntMember(object, "maks_anbefalt");
        Integer intAbsMinValue = parseIntMember(object, "min"), intAbsMaxValue = parseIntMember(object, "maks");
        Unit unit = object.has("enhet") ? parseUnit(object.getAsJsonObject("enhet")) : null;

        return new IntegerAttributeType(props, parameters, intDefValue, intMinValue, intMaxValue, intAbsMinValue,
                intAbsMaxValue, parseIntMember(object, "feltlengde"), unit, parseEnumValues(object));
    }

    private static AttributeType parseStringAttributeType(JsonObject object, AttributeTypeParameters parameters, AttributeCommonProperties props) {
        Set<StringEnumValue> values = parseEnumValues(object);
        Integer fieldLength = parseIntMember(object, "feltlengde");
        String textDefaultValue = parseStringMember(object, "standardverdi");

        return new StringAttributeType(props, parameters, textDefaultValue, fieldLength, values);
    }

    private static SpatialType determineSpatialType(JsonObject object) {
        String type = parseStringMember(object, "geometritype");
        switch (type) {
            case "PUNKT":
                return SpatialType.POINT;
            case "LINJE":
                return SpatialType.LINE_STRING;
            case "POLYGON":
                return SpatialType.POLYGON;
            case "KOMPLEKS":
                return SpatialType.COMPLEX;
            case "FLEREPUNKT":
                return SpatialType.MULTI_POINT;
            case "FLERELINJE":
                return SpatialType.MULTI_LINE_STRING;
            case "FLEREPOLYGON":
                return SpatialType.MULTI_POLYGON;
            default:
                return SpatialType.UNKNOWN;
        }
    }

    private static LocationalType determineLocationalType(JsonObject object){
        String type = parseStringMember(object, "geometritype");
        switch (type){
            case "LINJE":
                return LocationalType.LINE;
            case "PUNKT":
                return LocationalType.POINT;
            default:
                return LocationalType.UNKNOWN;
        }
    }

    private static <T extends EnumValue> Set<T> parseEnumValues(JsonObject obj) {
        if (!obj.has("tillatte_verdier")) {
            return Collections.emptySet();
        }
        Set<T> values = new HashSet<>();
        JsonArray array = obj.get("tillatte_verdier").getAsJsonArray();
        array.forEach(e -> {
            JsonObject allowedValue = e.getAsJsonObject();

            Integer id = parseIntMember(allowedValue, "id");
            String shortValue = parseStringMember(allowedValue, "kortnavn");

            String description = parseStringMember(allowedValue, "beskrivelse");
            Integer sortNumber = parseIntMember(allowedValue, "sorteringsnummer");
            LocalDate objectListDate = parseDateMember(allowedValue, "objektliste_dato");
            String type = parseStringMember(allowedValue, "type");


            values.add(
                    createEnumValue(
                            allowedValue,
                            type,
                            id,
                            sortNumber,
                            shortValue,
                            description,
                            objectListDate
                    ));
        });
        return values;
    }

    @SuppressWarnings("unchecked")
    private static <T extends EnumValue> T createEnumValue(JsonObject obj,
                                                           String type,
                                                           Integer id,
                                                           Integer sortNumber,
                                                           String shortValue,
                                                           String description,
                                                           LocalDate objectListDate) {
        switch (type) {
            case "Streng":
                return (T) new StringEnumValue(id,
                        sortNumber, parseStringMember(obj, "verdi"),
                        shortValue, description, objectListDate);
            case "Heltall":
                return (T) new IntegerEnumValue(id,
                        sortNumber, parseIntMember(obj, "verdi"),
                        shortValue, description, objectListDate);
            case "Flyttall":
                return (T) new DoubleEnumValue(id,
                        sortNumber, parseDoubleMember(obj, "verdi"),
                        shortValue, description, objectListDate);
            default:
                throw new IllegalArgumentException("Could not handle enum value of type " + type);
        }
    }

    public static Unit parseUnit(JsonObject obj) {
        Integer id = parseIntMember(obj, "id");
        String name = parseStringMember(obj, "navn");
        String shortName = parseStringMember(obj, "kortnavn");

        return new Unit(id, name, shortName);
    }

    public static DataType parseDataType(JsonObject obj) {
        Integer id = parseIntMember(obj, "id");
        String name = parseStringMember(obj, "navn");
        String shortName = parseStringMember(obj, "kortnavn");
        String description = parseStringMember(obj, "beskrivelse");

        return new DataType(id, name, shortName, description, determineActualType(id));
    }

    private static JavaType determineActualType(int typeId) {
        switch (typeId) {
            case 1:
            case 30:
                return JavaType.TEXT;
            case 2:
            case 31:
            case 32:
                return JavaType.NUMBER;
            case 8:
                return JavaType.LOCAL_DATE;
            case 9:
                return JavaType.SHORT_DATE;
            case 10:
                return JavaType.LOCAL_TIME;
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
                return JavaType.GEOMETRY;
            case 26:
                return JavaType.STRUCTURE;
            case 28:
                return JavaType.BOOLEAN;
            case 29:
                return JavaType.CHARACTER;
            case 27:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
                return JavaType.BINARY;
            case 38:
                return JavaType.LIST;
            default:
                return JavaType.UNKNOWN;
        }
    }


    public static AttributeTypeCategory parseCategory(JsonObject obj) {
        return new AttributeTypeCategory(
                parseIntMember(obj, "id"),
                parseStringMember(obj, "navn"),
                parseStringMember(obj, "kortnavn"),
                parseStringMember(obj, "beskrivelse"),
                parseIntMember(obj, "sorteringsnummer"));
    }
}
