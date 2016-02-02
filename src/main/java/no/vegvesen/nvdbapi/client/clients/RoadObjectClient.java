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

package no.vegvesen.nvdbapi.client.clients;

import com.google.common.base.Joiner;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import no.vegvesen.nvdbapi.client.clients.util.JerseyHelper;
import no.vegvesen.nvdbapi.client.gson.ChangesParser;
import no.vegvesen.nvdbapi.client.gson.RoadObjectParser;
import no.vegvesen.nvdbapi.client.model.Change;
import no.vegvesen.nvdbapi.client.model.Page;
import no.vegvesen.nvdbapi.client.model.datakatalog.DataType;
import no.vegvesen.nvdbapi.client.model.datakatalog.Datakatalog;
import no.vegvesen.nvdbapi.client.model.roadobjects.Attribute;
import no.vegvesen.nvdbapi.client.model.roadobjects.RoadObject;
import no.vegvesen.nvdbapi.client.model.roadobjects.Statistics;
import no.vegvesen.nvdbapi.client.util.ArgUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class RoadObjectClient extends AbstractJerseyClient {
    private static final Logger logger = LoggerFactory.getLogger(RoadObjectClient.class);
    private final Datakatalog datakatalog;

    protected RoadObjectClient(String baseUrl, Client client, Datakatalog datakatalog) {
        super(baseUrl, client);
        this.datakatalog = datakatalog;
    }

    public Datakatalog getDatakatalog() {
        return datakatalog;
    }

    public Attribute getAttribute(int featureTypeId, long featureId, int attributeTypeId) {
        UriBuilder path = start()
                .path(String.format("/vegobjekter/%d/%d/egenskaper/%d", featureTypeId, featureId, attributeTypeId));
        WebTarget target = getClient().target(path);
        JsonElement e = JerseyHelper.execute(target);
        return RoadObjectParser.parseAttribute(datakatalog.getDataTypeMap(), e.getAsJsonObject());
    }

    public Stream<Attribute> getAttributes(int featureTypeId, Stream<Long> featureIds, int attributeTypeId) {
        return featureIds.map(id -> getAttribute(featureTypeId, id, attributeTypeId));
    }

    public Statistics getStats(int featureTypeId, RoadObjectRequest request) {
        UriBuilder path = start()
                .path(String.format("/vegobjekter/%d/statistikk", featureTypeId));

        applyRequestParameters(path, request);
        WebTarget target = getClient().target(path);

        JsonElement e = JerseyHelper.execute(target);
        return RoadObjectParser.parseStatistics(e.getAsJsonObject());
    }

    public RoadObjectsResult getRoadObjects(int featureTypeId) {
        return getRoadObjects(featureTypeId, RoadObjectRequest.DEFAULT);
    }

    public RoadObjectsResult getRoadObjects(int featureTypeId, RoadObjectRequest request) {
        UriBuilder path = start()
                .path(String.format("/vegobjekter/%d", featureTypeId));

        applyRequestParameters(path, request);
        WebTarget target = getClient().target(path);

        return new RoadObjectsResult(target, Optional.ofNullable(request.getPage()), datakatalog);
    }

    public RoadObject getRoadObject(int featureTypeId, long featureId) {
        return getRoadObject(featureTypeId, featureId, RoadObjectRequest.DEFAULT);
    }

    public RoadObject getRoadObject(int featureTypeId, long featureId, RoadObjectRequest request) {
        UriBuilder path = start()
                .path(String.format("/vegobjekter/%d/%d", featureTypeId, featureId));

        applyRequestParameters(path, request);

        WebTarget target = getClient().target(path);

        JsonObject obj = JerseyHelper.execute(target).getAsJsonObject();
        return RoadObjectParser.parse(datakatalog.getDataTypeMap(), obj);
    }

    public ChangesResult getChanges(int typeId, LocalDate from, Page page, Change.Type type) {
        return getChanges(typeId, from.atStartOfDay(), page, type);
    }

    public ChangesResult getChanges(int typeId, LocalDateTime from, Page page, Change.Type type) {
        Objects.requireNonNull(from, "Missing from argument!");

        UriBuilder path = start()
                    .path(String.format("/vegobjekter/%d/endringer", typeId))
                    .queryParam("etter", ArgUtil.date(from))
                    .queryParam("type", type.getArgValue());

        WebTarget target = getClient().target(path);

        return new ChangesResult(datakatalog.getDataTypeMap(), typeId, target, Optional.ofNullable(page));
    }

    private static void applyRequestParameters(UriBuilder path, RoadObjectRequest request) {
        // Paging is left to the RoadObjectsResult class
        request.getSegmented().ifPresent(v -> path.queryParam("segmentering", v));
        request.getProjection().ifPresent(v -> path.queryParam("srid", v.getSrid()));
        request.getDistanceTolerance().ifPresent(v -> path.queryParam("geometritoleranse", v));
        request.getDepth().ifPresent(v -> path.queryParam("dybde", v));
        getIncludeArgument(request.getIncludes()).ifPresent(v -> path.queryParam("inkluder", v));
        request.getAttributeFilter().ifPresent(v -> path.queryParam("egenskap", v));
        request.getOverlapFilters().forEach(f -> path.queryParam("overlapp", f.toString()));
        request.getBbox().ifPresent(v -> path.queryParam("kartutsnitt", v));
        request.getRoadRefFilter().ifPresent(v -> path.queryParam("vegreferanse", v));
        flatten(request.getMunicipalities()).ifPresent(v -> path.queryParam("kommune", v));
        flatten(request.getCounties()).ifPresent(v -> path.queryParam("fylke", v));
        flatten(request.getRegions()).ifPresent(v -> path.queryParam("region", v));
        flatten(request.getRoadDepartments()).ifPresent(v -> path.queryParam("vegavdeling", v));
        flattenString(request.getContractAreas()).ifPresent(v -> path.queryParam("kontraktsomrade", v));
        flattenString(request.getNationalRoutes()).ifPresent(v -> path.queryParam("riksvegrute", v));
    }

    private static Optional<String> flatten(List<Integer> set) {
        if (set.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(set.stream().map(i -> i.toString()).collect(Collectors.joining(",")));
    }

    private static Optional<String> flattenString(List<String> set) {
        if (set.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(Joiner.on(",").join(set));
    }

    public List<Attribute> getAttributes(int featureTypeId, long featureId) {
        UriBuilder path = start()
                .path(String.format("/vegobjekter/%d/%d/egenskaper", featureTypeId, featureId));
        WebTarget target = getClient().target(path);

        JsonArray array = JerseyHelper.execute(target).getAsJsonArray();
        return StreamSupport.stream(array.spliterator(), false)
                .map(e -> e.getAsJsonObject())
                .map(o -> RoadObjectParser.parseAttribute(datakatalog.getDataTypeMap(), o))
                .collect(Collectors.toList());
    }

    private static Optional<String> getIncludeArgument(Include... informationToInclude) {
        Set<Include> values = informationToInclude != null && informationToInclude.length > 0 ? new HashSet<>(Arrays.asList(informationToInclude)) : Collections.emptySet();
        return getIncludeArgument(values);
    }

    private static Optional<String> getIncludeArgument(Set<Include> values) {
        // Defaults
        if (values.isEmpty()) {
            return Optional.empty();
        }

        // "All" trumps any other values
        if (values.contains(Include.ALL)) {
            return Optional.of(Include.ALL.value);
        }

        // "minimum" is redundant except when alone
        if (values.size() == 1 && values.contains(Include.MINIMUM)) {
            return Optional.of(Include.MINIMUM.value);
        }

        String val = values.stream().filter(i -> i != Include.MINIMUM).map(i -> i.value).collect(Collectors.joining(","));
        return Optional.of(val);
    }

    public enum Include {
        MINIMUM("minimum"),
        METADATA("metadata"),
        ATTRIBUTES("egenskaper"),
        ASSOCIATIONS("relasjoner"),
        LOCATION("lokasjon"),
        ROAD_SEGMENTS("vegsegmenter"),
        GEOMETRI("geometri"),
        ALL("alle");

        private final String value;

        Include(String value) {
            this.value = value;
        }

        public static Set<Include> all() {
            return EnumSet.complementOf(EnumSet.of(Include.MINIMUM, Include.ALL));
        }

        public static Set<Include> not(Include... without) {
            return all().stream().filter(v -> !Arrays.asList(without).contains(v)).collect(Collectors.toSet());
        }

        public String stringValue() {
            return value;
        }
    }

    public static class RoadObjectsResult extends GenericResultSet<RoadObject> {

        public RoadObjectsResult(WebTarget baseTarget, Optional<Page> currentPage, Datakatalog datakatalog) {
            super(baseTarget, currentPage, o -> RoadObjectParser.parse(datakatalog.getDataTypeMap(), o));
        }
    }

    public static class ChangesResult extends GenericResultSet<Change> {

        public ChangesResult(Map<Integer, DataType> dataTypes, int typeId, WebTarget baseTarget, Optional<Page> currentPage) {
            super(baseTarget, currentPage, obj -> ChangesParser.parse(dataTypes, obj, typeId));
        }
    }
}
