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
import no.vegvesen.nvdbapi.client.model.SpatialType;
import no.vegvesen.nvdbapi.client.model.datakatalog.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static no.vegvesen.nvdbapi.client.gson.GsonUtil.*;

public final class AttributeTypeParser {
    private AttributeTypeParser() {
    }

    public static AttributeType parse(Map<Integer, DataType> typeMap, JsonObject object) {
        int typeId = parseIntMember(object, "datatype");
        boolean isList = parseBooleanMember(object, "liste");
        DataType type = typeMap.get(Integer.valueOf(typeId));

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
                isList,
                sortNumber,
                requirementComment,
                importance,
                sosiName,
                sosiNvdbName,
                sensitivityLevel,
                validFrom);

        switch (type.getJavaType()) {
            case TEXT:
                Set<EnumValue> values = parseEnumValues(object);
                Integer fieldLength = parseIntMember(object, "feltlengde");
                String textDefaultValue = parseStringMember(object, "standardverdi");

                return new StringAttributeType(props, parameters, textDefaultValue, fieldLength, values);
            case CHARACTER:
                Character charDefValue = Optional.ofNullable(parseStringMember(object, "standardverdi")).map(s -> s.charAt(0)).orElse(null);

                return new CharacterAttributeType(props, parameters, charDefValue);
            case BOOLEAN:
                Boolean boolDefaultValue = parseBooleanMember(object, "standardverdi");
                return new BooleanAttributeType(props, parameters, boolDefaultValue);
            case NUMBER:
                values = parseEnumValues(object);
                fieldLength = parseIntMember(object, "feltlengde");

                boolean isDouble = false;
                if (object.has("desimaler")) {
                    int decimalCount = parseIntMember(object, "desimaler");
                    isDouble = decimalCount > 0;
                }

                if (!isDouble) {
                    Integer intDefValue = parseIntMember(object, "standardverdi");
                    Integer intMinValue = parseIntMember(object, "min_anbefalt"), intMaxValue = parseIntMember(object, "maks_anbefalt");
                    Integer intAbsMinValue = parseIntMember(object, "min"), intAbsMaxValue = parseIntMember(object, "maks");
                    Unit unit = object.has("enhet") ? parseUnit(object.getAsJsonObject("enhet")) : null;

                    return new IntegerAttributeType(props, parameters, intDefValue, intMinValue, intMaxValue, intAbsMinValue,
                            intAbsMaxValue, fieldLength, unit, values);
                } else {
                    values = parseEnumValues(object);

                    fieldLength = parseIntMember(object, "feltlengde");
                    Integer decimalCount = parseIntMember(object, "desimaler");
                    Double doubleDefValue = parseDoubleMember(object, "standardverdi");
                    Double doubleMinValue = parseDoubleMember(object, "min_anbefalt"), doubleMaxValue = parseDoubleMember(object, "maks_anbefalt");
                    Double doubleAbsMinValue = parseDoubleMember(object, "min"), doubleAbsMaxValue = parseDoubleMember(object, "maks");
                    Unit unit = object.has("enhet") ? parseUnit(object.getAsJsonObject("enhet")) : null;

                    return new DoubleAttributeType(props, parameters, doubleDefValue, doubleMinValue, doubleMaxValue,
                            doubleAbsMinValue, doubleAbsMaxValue, fieldLength, decimalCount, unit, values);
                }
            case SPATIAL:
                return new SpatialAttributeType(props,
                        parameters,
                        determineSpatialType(object),
                        parseIntMember(object, "dimensjoner"));
            case LOCAL_DATE:
                LocalDate defaultDateValue = parseDateMember(object, "standardverdi"), minDateValue = parseDateMember(object, "min");
                LocalDate maxDateValue = parseDateMember(object, "maks");

                return new DateAttributeType(props, parameters, defaultDateValue, minDateValue, maxDateValue);
            case SHORT_DATE:
                Integer intDefValue = parseIntMember(object, "standardverdi");
                Integer intMinValue = parseIntMember(object, "min_anbefalt");
                Integer intMaxValue = parseIntMember(object, "maks_anbefalt");

                return new ShortDateAttributeType(props, parameters, intDefValue, intMinValue, intMaxValue);
            case LOCAL_TIME:
                LocalTime defaultTimeValue = parseTimeMember(object, "standardverdi");
                LocalTime minTimeValue = parseTimeMember(object, "min_anbefalt");
                LocalTime maxTimeValue = parseTimeMember(object, "maks_anbefalt");

                return new TimeAttributeType(props, parameters, defaultTimeValue, minTimeValue, maxTimeValue);
            case BINARY:
                return new BinaryObjectAttributeType(props, parameters);
            case STRUCTURE:
                return new StructureAttributeType(props, parameters);
            default:
                throw new UnsupportedOperationException("Unrecognized data type" + type);
        }
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

    private static Set<EnumValue> parseEnumValues(JsonObject obj) {
        if (!obj.has("tillatte_verdier")) {
            return Collections.emptySet();
        }
        Set<EnumValue> values = new HashSet<>();
        JsonArray array = obj.get("tillatte_verdier").getAsJsonArray();
        array.forEach(e -> {
            JsonObject object = e.getAsJsonObject();

            Integer id = parseIntMember(object, "id");
            String shortValue = parseStringMember(object, "kortnavn");
            String value = parseStringMember(object, "navn");
            String description = parseStringMember(object, "beskrivelse");
            Integer sortNumber = parseIntMember(object, "sorteringsnummer");
            LocalDate objectListDate = parseDateMember(object, "objektliste_dato");

            values.add(new EnumValue(id, sortNumber, value, shortValue, description, objectListDate));
        });
        return values;
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
                return JavaType.SPATIAL;
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
