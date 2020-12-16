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
import no.vegvesen.nvdbapi.client.model.Direction;
import no.vegvesen.nvdbapi.client.model.SidePosition;
import no.vegvesen.nvdbapi.client.model.roadnet.*;
import no.vegvesen.nvdbapi.client.model.roadobjects.RefLinkExtentPlacement;

import static no.vegvesen.nvdbapi.client.gson.GsonUtil.*;
import static no.vegvesen.nvdbapi.client.gson.RoadObjectParser.*;

public final class SegmentedLinkParser {

    private SegmentedLinkParser() {
    }

    public static SegmentedLink parse(JsonObject obj) {
        return new SegmentedLink(
                parseLongMember(obj, "veglenkesekvensid"),
                parseDoubleMember(obj, "startposisjon"),
                parseDoubleMember(obj, "sluttposisjon"),
                parseIntMember(obj, "veglenkenummer"),
                parseIntMember(obj, "segmentnummer"),
                DetailLevel.fromTextValue(parseStringMember(obj, "detaljnivå")),
                TopologyLevel.fromValue(parseStringMember(obj, "topologinivå")),
                TypeOfRoad.fromTextValue(parseStringMember(obj, "typeVeg")),
                parseStringMember(obj, "startnode"),
                parseStringMember(obj, "sluttnode"),
                parseSuperlinkExtent(obj),
                parseDateMember(obj, "metadata.startdato"),
                parseDateMember(obj, "metadata.sluttdato"),
                parseIntMember(obj, "fylke"),
                parseIntMember(obj, "kommune"),
                GsonUtil.parseGeometryMember(obj, "geometri"),
                parseDoubleMember(obj, "lengde"),
                GsonUtil.parseRoadSysRefMember(obj, "vegsystemreferanse"),
                RefLinkPartType.fromValue(parseStringMember(obj,"type")),
                parseContractAreas(obj),
                parseRoutes(obj),
                parseStreet(obj));
    }

    private static RefLinkExtentPlacement parseSuperlinkExtent(JsonObject obj) {
        Long superLinkId = parseLongMember(obj, "superstedfesting.veglenkesekvensid");
        if(superLinkId != null) {
            return new RefLinkExtentPlacement(
                superLinkId,
                parseDoubleMember(obj, "superstedfesting.startposisjon"),
                parseDoubleMember(obj, "superstedfesting.sluttposisjon"),
                parseOptionalStringMember(obj, "superstedfesting.retning").map(Direction::from).orElse(null),
                parseOptionalStringMember(obj, "superstedfesting.sideposisjon").map(SidePosition::from).orElse(null),
                parseStringListMember(obj, "superstedfesting.kjørefelt")
            );
        } else {
            return null;
        }
    }

}
