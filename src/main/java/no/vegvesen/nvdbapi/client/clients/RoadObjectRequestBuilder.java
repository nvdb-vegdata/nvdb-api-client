/*
 * Copyright (c) 2015-2017, Statens vegvesen
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 *  Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package no.vegvesen.nvdbapi.client.clients;

import no.vegvesen.nvdbapi.client.model.roadnet.DetailLevel;
import no.vegvesen.nvdbapi.client.model.roadnet.SeparatePassages;
import no.vegvesen.nvdbapi.client.model.roadnet.TypeOfRoad;

import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

class RoadObjectRequestBuilder {
    private RoadObjectRequestBuilder() {}

    static MultivaluedMap<String, String> convert(RoadObjectRequest request) {
        MultivaluedMap<String, String> map = new MultivaluedHashMap<>();

        // Single parameters
        request.getSegmented().ifPresent(v -> map.putSingle("segmentering", Boolean.toString(v)));
        request.getAllVersions().ifPresent(v -> map.putSingle("alle_versjoner", Boolean.toString(v)));
        request.getPointInTime().ifPresent(v -> map.putSingle("tidspunkt",
            v.format(DateTimeFormatter.ISO_DATE)));
        request.getArmFilter().ifPresent(v -> map.putSingle("arm", Boolean.toString(v)));
        request.getIntersectionFilter().ifPresent(v -> map.putSingle("kryssystem", Boolean.toString(v)));
        request.getSideAreaFilter().ifPresent(v -> map.putSingle("sideanlegg", Boolean.toString(v)));
        request.getRoadUserGroupFilter().ifPresent(v -> map.putSingle("trafikantgruppe", v.getTextValue()));
        fromSet(request.getSeparatePassagesFilter(), SeparatePassages::getTextValue).ifPresent(v -> map.putSingle("adskiltelop", v));
        request.getModifiedAfter().ifPresent(v -> map.putSingle("endret_etter",
                v.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
        request.getProjection().ifPresent(v -> map.putSingle("srid", Integer.toString(v.getSrid())));
        request.getDistanceTolerance().ifPresent(v -> map.putSingle("geometritoleranse", Integer.toString(v)));
        request.getDepth().ifPresent(v -> map.putSingle("dybde", v));
        getIncludeArgument(request.getIncludes()).ifPresent(v -> map.putSingle("inkluder", v));
        getIncludeGeometriesArgument(request.getIncludeGeometries()).ifPresent(v -> map.putSingle("inkludergeometri", v));
        getIncludeAttributesArgument(request.getIncludeAttributes()).ifPresent(v -> map.putSingle("inkluder_egenskaper", v));
        fromSet(request.getTypeOfRoadFilter(), TypeOfRoad::getTypeOfRoadSosi).ifPresent(v -> map.putSingle("typeveg", v));
        request.getAttributeFilter().ifPresent(v -> map.putSingle("egenskap", v));
        request.getBpolygon().ifPresent(v -> map.putSingle("polygon", v));
        request.getBbox().ifPresent(v -> map.putSingle("kartutsnitt", v));
        fromSet(request.getDetailLevel(), DetailLevel::getSosi).ifPresent(v -> map.putSingle("detaljniva", v));
        request.getRefLinkPartType().ifPresent(v -> map.putSingle("veglenketype", v.getRefLinkPartType()));
        request.getRoadRefFilter().ifPresent(v -> map.putSingle("vegsystemreferanse", v));
        request.getRefLinkFilter().ifPresent(v -> map.putSingle("veglenkesekvens", v));
        flatten(request.getMunicipalities()).ifPresent(v -> map.putSingle("kommune", v));
        flatten(request.getRoadobjectIds()).ifPresent(v -> map.putSingle("ider", v));
        flatten(request.getCounties()).ifPresent(v -> map.putSingle("fylke", v));
        flattenString(request.getContractAreas()).ifPresent(v -> map.putSingle("kontraktsomrade", v));
        flattenString(request.getNationalRoutes()).ifPresent(v -> map.putSingle("riksvegrute", v));
        flattenString(request.getStreets()).ifPresent(v -> map.putSingle("gate", v));

        // Multiple parameters
        request.getOverlapFilters().forEach(f -> map.add("overlapp", f.toString()));

        return map;
    }

    private static <T> Optional<String> fromSet(Set<T> values, Function<T, String> stringify){
        // Defaults
        if (values == null || values.isEmpty()) {
            return Optional.empty();
        }
        String val = values.stream()
                .map(stringify)
                .collect(Collectors.joining(","));
        return Optional.of(val);
    }

    private static Optional<String> getIncludeArgument(Set<RoadObjectClient.Include> values) {
        // Defaults
        if (values == null || values.isEmpty()) {
            return Optional.empty();
        }

        // "All" trumps any other values
        if (values.contains(RoadObjectClient.Include.ALL)) {
            return Optional.of(RoadObjectClient.Include.ALL.stringValue());
        }

        // "minimum" is redundant except when alone
        if (values.size() == 1 && values.contains(RoadObjectClient.Include.MINIMUM)) {
            return Optional.of(RoadObjectClient.Include.MINIMUM.stringValue());
        }

        String val = values.stream()
                           .filter(i -> i != RoadObjectClient.Include.MINIMUM)
                           .map(RoadObjectClient.Include::stringValue)
                           .collect(Collectors.joining(","));
        return Optional.of(val);
    }

    private static Optional<String> getIncludeGeometriesArgument(Set<RoadObjectClient.IncludeGeometry> values) {
        // Defaults
        if (values == null
                || values.isEmpty()
                || values.equals(RoadObjectClient.IncludeGeometry.all())) {
            return Optional.empty();
        }

        if (values.contains(RoadObjectClient.IncludeGeometry.NONE)) {
            return Optional.ofNullable(RoadObjectClient.IncludeGeometry.NONE.stringValue());
        }

        return Optional.of(values.stream()
                .map(RoadObjectClient.IncludeGeometry::stringValue)
                .collect(Collectors.joining(",")));
    }

    private static Optional<String> getIncludeAttributesArgument(Set<RoadObjectClient.IncludeAttribute> values) {
        // Defaults
        if (values == null
            || values.isEmpty()
            || values.equals(RoadObjectClient.IncludeAttribute.all())) {
            return Optional.empty();
        }

        return Optional.of(values.stream()
            .map(RoadObjectClient.IncludeAttribute::stringValue)
            .collect(Collectors.joining(",")));
    }

    private static Optional<String> flatten(List<?> set) {
        if (set.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(set.stream()
                              .map(Object::toString)
                              .collect(Collectors.joining(",")));
    }

    private static Optional<String> flattenString(List<String> set) {
        if (set.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(String.join(",", set));
    }
}
