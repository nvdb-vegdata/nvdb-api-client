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

import com.google.gson.JsonObject;
import no.vegvesen.nvdbapi.client.model.datakatalog.*;

import java.util.List;
import java.util.Map;

import static no.vegvesen.nvdbapi.client.gson.GsonUtil.*;

public final class FeatureTypeParser {
    private FeatureTypeParser() {
    }

    public static FeatureType parse(Map<String, DataType> dataTypes, JsonObject obj) {
        return new FeatureType(
            parseIntMember(obj, "id"),
            parseStringMember(obj, "navn"),
            parseStringMember(obj, "kortnavn"),
            parseStringMember(obj, "beskrivelse"),
            parseAttributeTypes(dataTypes, obj),
            getAssociations(obj, "relasjonstyper.foreldre"),
            getAssociations(obj, "relasjonstyper.barn"),
            parseStringMember(obj, "veiledning"),
            parseStringMember(obj, "sosinavn"),
            parseStringMember(obj, "sosinvdbnavn"),
            parseIntMember(obj, "sorteringsnummer"),
            parseDateMember(obj, "objektliste_dato"),
            parsePlacementType(obj),
            parseLocationalAttribute(dataTypes, obj),
            parseStringMember(obj, "status"),
            parseStringMember(obj, "hovedkategori"),
            parseBooleanMember(obj, "en_versjon"),
            parseBooleanMember(obj, "abstrakt_type"),
            parseBooleanMember(obj, "avledet"),
            parseBooleanMember(obj, "må_ha_mor"),
            parseBooleanMember(obj, "er_dataserie"),
            parseBooleanMember(obj, "konnekteringslenke_ok"),
            parseStringMember(obj, "tilleggsinformasjon"),
            parseBooleanMember(obj, "sensitiv"));
    }

    private static AttributeType parseLocationalAttribute(Map<String, DataType> dataTypes, JsonObject obj) {
        if(obj.has("stedfesting")){
            return AttributeTypeParser.parse(dataTypes, obj.getAsJsonObject("stedfesting"));
        } else {
            return null;
        }
    }

    private static FeatureType.PlacementType parsePlacementType(JsonObject obj) {
        JsonObject stedfesting = obj.getAsJsonObject("stedfesting");
        if(stedfesting == null) return null;
        JsonObject stedfestingObj = stedfesting.has("innhold") ? stedfesting.getAsJsonObject("innhold") : stedfesting;
        return FeatureType.PlacementType.from(parseStringMember(stedfestingObj, "stedfestingstype"));
    }

    private static List<AssociationType> getAssociations(JsonObject obj, String path) {
        return parseArray(obj, path, AssociationTypeParser::parse);
    }

    private static List<AttributeType> parseAttributeTypes(Map<String, DataType> dataTypes, JsonObject obj) {
        return parseArray(obj, "egenskapstyper", e -> AttributeTypeParser.parse(dataTypes, e));
    }

    public static FeatureTypeCategory parseCategory(JsonObject obj) {
        return new FeatureTypeCategory(
            parseIntMember(obj, "id"),
            parseStringMember(obj, "navn"),
            parseStringMember(obj, "kortnavn"),
            parseStringMember(obj, "beskrivelse"),
            parseIntMember(obj, "sorteringsnummer"),
            parseDateMember(obj, "startdato"),
            parseBooleanMember(obj, "primærkategori"));
    }
}
