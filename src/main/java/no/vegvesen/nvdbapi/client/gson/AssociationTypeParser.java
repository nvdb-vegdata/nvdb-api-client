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
import no.vegvesen.nvdbapi.client.model.datakatalog.AssociationType;

import java.time.LocalDate;
import java.util.Optional;

import static no.vegvesen.nvdbapi.client.gson.GsonUtil.*;

public final class AssociationTypeParser {

    private AssociationTypeParser() {
    }

    public static AssociationType parse(JsonObject object) {

        if (object.has("innhold")) {
            Integer listId = parseIntMember(object, "id");
            Integer maxNumber = parseIntMember(object, "maksimalt_antall_verdier");
            Integer minNumber = parseIntMember(object, "minimalt_antall_verdier");

            JsonObject content = object.getAsJsonObject("innhold");
            return parseAssociation(content, listId, maxNumber, minNumber);
        } else {
            return parseAssociation(object, null, null, null);
        }
    }

    private static AssociationType parseAssociation(JsonObject object, Integer listId, Integer maxNumber, Integer minNumber) {
        Integer id = parseIntMember(object, "id");
        LocalDate validFrom = parseDateMember(object, "objektliste_dato");

        AssociationType.InsideParent insideParent =
                Optional.ofNullable(parseStringMember(object, "innenfor_mor"))
                    .map(AssociationType.InsideParent::from).orElse(null);
        AssociationType.Affiliation affiliation =
                Optional.ofNullable(parseStringMember(object, "relasjonstype"))
                    .map(AssociationType.Affiliation::from).orElse(null);
        Integer featureTypeId = null;
        if (object.has("type")) {
            featureTypeId = parseIntMember(object.getAsJsonObject("type"), "id");
        }

        return new AssociationType(id, featureTypeId, insideParent, affiliation, validFrom, listId, maxNumber, minNumber);
    }
}
