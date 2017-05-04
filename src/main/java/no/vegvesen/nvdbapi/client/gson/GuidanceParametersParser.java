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
import no.vegvesen.nvdbapi.client.model.datakatalog.FeatureType;
import no.vegvesen.nvdbapi.client.model.datakatalog.AttributeTypeParameters;
import no.vegvesen.nvdbapi.client.model.datakatalog.FeatureTypeParameters;

import static no.vegvesen.nvdbapi.client.gson.GsonUtil.*;

public final class GuidanceParametersParser {

    private GuidanceParametersParser() {
    }

    public static AttributeTypeParameters parseAttributeType(JsonObject obj) {
        Boolean derived = parseBooleanMember(obj, "avledet");
        Boolean requiredValue = parseBooleanMember(obj, "obligatorisk_verdi");

        return new AttributeTypeParameters(derived, requiredValue);
    }

    public static FeatureTypeParameters parseFeatureType(JsonObject obj) {
        Boolean timeRelevant = parseBooleanMember(obj, "tidsrom_relevant");
        Boolean secType20K = parseBooleanMember(obj, "sektype_20k");
        Boolean isAbstract = parseBooleanMember(obj, "abstrakt_type");
        Boolean hasFiltration = parseBooleanMember(obj, "filtrering");
        Boolean isDerived = parseBooleanMember(obj, "avledet");
        Boolean needsParent = parseBooleanMember(obj, "må_ha_mor");
        Boolean isMeasureSet = parseBooleanMember(obj, "er_dataserie");
        String coverage = parseStringMember(obj, "dekningsgrad");
        Boolean overlaps = parseBooleanMember(obj, "overlapp");

        FeatureType.Relevant laneRelevant =
                FeatureType.Relevant.from(parseStringMember(obj, "kjørefelt_relevant"));
        FeatureType.Relevant sidePositionRelevant =
                FeatureType.Relevant.from(parseStringMember(obj, "sideposisjon_relevant"));
        Boolean heightRelevant = parseBooleanMember(obj, "høyde_relevant");
        Boolean directionRelevant = parseBooleanMember(obj, "retning_relevant");
        Boolean movable = parseBooleanMember(obj, "flyttbar");
        FeatureType.Survivability survivability =
                FeatureType.Survivability.from(parseStringMember(obj, "ajourhold_i"));
        FeatureType.Splitability split =
                FeatureType.Splitability.from(parseStringMember(obj, "ajourhold_splitt"));


        return new FeatureTypeParameters(timeRelevant, secType20K, isAbstract, hasFiltration, isDerived, needsParent,
                isMeasureSet, coverage, laneRelevant, sidePositionRelevant, directionRelevant, heightRelevant,
                overlaps, movable, survivability, split);
    }
}
