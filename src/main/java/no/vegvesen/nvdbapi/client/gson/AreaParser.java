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
import no.vegvesen.nvdbapi.client.model.areas.*;

import static no.vegvesen.nvdbapi.client.gson.GsonUtil.parseIntMember;
import static no.vegvesen.nvdbapi.client.gson.GsonUtil.parseLongMember;
import static no.vegvesen.nvdbapi.client.gson.GsonUtil.parseStringMember;

public final class AreaParser {

    private AreaParser() {}

    public static Municipality parseMun(JsonObject obj) {
        String name = parseStringMember(obj, "navn");
        int number = parseIntMember(obj, "nummer");
        Geometry boundingBox = parseGeometry(obj, "kartutsnitt");
        Geometry centerPoint = parseGeometry(obj, "senterpunkt");

        Integer county = parseIntMember(obj, "fylke");

        return new Municipality(
                parseId(obj),
                number,
                name,
                county,
                boundingBox,
                centerPoint);
    }

    public static County parseCounty(JsonObject obj) {
        String name = parseStringMember(obj, "navn");
        int number = parseIntMember(obj, "nummer");
        Geometry boundingBox = parseGeometry(obj, "kartutsnitt");
        Geometry centerPoint = parseGeometry(obj, "senterpunkt");

        return new County(parseId(obj), number, name, boundingBox, centerPoint);
    }

    public static Route parseRoute(JsonObject obj) {
        return new Route(
                parseLongMember(obj, "id"),
                parseStringMember(obj, "nummer"),
                parseStringMember(obj, "navn"),
                parseStringMember(obj, "beskrivelse"),
                parseStringMember(obj, "periode"));
    }

    public static ContractArea parseContractArea(JsonObject obj) {
        return new ContractArea(
                parseLongMember(obj, "id"),
                parseIntMember(obj, "nummer"),
                parseStringMember(obj, "navn"),
                parseStringMember(obj, "type"));
    }

    private static RoadObjectId parseId(JsonObject obj) {
        RoadObjectId id = null;
        if (obj.has("vegobjekt")) {
            long fid = parseLongMember(obj, "vegobjekt.id");
            int tid = parseIntMember(obj, "vegobjekt.type");
            id = new RoadObjectId(tid, fid);
        }
        return id;
    }

    private static Geometry parseGeometry(JsonObject obj, String fieldName) {
        if (!obj.has(fieldName)) {
            return null;
        }
        return GeometryParser.parse(obj.getAsJsonObject(fieldName));
    }



}
