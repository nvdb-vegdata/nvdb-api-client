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

package no.vegvesen.nvdbapi.client.clients;

import java.io.InputStream;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.StreamSupport;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import no.vegvesen.nvdbapi.client.gson.RoadObjectParser;
import no.vegvesen.nvdbapi.client.model.Page;
import no.vegvesen.nvdbapi.client.model.roadobjects.RoadObject;
import no.vegvesen.nvdbapi.client.model.roadobjects.RoadObjectAttribute;
import no.vegvesen.nvdbapi.client.model.roadobjects.RoadObjectType;
import no.vegvesen.nvdbapi.client.model.roadobjects.RoadObjectTypeWithStats;
import no.vegvesen.nvdbapi.client.model.roadobjects.Statistics;

import static java.lang.String.valueOf;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import static no.vegvesen.nvdbapi.client.clients.RoadObjectRequest.DEFAULT;
import static no.vegvesen.nvdbapi.client.clients.RoadObjectRequestBuilder.convert;
import static no.vegvesen.nvdbapi.client.clients.JerseyHelper.MEDIA_TYPE;
import static no.vegvesen.nvdbapi.client.clients.JerseyHelper.execute;
import static no.vegvesen.nvdbapi.client.clients.JerseyHelper.isSuccess;
import static no.vegvesen.nvdbapi.client.clients.JerseyHelper.parseError;
import static no.vegvesen.nvdbapi.client.gson.GsonUtil.rt;

public class RoadObjectClient extends AbstractJerseyClient {
    private static final Logger logger = LoggerFactory.getLogger(RoadObjectClient.class);

    RoadObjectClient(String baseUrl, Client client, Consumer<AbstractJerseyClient> onClose) {
        super(baseUrl, client, onClose);
    }

    public Statistics getStats(int featureTypeId, RoadObjectRequest request) {
        UriBuilder path = start(featureTypeId).path("statistikk");

        applyRequestParameters(path, convert(request));
        logger.debug("Invoking {}", path);
        WebTarget target = getClient().target(path);

        JsonElement e = execute(target);
        return rt(RoadObjectParser::parseStatistics).apply(e.getAsJsonObject());
    }

    public List<Statistics> getRootStats(RoadObjectRequest request) {
        UriBuilder path = start().path("statistikk");

        applyRequestParameters(path, convert(request));
        logger.debug("Invoking {}", path);
        WebTarget target = getClient().target(path);

        JsonElement e = execute(target);
        return StreamSupport.stream(e.getAsJsonArray().spliterator(),true)
                .map(obj -> rt(RoadObjectParser::parseStatistics).apply(obj.getAsJsonObject().get("statistikk").getAsJsonObject()))
                .collect(toList());
    }

    public RoadObjectsResult getRoadObjects(int featureTypeId) {
        return getRoadObjects(featureTypeId, DEFAULT);
    }

    public AsyncRoadObjectsResult getRoadObjectsAsync(int featureTypeId) {
        return getRoadObjectsAsync(featureTypeId, DEFAULT);
    }

    public List<RoadObjectType> getRoadObjectTypes(){
        UriBuilder path = start();
        WebTarget target = getClient().target(path);

        JsonArray e = execute(target).getAsJsonArray();

        return StreamSupport.stream(e.getAsJsonArray().spliterator(), false)
            .map(JsonElement::getAsJsonObject)
            .map(rt(RoadObjectParser::parseRoadObjectType))
            .collect(toList());
    }

    /**
     * This method can be used when you desire complete control of which query parameters is sent to the API
     * @param featureTypeId Get object with feature type id
     * @param queryParameters raw query parameters
     * @return {@code RoadObjectsResult} for query
     */
    public RoadObjectsResult getRoadObjects(int featureTypeId, MultivaluedMap<String, String> queryParameters) {
        UriBuilder path = start(featureTypeId);

        applyRequestParameters(path, queryParameters);
        WebTarget target = getClient().target(path);

        return new RoadObjectsResult(target, extractPage(queryParameters));
    }

    public RoadObjectsResult getRoadObjects(int featureTypeId, RoadObjectRequest request) {
        WebTarget target = getWebTarget(featureTypeId, request);

        return new RoadObjectsResult(target,
            request.getPage()
        );
    }

    public AsyncRoadObjectsResult getRoadObjectsAsync(int featureTypeId, RoadObjectRequest request) {
        WebTarget target = getWebTarget(featureTypeId, request);

        return new AsyncRoadObjectsResult(target,
            request.getPage()
        );
    }

    private WebTarget getWebTarget(int featureTypeId, RoadObjectRequest request) {
        UriBuilder path = start(featureTypeId);

        applyRequestParameters(path, convert(request));
        return getClient().target(path);
    }

    private Page extractPage(MultivaluedMap<String, String> params) {
        if (params.containsKey("antall")) {
            return Page.count(Integer.parseInt(params.getFirst("antall")));
        }

        return Page.defaults();
    }

    public RoadObject getRoadObject(long featureId, RoadObjectRequest request) {
        return getRoadObject(null, featureId, request);
    }
    public RoadObject getRoadObject(long featureId) {
        return getRoadObject(featureId, DEFAULT);
    }

    public RoadObject getRoadObject(int featureTypeId, long featureId) {
        return getRoadObject(featureTypeId, featureId, DEFAULT);
    }

    public RoadObject getRoadObject(int featureTypeId, long featureId, RoadObjectRequest request) {
        return getRoadObject(Integer.valueOf(featureTypeId), featureId, request);
    }

    private RoadObject getRoadObject(Integer featureTypeId, long featureId, RoadObjectRequest request) {
        UriBuilder path = featureTypeId != null
                ? start(featureTypeId).path(valueOf(featureId))
                : startShortcut().queryParam("id", valueOf(featureId));

        logger.debug("Invoking {}", path);
        applyRequestParameters(path, convert(request));

        WebTarget target = getClient().target(path);

        JsonObject obj = execute(target).getAsJsonObject();

        return rt(RoadObjectParser::parse).apply(obj);
    }

    public List<RoadObject> getRoadObjectVersions(int featureTypeId, long featureId) {
        return getRoadObjectVersions(featureTypeId, featureId, DEFAULT);
    }

    public List<RoadObject> getRoadObjectVersions(int featureTypeId, long featureId, RoadObjectRequest roadObjectRequest) {
        UriBuilder path = start(featureTypeId).path(valueOf(featureId)).path("versjoner");

        logger.debug("Invoking {}", path);
        applyRequestParameters(path, convert(roadObjectRequest));

        WebTarget target = getClient().target(path);

        JsonArray e = execute(target).getAsJsonArray();
        return StreamSupport.stream(e.spliterator(), false)
            .map(JsonElement::getAsJsonObject)
            .map(rt(RoadObjectParser::parse))
            .collect(toList());
    }

    public RoadObject getRoadObjectVersion(int featureTypeId, long featureId, int version){
        return getRoadObjectVersion(featureTypeId, featureId, version, DEFAULT);
    }

    public RoadObject getRoadObjectVersion(int featureTypeId, long featureId, int version, RoadObjectRequest roadObjectRequest){
        UriBuilder path = start(featureTypeId).path(valueOf(featureId)).path(valueOf(version));

        logger.debug("Invoking {}", path);
        applyRequestParameters(path, convert(roadObjectRequest));

        WebTarget target = getClient().target(path);

        JsonObject obj = execute(target).getAsJsonObject();
        return rt(RoadObjectParser::parse).apply(obj);
    }

    public RoadObjectAttribute getBinaryAttributeRoadObject(int featureTypeId, long featureId, int version, int attributeId, int blobId){
        UriBuilder path = start(featureTypeId).path(valueOf(featureId)).path(valueOf(version))
            .path("egenskaper").path(valueOf(attributeId)).path(valueOf(blobId)).path("binaer");

        logger.debug("Invoking {}", path);
        WebTarget target = getClient().target(path);

        Invocation invocation = target.request().accept(MEDIA_TYPE).buildGet();
        Response response = execute(invocation, Response.class);

        if (!isSuccess(response)) {
            throw parseError(response);
        }

        List<String> contentTypes = new ArrayList<>();
        for(Object o : response.getHeaders().get("Content-Type")){
            contentTypes.add(o.toString());
        }
        InputStream inputStream = response.readEntity(InputStream.class);

        return new RoadObjectAttribute(contentTypes, inputStream);
    }

    private static void applyRequestParameters(UriBuilder path, MultivaluedMap<String, String> params) {
        params.forEach((k, values) -> path.queryParam(k, values.toArray(new Object[0])));
    }

    public List<RoadObjectTypeWithStats> getSummary() {
        return getSummary(DEFAULT);
    }

    public List<RoadObjectTypeWithStats> getSummary(RoadObjectRequest request) {
        UriBuilder path = start().path("statistikk");
        applyRequestParameters(path, convert(request));
        WebTarget target = getClient().target(path);
        logger.debug("Invoking {}", path);
        JsonArray array = execute(target).getAsJsonArray();
        return StreamSupport.stream(array.spliterator(), false)
            .map(JsonElement::getAsJsonObject)
            .map(rt(RoadObjectParser::parseRoadObjectTypeWithStats))
            .collect(toList());
    }

    private UriBuilder start(int typeId) {
        return start().path(valueOf(typeId));
    }
    private UriBuilder startShortcut(){
        return super.start().path("vegobjekt");
    }

    @Override
    protected UriBuilder start() {
        return super.start().path("vegobjekter");
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
            Set<Include> includes = all();
            includes.removeAll(asList(without));
            return includes;
        }

        public String stringValue() {
            return value;
        }
    }

    public enum IncludeGeometry {
        ATTRIBUTE("egenskap"),
        LOCATION("lokasjon"),
        DERIVED("utledet"),
        NONE("ingen");

        private final String value;
        IncludeGeometry(String stringValue) {
            this.value = stringValue;
        }

        public String stringValue() {
            return value;
        }

        public static Set<IncludeGeometry> all() {
            return EnumSet.of(ATTRIBUTE, LOCATION, DERIVED);
        }
    }

    public enum IncludeAttribute {
        BASE("basis"),
        ASSOCIATIONS("assosiasjon"),
        PLACEMENTS("stedfesting"),
        GEOMETRY("geometri");

        private final String value;
        IncludeAttribute(String stringValue) {
            this.value = stringValue;
        }

        public String stringValue() {
            return value;
        }

        public static Set<IncludeAttribute> all() {
            return EnumSet.allOf(IncludeAttribute.class);
        }
    }

    public enum IncludeStats {
        ALLE("alle"),
        LENGDE("lengde"),
        ANTALL("antall");

        private final String value;
        IncludeStats(String stringValue) {
            this.value = stringValue;
        }

        public String stringValue() {
            return value;
        }

        public static Set<IncludeStats> all() {
            return EnumSet.allOf(IncludeStats.class);
        }
    }

    public static class RoadObjectsResult extends GenericResultSet<RoadObject> {

        public RoadObjectsResult(WebTarget baseTarget,
                                 Page currentPage) {
            super(baseTarget, currentPage, rt(RoadObjectParser::parse));
        }
    }

    public static class AsyncRoadObjectsResult extends AsyncResult<RoadObject> {

        public AsyncRoadObjectsResult(WebTarget baseTarget,
                                      Page currentPage) {
            super(baseTarget, currentPage, rt(RoadObjectParser::parse));
        }
    }
}
