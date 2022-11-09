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
import no.vegvesen.nvdbapi.client.model.roadobjects.RoadRef;

import static no.vegvesen.nvdbapi.client.gson.GsonUtil.parseIntMember;
import static no.vegvesen.nvdbapi.client.gson.GsonUtil.parseStringMember;

public final class RoadRefParser {
    private RoadRefParser() {}

    public static RoadRef parse(JsonObject obj) {
        Integer county = parseIntMember(obj, "fylke");
        Integer municipality = parseIntMember(obj, "kommune");

        String category = parseStringMember(obj, "kategori");
        String status = parseStringMember(obj, "status");
        Integer number = parseIntMember(obj, "nummer");
        String shortName = parseStringMember(obj, "kortform");

        if (obj.has("hp")) {
            Integer hp = parseIntMember(obj, "hp");
            if (obj.has("fra_meter")) {
                Integer fromMeter = parseIntMember(obj, "fra_meter");
                Integer toMeter = parseIntMember(obj, "til_meter");

                return RoadRef.stretch(county, municipality, category, status, number, hp, fromMeter, toMeter, shortName);
            } else {
                Integer meter = parseIntMember(obj, "meter");

                return RoadRef.point(county, municipality, category, status, number, hp, meter, shortName);
            }
        } else {
            Integer fromHp = parseIntMember(obj, "fra_hp");
            Integer toHp = parseIntMember(obj, "til_hp");
            Integer fromMeter = parseIntMember(obj, "fra_meter");
            Integer toMeter = parseIntMember(obj, "til_meter");

            return RoadRef.merged(county, municipality, category, status, number, fromHp, toHp, fromMeter, toMeter, shortName);
        }
    }
}
