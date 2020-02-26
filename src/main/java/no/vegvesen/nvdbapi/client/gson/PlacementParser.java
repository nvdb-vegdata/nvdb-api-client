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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import no.vegvesen.nvdbapi.client.model.Direction;
import no.vegvesen.nvdbapi.client.model.Position;
import no.vegvesen.nvdbapi.client.model.RoadPlacement;
import no.vegvesen.nvdbapi.client.model.SidePosition;
import no.vegvesen.nvdbapi.client.model.roadobjects.Placement;
import no.vegvesen.nvdbapi.client.model.roadobjects.RefLinkExtentPlacement;
import no.vegvesen.nvdbapi.client.model.roadobjects.TurnExtentPlacement;

import static java.util.Objects.isNull;

import static no.vegvesen.nvdbapi.client.gson.GsonUtil.parseDoubleMember;
import static no.vegvesen.nvdbapi.client.gson.GsonUtil.parseLongMember;
import static no.vegvesen.nvdbapi.client.gson.GsonUtil.parseStringListMember;
import static no.vegvesen.nvdbapi.client.gson.GsonUtil.parseStringMember;

public final class PlacementParser {

    private PlacementParser() {
    }

    private static RoadPlacement parseRoadPlacement(JsonObject obj) {
        return RoadPlacementParser.parseRoadPlacement(obj);
    }

    public static Position.Result parsePosition(JsonObject obj) {
        RoadPlacement placement = parseRoadPlacement(obj);
        Double distance = obj.getAsJsonPrimitive("avstand").getAsDouble();

        return new Position.Result(placement, distance);
    }

    public static List<Position.Result> parseList(JsonArray array) {
        return StreamSupport.stream(array.spliterator(), false)
            .map(JsonElement::getAsJsonObject)
            .map(PlacementParser::parsePosition)
            .collect(Collectors.toList());
    }

    static Placement parsePlacement(JsonObject obj) {
        return parsePlacement(obj, "startposisjon", "sluttposisjon");
    }

    public static RefLinkExtentPlacement parseRefLinkExtentPlacement(JsonObject obj) {
        return parseRefLinkExtentPlacement(obj, "startposisjon", "sluttposisjon");
    }

    private static Placement parsePlacement(JsonObject obj, String startPosField, String endPosField) {
        if (isNull(obj)) return null;
        String type = parseStringMember(obj, "type");
        if (type != null && type.equalsIgnoreCase("sving")) {
            return parseTurnExtentPlacement(obj, startPosField, endPosField);
        }

        return parseRefLinkExtentPlacement(obj, startPosField, endPosField);
    }

    private static TurnExtentPlacement parseTurnExtentPlacement(JsonObject obj, String startPosField,
                                                                String endPosField) {
        if (isNull(obj)) return null;
        Long nodeid = parseLongMember(obj, "nodeid");
        RefLinkExtentPlacement startposisjon = parseRefLinkExtentPlacement(obj.getAsJsonObject("startpunkt"),
            startPosField, endPosField);
        RefLinkExtentPlacement sluttposisjon = parseRefLinkExtentPlacement(obj.getAsJsonObject("sluttpunkt"),
            startPosField, endPosField);

        return new TurnExtentPlacement(nodeid, startposisjon, sluttposisjon);
    }

    private static RefLinkExtentPlacement parseRefLinkExtentPlacement(JsonObject obj, String startPosField,
                                                                      String endPosField) {
        if (isNull(obj)) return null;

        long netElementId = parseLongMember(obj, "veglenkesekvensid");

        double startPos, endPos;
        if (obj.has("relativPosisjon")) {
            startPos = endPos = parseDoubleMember(obj, "relativPosisjon");
        } else {
            startPos = parseDoubleMember(obj, startPosField);
            endPos = parseDoubleMember(obj, endPosField);
        }

        Direction dir = Optional.ofNullable(parseStringMember(obj, "retning"))
            .map(Direction::from)
            .orElse(null);
        SidePosition sidePos = Optional.ofNullable(parseStringMember(obj, "sideposisjon"))
            .map(SidePosition::from)
            .orElse(null);
        List<String> lane = parseStringListMember(obj, "kjørefelt");

        return new RefLinkExtentPlacement(netElementId, startPos, endPos, dir, sidePos, lane);
    }

}
