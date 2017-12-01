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
import no.vegvesen.nvdbapi.client.model.Geometry;
import no.vegvesen.nvdbapi.client.model.roadnet.Link;
import no.vegvesen.nvdbapi.client.model.roadnet.Ltema;
import no.vegvesen.nvdbapi.client.model.roadnet.SosiMedium;
import no.vegvesen.nvdbapi.client.model.roadnet.TopologyLevel;
import no.vegvesen.nvdbapi.client.model.roadobjects.RoadRef;

import java.time.LocalDate;
import java.util.Optional;

import static no.vegvesen.nvdbapi.client.gson.GsonUtil.*;

public final class LinkParser {

    private LinkParser() {
    }

    public static Link parse(JsonObject obj) {
        // Metadata
        LocalDate fromDate = parseDateMember(obj, "metadata.startdato"), toDate = parseDateMember(obj, "metadata.sluttdato");

        long id = parseLongMember(obj, "veglenkeid");
        Double start = parseDoubleMember(obj, "startposisjon"), end = parseDoubleMember(obj, "sluttposisjon");
        String startNode = parseStringMember(obj, "startnode"), endNode = parseStringMember(obj, "sluttnode");

        boolean isConnectionLink = parseBooleanMember(obj, "konnekteringslenke");

        SosiMedium medium = Optional.ofNullable(parseStringMember(obj, "medium")).map(SosiMedium::from).orElse(null);
        Ltema ltema = Optional.ofNullable(parseIntMember(obj, "temakode")).map(Ltema::from).orElse(null);
        TopologyLevel level = Optional.ofNullable(parseIntMember(obj, "topologinivå")).map(TopologyLevel::from).orElse(null);

        // Areas
        Integer municipality = parseIntMember(obj, "kommune");
        Integer region = parseIntMember(obj, "region");
        Integer county = parseIntMember(obj, "fylke");
        Integer roadDepartment = parseIntMember(obj, "vegavdeling");

        // Geometry
        Geometry geo = null;
        if (obj.has("geometri")) {
            geo = GeometryParser.parse(obj.getAsJsonObject("geometri"));
        }

        RoadRef roadRef = null;
        if (obj.has("vegreferanse")) {
            roadRef = RoadRefParser.parse(obj.getAsJsonObject("vegreferanse"));
        }

        Long superLinkId = null;
        if(obj.has("foreldrelenkeid")) {
            superLinkId = parseLongMember(obj, "foreldrelenkeid");
        }

        return new Link(id, superLinkId, start, end, startNode, endNode, fromDate, toDate, medium, ltema, level, region, county, municipality, roadDepartment, geo, roadRef, isConnectionLink);
    }

}
