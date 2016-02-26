/*
 * Copyright (c) 2015-2016, Statens vegvesen
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

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import no.vegvesen.nvdbapi.client.model.Geometry;
import no.vegvesen.nvdbapi.client.model.Projection;
import no.vegvesen.nvdbapi.client.model.Quality;

import java.time.LocalDate;
import java.util.Optional;

import static no.vegvesen.nvdbapi.client.gson.GsonUtil.*;

public final class GeometryParser {
    private GeometryParser() {
    }

    public static Geometry parse(JsonObject obj) {
        String wkt = parseStringMember(obj, "wkt");
        Projection srid = getNode(obj, "srid").map(GeometryParser::parseProjection).orElse(null);

        boolean isSimplified = Optional.ofNullable(parseBooleanMember(obj, "forenklet")).orElse(false);
        boolean isOwnGeometry = Optional.ofNullable(parseBooleanMember(obj, "egengeometri")).orElse(false);
        Quality quality = null;
        if (obj.has("kvalitet")) {
            int method = parseIntMember(obj, "kvalitet.metode");
            Integer accuracy = parseIntMember(obj, "kvalitet.nøyaktighet");
            Integer heightMethod = parseIntMember(obj, "kvalitet.høydemetode");
            int heightAccuracy = parseIntMember(obj, "kvalitet.høydenøyaktighet");
            int tolerance = parseIntMember(obj, "kvalitet.toleranse");
            int visibility = parseIntMember(obj, "kvalitet.synlighet");
            LocalDate verifiedDate = parseDateMember(obj, "kvalitet.datafangstdato");
            quality = new Quality(method, accuracy, heightMethod, heightAccuracy, tolerance, visibility, verifiedDate);
        }

        return new Geometry(wkt, srid, quality, isSimplified, isOwnGeometry);
    }

    public static Projection parseProjection(JsonElement e) {
        switch (e.getAsInt()) {
            case 32633:
                return Projection.UTM33;
            case 4326:
                return Projection.WGS84;
            default:
                return null;
        }
    }
}
