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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import no.vegvesen.nvdbapi.client.model.LocationalType;
import no.vegvesen.nvdbapi.client.model.SpatialType;
import no.vegvesen.nvdbapi.client.model.datakatalog.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toSet;
import static no.vegvesen.nvdbapi.client.gson.GsonUtil.*;

public final class AttributeTypeParser {
    private AttributeTypeParser() {  }

    public static AttributeType parse(Map<String, DataType> typeMap, JsonObject object) {
        String egenskapstype = parseStringMember(object, "egenskapstype");
        switch (egenskapstype) {
            case "Tekst":
                return parseStringAttributeType(object, parseCommonProperties(typeMap, object));
            case "Tekstenum":
                return parseStringEnumAttributeType(object, parseCommonProperties(typeMap, object));
            case "Boolsk":
                return parseBooleanAttributeType(typeMap, object);
            case "Flyttall":
                return parseDoubleAttributeType(object, parseCommonProperties(typeMap, object));
            case "Flyttallenum":
                return parseDoubleEnumAttributeType(object, parseCommonProperties(typeMap, object));
            case "Heltall":
                return parseIntegerAttributeType(object, parseCommonProperties(typeMap, object));
            case "Heltallenum":
                return parseIntegerEnumAttributeType(object, parseCommonProperties(typeMap, object));
            case "Geometri":
                return parseSpatialAttributeType(typeMap, object);
            case "Stedfesting":
                return parseLocationalAttributeType(typeMap, object);
            case "Dato":
                return parseDateAttributeType(typeMap, object);
            case "Kortdato":
                return parseShortDateAttributeType(typeMap, object);
            case "Tid":
                return parseTimeAttributeType(typeMap, object);
            case "Binær":
                return parseBinaryObjectAttributeType(typeMap, object);
            case "Struktur":
                return new StructureAttributeType(parseCommonProperties(typeMap, object));
            case "Liste":
                return parseListAttributeType(typeMap, object);
            case "Assosiasjon":
                return parseAssociationRoleType(typeMap, object);
            default:
                throw new UnsupportedOperationException("Unrecognized attribute type" + egenskapstype);
        }
    }

    private static AssociationRoleType parseAssociationRoleType(Map<String, DataType> typeMap, JsonObject object) {
        return new AssociationRoleType(
            parseCommonProperties(typeMap, object),
            parseIntMember(object, "tilknytning"),
            parseIntMember(object, "vegobjekttypeid"),
            parseIntMember(object, "innenfor_mor"),
            parseDateMember(object, "startdato"),
            parseDateMember(object, "sluttdato"),
            parseIntMember(object, "assosiasjonskrav"),
            parseStringMember(object, "assosiasjonskravkommentar"));
    }

    private static ListAttributeType parseListAttributeType(Map<String, DataType> typeMap, JsonObject object) {
        return new ListAttributeType(
            parseCommonProperties(typeMap, object),
            parse(typeMap, object.getAsJsonObject("innhold")),
            parseIntMember(object, "maksimalt_antall_verdier"),
            parseIntMember(object, "minimalt_antall_verdier"));
    }

    private static BinaryObjectAttributeType parseBinaryObjectAttributeType(Map<String, DataType> typeMap, JsonObject object) {
        return new BinaryObjectAttributeType(
            parseCommonProperties(typeMap, object),
            parseStringMember(object, "mediatype"),
            parseBooleanMember(object, "ajourhold_snu"),
            parseBooleanMember(object, "lengdeavhengig_verdi"));
    }

    private static TimeAttributeType parseTimeAttributeType(Map<String, DataType> typeMap, JsonObject object) {
        return new TimeAttributeType(
            parseCommonProperties(typeMap, object),
            parseTimeMember(object, "standardverdi"),
            parseTimeMember(object, "min_anbefalt"),
            parseTimeMember(object, "maks_anbefalt"),
            parseStringMember(object, "format"),
            parseBooleanMember(object, "ajourhold_snu"),
            parseBooleanMember(object, "lengdeavhengig_verdi"));
    }

    private static ShortDateAttributeType parseShortDateAttributeType(Map<String, DataType> typeMap, JsonObject object) {
        return new ShortDateAttributeType(
            parseCommonProperties(typeMap, object),
            parseIntMember(object, "standardverdi"),
            parseIntMember(object, "min_anbefalt"),
            parseIntMember(object, "maks_anbefalt"),
            parseStringMember(object, "format"),
            parseBooleanMember(object, "ajourhold_snu"),
            parseBooleanMember(object, "lengdeavhengig_verdi"));
    }

    private static DateAttributeType parseDateAttributeType(Map<String, DataType> typeMap, JsonObject object) {
        return new DateAttributeType(
            parseCommonProperties(typeMap, object),
            parseDateMember(object, "standardverdi"),
            parseDateMember(object, "min"),
            parseDateMember(object, "maks"),
            parseStringMember(object, "format"),
            parseBooleanMember(object, "ajourhold_snu"),
            parseBooleanMember(object, "lengdeavhengig_verdi"));
    }

    private static LocationalAttributeType parseLocationalAttributeType(Map<String, DataType> typeMap, JsonObject object) {
        return new LocationalAttributeType(
            parseCommonProperties(typeMap, object),
            determineLocationalType(object),
            parseBooleanMember(object, "overlapp_ok"),
            parseStringMember(object, "kjørefelt_relevant"),
            parseStringMember(object, "sideposisjon_relevant"),
            parseStringMember(object, "ajourhold_i"),
            parseStringMember(object, "ajourhold_splitt"),
            parseBooleanMember(object, "innenfor_mor"),
            parseStringMember(object, "overlappsautomatikk"));
    }

    private static SpatialAttributeType parseSpatialAttributeType(Map<String, DataType> typeMap, JsonObject object) {
        return new SpatialAttributeType(
            parseCommonProperties(typeMap, object),
            determineSpatialType(object),
            parseIntMember(object, "dimensjoner"),
            parseBooleanMember(object, "innenfor_mor"));
    }

    private static BooleanAttributeType parseBooleanAttributeType(Map<String, DataType> typeMap, JsonObject object) {
        return new BooleanAttributeType(
            parseCommonProperties(typeMap, object),
            parseBooleanMember(object, "standardverdi"),
            parseBooleanMember(object, "ajourhold_snu"),
            parseBooleanMember(object, "lengdeavhengig_verdi"));
    }

    private static AttributeCommonProperties parseCommonProperties(Map<String, DataType> typeMap, JsonObject object) {
        return new AttributeCommonProperties(
            parseIntMember(object, "id"),
            parseIntMember(object, "kategori"),
            parseStringMember(object, "navn"),
            parseStringMember(object, "kortnavn"),
            parseStringMember(object, "beskrivelse"),
            typeMap.get(parseStringMember(object, "datatype")),
            parseIntMember(object, "sorteringsnummer"),
            parseStringMember(object, "veiledning"),
            AttributeType.Importance.from(parseStringMember(object, "viktighet")),
            parseStringMember(object, "sosinavn"),
            parseStringMember(object, "sosinvdbnavn"),
            parseIntMember(object, "sensitivitet"),
            parseDateMember(object, "objektliste_dato"),
            parseDateMember(object, "slutt_dato"),
            parseBooleanMember(object, "skrivebeskyttet"),
            parseStringMember(object, "ledetekst"),
            parseIntMember(object, "komplementær_egenskapstype"),
            parseStringMember(object, "grunnrissreferanse"),
            parseStringMember(object, "høydereferanse"),
            parseStringMember(object, "sosi_referanse"),
            parseBooleanMember(object, "referansegeometri_tilstrekkelig"),
            parseIntMember(object, "høydereferanse_tall"),
            parseDoubleMember(object, "nøyaktighetskrav_grunnriss"),
            parseDoubleMember(object, "nøyaktighetskrav_høyde"),
            parseStringListMember(object, "tilleggskrav"),
            parseBooleanMember(object, "avledet"),
            parseBooleanMember(object, "obligatorisk_verdi"));
    }

    private static DoubleAttributeType parseDoubleAttributeType(JsonObject object, AttributeCommonProperties props) {
        return new DoubleAttributeType(
            props,
            parseDoubleMember(object, "standardverdi"),
            parseDoubleMember(object, "min_anbefalt"),
            parseDoubleMember(object, "maks_anbefalt"),
            parseDoubleMember(object, "min"),
            parseDoubleMember(object, "maks"),
            parseIntMember(object, "feltlengde"),
            parseIntMember(object, "desimaler"),
            parseUnit(object.getAsJsonObject("enhet")),
            parseBooleanMember(object, "fortegnsendring_snu"),
            parseBooleanMember(object, "ajourhold_snu"),
            parseBooleanMember(object, "lengdeavhengig_verdi"));
    }

    private static DoubleEnumAttributeType parseDoubleEnumAttributeType(JsonObject object, AttributeCommonProperties props) {
        return new DoubleEnumAttributeType(
            props,
            parseDoubleMember(object, "standardverdi"),
            parseDoubleMember(object, "min_anbefalt"),
            parseDoubleMember(object, "maks_anbefalt"),
            parseDoubleMember(object, "min"),
            parseDoubleMember(object, "maks"),
            parseIntMember(object, "feltlengde"),
            parseIntMember(object, "desimaler"),
            parseUnit(object.getAsJsonObject("enhet")),
            parseEnumValues(object),
            parseBooleanMember(object, "fortegnsendring_snu"),
            parseBooleanMember(object, "ajourhold_snu"),
            parseBooleanMember(object, "lengdeavhengig_verdi"));
    }

    private static AttributeType parseIntegerAttributeType(JsonObject object, AttributeCommonProperties props) {
        return new IntegerAttributeType(
            props,
            parseIntMember(object, "standardverdi"),
            parseIntMember(object, "min_anbefalt"),
            parseIntMember(object, "maks_anbefalt"),
            parseIntMember(object, "min"),
            parseIntMember(object, "maks"),
            parseIntMember(object, "feltlengde"),
            parseUnit(object.getAsJsonObject("enhet")),
            parseBooleanMember(object, "fortegnsendring_snu"),
            parseBooleanMember(object, "ajourhold_snu"),
            parseBooleanMember(object, "lengdeavhengig_verdi"));
    }

    private static IntegerEnumAttributeType parseIntegerEnumAttributeType(JsonObject object, AttributeCommonProperties props) {
        return new IntegerEnumAttributeType(
            props,
            parseIntMember(object, "standardverdi"),
            parseIntMember(object, "min_anbefalt"),
            parseIntMember(object, "maks_anbefalt"),
            parseIntMember(object, "min"),
            parseIntMember(object, "maks"),
            parseIntMember(object, "feltlengde"),
            parseUnit(object.getAsJsonObject("enhet")),
            parseEnumValues(object),
            parseBooleanMember(object, "fortegnsendring_snu"),
            parseBooleanMember(object, "ajourhold_snu"),
            parseBooleanMember(object, "lengdeavhengig_verdi"));
    }

    private static StringAttributeType parseStringAttributeType(JsonObject object, AttributeCommonProperties props) {
        return new StringAttributeType(
            props,
            parseStringMember(object, "standardverdi"),
            parseIntMember(object, "feltlengde"),
            parseStringMember(object, "format"),
            parseBooleanMember(object, "ajourhold_snu"),
            parseBooleanMember(object, "lengdeavhengig_verdi"));
    }

    private static StringEnumAttributeType parseStringEnumAttributeType(JsonObject object, AttributeCommonProperties props) {
        return new StringEnumAttributeType(
            props,
            parseStringMember(object, "standardverdi"),
            parseIntMember(object, "feltlengde"),
            parseEnumValues(object),
            parseStringMember(object, "format"),
            parseBooleanMember(object, "ajourhold_snu"),
            parseBooleanMember(object, "lengdeavhengig_verdi"));
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

        JsonArray array = obj.get("tillatte_verdier").getAsJsonArray();
        return StreamSupport.stream(array.spliterator(), false)
            .map(JsonElement::getAsJsonObject)
            .map(allowedValue ->
                AttributeTypeParser.<T>createEnumValue(
                    allowedValue,
                    parseStringMember(allowedValue, "type"),
                    parseIntMember(allowedValue, "id"),
                    parseIntMember(allowedValue, "sorteringsnummer"),
                    parseStringMember(allowedValue, "kortnavn"),
                    parseStringMember(allowedValue, "beskrivelse"),
                    parseDateMember(allowedValue, "objektliste_dato")
                ))
            .collect(toSet());
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
            case "Tekst":
                return (T) new StringEnumValue(id,
                    sortNumber, parseStringMember(obj, "verdi"),
                    shortValue, description, objectListDate,
                    parseBooleanMember(obj, "standardverdi"),
                    parseBooleanMember(obj, "kortnavn_brukbar"),
                    parseIntMember(obj, "kortnavnlengde"),
                    parseIntMember(obj, "komplementær_enumverdi"));
            case "Heltall":
                return (T) new IntegerEnumValue(id,
                    sortNumber, parseIntMember(obj, "verdi"),
                    shortValue, description, objectListDate,
                    parseBooleanMember(obj, "standardverdi"),
                    parseBooleanMember(obj, "kortnavn_brukbar"),
                    parseIntMember(obj, "kortnavnlengde"),
                    parseIntMember(obj, "komplementær_enumverdi"));
            case "Flyttall":
                return (T) new DoubleEnumValue(id,
                    sortNumber, parseDoubleMember(obj, "verdi"),
                    shortValue, description, objectListDate,
                    parseBooleanMember(obj, "standardverdi"),
                    parseBooleanMember(obj, "kortnavn_brukbar"),
                    parseIntMember(obj, "kortnavnlengde"),
                    parseIntMember(obj, "komplementær_enumverdi"));
            default:
                throw new IllegalArgumentException("Could not handle enum value of type " + type);
        }
    }

    public static Unit parseUnit(JsonObject obj) {
        if(obj == null) return null;
        return new Unit(
            parseIntMember(obj, "id"),
            parseStringMember(obj, "navn"),
            parseStringMember(obj, "kortnavn"));
    }

    public static DataType parseDataType(JsonObject obj) {

        return new DataType(
            parseIntMember(obj, "id"),
            parseStringMember(obj, "navn"),
            parseStringMember(obj, "kortnavn"),
            parseStringMember(obj, "beskrivelse"),
            determineActualType(parseIntMember(obj, "id")));
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
            case 39:
                return JavaType.ASSOCIATION;
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
