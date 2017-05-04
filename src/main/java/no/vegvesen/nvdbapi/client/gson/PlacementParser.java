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
import no.vegvesen.nvdbapi.client.model.Geometry;
import no.vegvesen.nvdbapi.client.model.Position;
import no.vegvesen.nvdbapi.client.model.RefLinkPosition;
import no.vegvesen.nvdbapi.client.model.RoadPlacement;
import no.vegvesen.nvdbapi.client.model.roadobjects.RoadRef;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public final class PlacementParser {

    private PlacementParser() {}

    public static RoadPlacement parsePlacement(JsonObject obj) {
        RoadRef roadRef = RoadRefParser.parse(obj.getAsJsonObject("vegreferanse"));
        RefLinkPosition refLink = ShortRefLinkParser.parseShortRefLink(obj.getAsJsonObject("veglenke"));
        Geometry point = GeometryParser.parse(obj.getAsJsonObject("geometri"));

        return new RoadPlacement(roadRef, refLink, point);
    }

    public static Position.Result parsePosition(JsonObject obj) {
        RoadPlacement placement = parsePlacement(obj);
        Double distance = obj.getAsJsonPrimitive("avstand").getAsDouble();

        return new Position.Result(placement, distance);
    }

    public static List<Position.Result> parseList(JsonArray array) {
        return StreamSupport.stream(array.spliterator(), false)
                        .map(JsonElement::getAsJsonObject)
                        .map(PlacementParser::parsePosition)
                        .collect(Collectors.toList());
    }

}
