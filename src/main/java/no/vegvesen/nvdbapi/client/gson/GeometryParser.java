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

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import no.vegvesen.nvdbapi.client.model.Geometry;
import no.vegvesen.nvdbapi.client.model.GeometryAttributes;
import no.vegvesen.nvdbapi.client.model.Projection;
import no.vegvesen.nvdbapi.client.model.Quality;

import java.util.Optional;

import static no.vegvesen.nvdbapi.client.gson.GsonUtil.*;

public final class GeometryParser {
    private GeometryParser() {}

    public static Geometry parse(JsonObject obj) {
        if (obj==null) return null;

        String wkt = parseStringMember(obj, "wkt");
        Projection srid = getNode(obj, "srid").map(GeometryParser::parseProjection).orElse(null);

        boolean isSimplified = Optional.ofNullable(parseBooleanMember(obj, "forenklet")).orElse(false);
        boolean isOwnGeometry = Optional.ofNullable(parseBooleanMember(obj, "egengeometri")).orElse(false);

        GeometryAttributes geometryAttributes = getGeometryAttributes(obj);

        return new Geometry(wkt, srid, isSimplified, isOwnGeometry, geometryAttributes);
    }

    private static GeometryAttributes getGeometryAttributes(JsonObject obj) {
        return new GeometryAttributes(
            parseDateMember(obj, "datafangstdato"),
            parseDateMember(obj, "verifiseringsdato"),
            parseDateMember(obj, "oppdateringsdato"),
            parseStringMember(obj, "prosesshistorikk"),
            parseIntMember(obj, "kommune"),
            parseStringMember(obj, "medium"),
            parseStringMember(obj, "sosinavn"),
            parseIntMember(obj, "temakode"),
            parseBooleanMember(obj, "referansegeometri"),
            parseDoubleMember(obj, "lengde"),
            parseIntMember(obj, "høydereferanse"),
            getQuality(obj)
        );
    }

    public static Geometry parseAttribute(JsonObject obj) {
        if (obj==null) return null;

        String wkt = parseStringMember(obj, "verdi");
        Projection srid = Projection.UTM33;

        GeometryAttributes geometryAttributes = getGeometryAttributes(obj);

        return new Geometry(wkt, srid, false, true, geometryAttributes);
    }

    private static Quality getQuality(JsonObject obj) {
        if (obj.has("kvalitet")) {
            Integer method = parseIntMember(obj, "kvalitet.målemetode");
            Integer accuracy = parseIntMember(obj, "kvalitet.nøyaktighet");
            Integer heightMethod = parseIntMember(obj, "kvalitet.målemetodeHøyde");
            Integer heightAccuracy = parseIntMember(obj, "kvalitet.nøyaktighetHøyde");
            Integer tolerance = parseIntMember(obj, "kvalitet.maksimaltAvvik");
            Integer visibility = parseIntMember(obj, "kvalitet.synbarhet");
            String captureMethod = parseStringMember(obj, "kvalitet.datafangstmetode");
            String heightCaptureMethod = parseStringMember(obj, "kvalitet.datafangstmetodeHøyde");
            return new Quality(method, accuracy, heightMethod, heightAccuracy, tolerance, visibility, captureMethod, heightCaptureMethod);
        } else {
            return null;
        }
    }

    private static Projection parseProjection(JsonElement e) {
        return Projection.of(e.getAsInt()).orElse(null);
    }
}
