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
import no.vegvesen.nvdbapi.client.model.datakatalog.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static no.vegvesen.nvdbapi.client.gson.GsonUtil.*;

public final class FeatureTypeParser {
    private FeatureTypeParser() {
    }

    public static FeatureType parse(Map<String, DataType> dataTypes, JsonObject obj) {
        List<AttributeType> attributeTypes = new ArrayList<>();
        if (obj.has("egenskapstyper")) {
            JsonArray attributeTypeArray = obj.getAsJsonArray("egenskapstyper");
            attributeTypeArray.forEach(e -> attributeTypes.add(AttributeTypeParser.parse(dataTypes, e.getAsJsonObject())));
        }

        List<AssociationType> parents = new ArrayList<>(), children = new ArrayList<>();
        JsonArray childrenArray = getArray(obj, "relasjonstyper.barn").orElse(null);
        if (childrenArray != null) {
            childrenArray.forEach(e -> children.add(AssociationTypeParser.parse(e.getAsJsonObject())));
        }
        JsonArray parentsArray = getArray(obj, "relasjonstyper.foreldre").orElse(null);
        if (parentsArray != null) {
            parentsArray.forEach(e -> parents.add(AssociationTypeParser.parse(e.getAsJsonObject())));
        }

        String placementPath = obj.has("stedfesting.innhold") ? "stedfesting.innhold.geometritype" : "stedfesting.geometritype";
        FeatureType.PlacementType placementType = FeatureType.PlacementType.from(parseStringMember(obj, placementPath));

        AttributeType locationalAttribute = null;
        if(obj.has("stedfesting")){
            locationalAttribute = AttributeTypeParser.parse(dataTypes, obj.getAsJsonObject("stedfesting"));
        }

        return new FeatureType(parseIntMember(obj, "id"),
                parseStringMember(obj, "navn"),
                parseStringMember(obj, "kortnavn"),
                parseStringMember(obj, "beskrivelse"),
                attributeTypes,
                parents,
                children,
                parseStringMember(obj, "veiledning"),
                parseStringMember(obj, "sosinavn"),
                parseStringMember(obj, "sosinvdbnavn"),
                parseIntMember(obj, "sorteringsnummer"),
                parseDateMember(obj, "objektliste_dato"),
                placementType,
                locationalAttribute,
                parseStringMember(obj, "status"),
                parseStringMember(obj, "hovedkategori"),
                parseBooleanMember(obj, "dekning"),
                parseBooleanMember(obj, "abstrakt_type"),
                parseBooleanMember(obj, "avledet"),
                parseBooleanMember(obj, "må_ha_mor"),
                parseBooleanMember(obj, "er_dataserie"),
                parseBooleanMember(obj, "konnekteringslenkeOk"),
                parseStringMember(obj, "tilleggsinformasjon"));
    }

    public static FeatureTypeCategory parseCategory(JsonObject obj) {
        Integer id = parseIntMember(obj, "id");
        String name = parseStringMember(obj, "navn");
        String shortName = parseStringMember(obj, "kortnavn");
        String description = parseStringMember(obj, "beskrivelse");
        Integer sortNumber = parseIntMember(obj, "sorteringsnummer");
        LocalDate validFrom = parseDateMember(obj, "startdato");

        return new FeatureTypeCategory(id, name, shortName, description, sortNumber, validFrom);
    }
}
