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
import no.vegvesen.nvdbapi.client.model.roadnet.RoadUserGroup;
import no.vegvesen.nvdbapi.client.model.roadnet.SeparatePassages;
import no.vegvesen.nvdbapi.client.model.roadnet.roadsysref.*;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static no.vegvesen.nvdbapi.client.gson.GsonUtil.*;

public final class RoadSysRefParser {
    private RoadSysRefParser() {}

    public static RoadSysRef parse(JsonObject obj) {
        if (isNull(obj)) return null;

        if(!obj.has("vegsystem")) return  null;
        return new RoadSysRef(
            parseRoadSystem(obj.getAsJsonObject("vegsystem")),
            parseSection(obj.getAsJsonObject("strekning")),
            parseIntersection(obj.getAsJsonObject("kryssystem")),
            parseSideArea(obj.getAsJsonObject("sideanlegg")),
            parseStringMember(obj, "kortform"));
    }

    private static RoadSystem parseRoadSystem(JsonObject obj) {
        return new RoadSystem(
            parseLongMember(obj, "id"),
            parseIntMember(obj, "versjon"),
            parseIntMember(obj,"nummer"),
            RoadCategory.valueOf(parseStringMember(obj, "vegkategori")),
            Phase.valueOf(parseStringMember(obj, "fase")));
    }

    private static Double getFromMeter(JsonObject obj) {
        Double fromMeter = parseDoubleMember(obj, "fra_meter");
        if (isNull(fromMeter) ) {
            return parseDoubleMember(obj, "meter");
        }
        return fromMeter;
    }

    private static Double getToMeter(JsonObject obj) {
        Double toMeter = parseDoubleMember(obj, "til_meter");

        if (isNull(toMeter)) {
            return parseDoubleMember(obj, "meter");
        }
        return toMeter;
    }

    private static Section parseSection(JsonObject obj) {
        if (isNull(obj)) return null;
        return new Section(
            parseLongMember(obj, "id"),
            parseIntMember(obj, "versjon"),
            parseIntMember(obj, "strekning"),
            parseIntMember(obj, "delstrekning"),
            parseBooleanMember(obj, "arm"),
            parseOptionalStringMember(obj, "adskilte_løp").map(SeparatePassages::fromValue).orElse(null),
            parseOptionalStringMember(obj, "adskilte_løp_nummer").orElse(null),
            RoadUserGroup.fromValue(parseStringMember(obj, "trafikantgruppe")),
            getFromMeter(obj),
            getToMeter(obj),
            Direction.from(parseStringMember(obj, "retning")));
    }

    private static Intersection parseIntersection(JsonObject obj) {
        if (isNull(obj)) return null;

        Integer intersectionNumber = parseIntMember(obj, "kryssystem");
        if (nonNull(intersectionNumber)) {
            return new Intersection(
                parseLongMember(obj, "id"),
                parseIntMember(obj, "versjon"),
                intersectionNumber,
                parseIntMember(obj, "kryssdel"),
                getFromMeter(obj),
                getToMeter(obj),
                Direction.from(parseStringMember(obj, "retning")),
                RoadUserGroup.fromValue(parseStringMember(obj, "trafikantgruppe")));
        }
        return null;
    }

    private static SideArea parseSideArea(JsonObject obj) {
        if (isNull(obj)) return null;

        Integer sideAreaNumber = parseIntMember(obj, "sideanlegg");
        if (nonNull(sideAreaNumber)) {
            return new SideArea(
                parseLongMember(obj, "id"),
                parseIntMember(obj, "versjon"),
                sideAreaNumber,
                parseIntMember(obj, "sideanleggsdel"),
                getFromMeter(obj),
                getToMeter(obj),
                Direction.from(parseStringMember(obj, "retning")),
                RoadUserGroup.fromValue(parseStringMember(obj, "trafikantgruppe")));
        }
        return null;
    }

}
