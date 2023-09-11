/*
 * Copyright (c) 2015-2019, Statens vegvesen
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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriBuilder;

import com.google.gson.JsonObject;
import no.vegvesen.nvdbapi.client.gson.RouteParser;
import no.vegvesen.nvdbapi.client.model.Coordinates;
import no.vegvesen.nvdbapi.client.model.Geometry;
import no.vegvesen.nvdbapi.client.model.Projection;
import no.vegvesen.nvdbapi.client.model.roadnet.TypeOfRoad;
import no.vegvesen.nvdbapi.client.model.roadnet.route.RouteOnRoadNet;

public class RoadNetRouteClient extends AbstractJerseyClient {
    static class RouteRequestField {
        static final String START = "start";
        static final String END = "slutt";
        static final String GEOMETRY = "geometri";
        static final String SRID = "srid";
        static final String BRIEF_RESPONSE = "kortform";
        static final String CONNECTION_LINKS = "konnekteringslenker";
        static final String DETAILED_LINKS = "detaljerte_lenker";
        static final String DISTANCE = "maks_avstand";
        static final String ENVELOPE = "omkrets";
        static final String ROAD_SYS_REFS = "vegsystemreferanse";
        static final String TYPE_OF_ROAD = "typeveg";
        static final String ROAD_USER_GROUP = "trafikantgruppe";
        static final String KEEP_ROAD_USER_GROUP = "behold_trafikantgruppe";
        static final String POINT_IN_TIME = "tidspunkt";
        static final String START_POINT_IN_TIME = "tidspunkt_start";
        static final String END_POINT_IN_TIME = "tidspunkt_slutt";
    }

    RoadNetRouteClient(String baseUrl, Client client, Consumer<AbstractJerseyClient> onClose) {
        super(baseUrl, client, onClose);
    }

    public RouteOnRoadNet getRouteOnRoadnet(RoadNetRouteRequest request) {
        WebTarget target = getWebTarget(request);
        JsonObject result = JerseyHelper.execute(target).getAsJsonObject();
        if (request.isBriefResponse()) {
            return RouteParser.parseBrief(result);
        } else {
            return RouteParser.parseDetailed(result);
        }
    }

    public RouteOnRoadNet postRouteOnRoadnet(RoadNetRouteRequest request) {
        WebTarget target = getWebTarget();
        Entity<Map<String, String>> entity = Entity.entity(getJsonObject(request), MediaType.APPLICATION_JSON);
        JsonObject result = JerseyHelper.execute(target, entity).getAsJsonObject();
        if (request.isBriefResponse()) {
            return RouteParser.parseBrief(result);
        } else {
            return RouteParser.parseDetailed(result);
        }
    }

    private Map<String, String> getJsonObject(RoadNetRouteRequest request) {
        Map<String, String> jsonMap = new HashMap<>();

        if (request.getStartReflinkPosition() != null) jsonMap.put(RouteRequestField.START, String.valueOf(request.getStartReflinkPosition()));
        if (request.getEndReflinkPosition() != null) jsonMap.put(RouteRequestField.END, String.valueOf(request.getEndReflinkPosition()));
        if (request.getGeometry() != null) jsonMap.put(RouteRequestField.GEOMETRY, request.getGeometry());
        jsonMap.put(RouteRequestField.DISTANCE, String.valueOf(request.getDistance()));
        jsonMap.put(RouteRequestField.ENVELOPE, String.valueOf(request.getEnvelope()));
        jsonMap.put(RouteRequestField.BRIEF_RESPONSE, String.valueOf(request.isBriefResponse()));
        jsonMap.put(RouteRequestField.CONNECTION_LINKS, String.valueOf(request.isConnectionLinks()));
        jsonMap.put(RouteRequestField.DETAILED_LINKS, String.valueOf(request.isDetailedLinks()));
        jsonMap.put(RouteRequestField.KEEP_ROAD_USER_GROUP, String.valueOf(request.isKeepRoadUserGroup()));
        request.getRoadRefFilter().ifPresent(s -> jsonMap.put(RouteRequestField.ROAD_SYS_REFS, s));
        request.getRoadUserGroup().ifPresent(userGroup -> jsonMap.put(RouteRequestField.ROAD_USER_GROUP, userGroup.getTextValue()));
        if (!request.getTypeOfRoad().isEmpty()) jsonMap.put(RouteRequestField.TYPE_OF_ROAD, request.getTypeOfRoad().stream().map(TypeOfRoad::getTypeOfRoadSosi).collect(Collectors.joining(",")));

        request.getPointInTime().ifPresent(pit -> jsonMap.put(RouteRequestField.POINT_IN_TIME, pit.toString()));
        request.getStartPointInTime().ifPresent(pit -> jsonMap.put(RouteRequestField.START_POINT_IN_TIME, pit.toString()));
        request.getEndPointInTime().ifPresent(pit -> jsonMap.put(RouteRequestField.END_POINT_IN_TIME, pit.toString()));

        return jsonMap;
    }

    private WebTarget getWebTarget() {
        return getClient().target(endpoint());
    }

    private WebTarget getWebTarget(RoadNetRouteRequest request) {
        Objects.requireNonNull(request, "Missing page info argument.");

        UriBuilder path = endpoint();

        request.getPointInTime().ifPresent(v -> path.queryParam(RouteRequestField.POINT_IN_TIME, v.toString()));
        request.getStartPointInTime().ifPresent(v -> path.queryParam(RouteRequestField.START_POINT_IN_TIME, v.toString()));
        request.getEndPointInTime().ifPresent(v -> path.queryParam(RouteRequestField.END_POINT_IN_TIME, v.toString()));
        path.queryParam(RouteRequestField.BRIEF_RESPONSE, request.isBriefResponse());
        path.queryParam(RouteRequestField.CONNECTION_LINKS, request.isConnectionLinks());
        path.queryParam(RouteRequestField.DETAILED_LINKS, request.isDetailedLinks());
        request.getRoadRefFilter().ifPresent(v -> path.queryParam(RouteRequestField.ROAD_SYS_REFS, v));
        request.getRoadUserGroup().ifPresent(v -> path.queryParam(RouteRequestField.ROAD_USER_GROUP, v.getTextValue()));
        path.queryParam(RouteRequestField.KEEP_ROAD_USER_GROUP, request.isKeepRoadUserGroup());
        if (!request.getTypeOfRoad().isEmpty()) path.queryParam(RouteRequestField.TYPE_OF_ROAD, request.getTypeOfRoad()
                .stream()
                .map(TypeOfRoad::getTypeOfRoadSosi)
                .collect(Collectors.joining(",")));

        if (request.usesGeometry()) {
            path.queryParam(RouteRequestField.GEOMETRY, request.getGeometry());
            path.queryParam(RouteRequestField.DISTANCE, request.getDistance());
            if (request.getProjection() != Projection.UTM33) {
                path.queryParam(RouteRequestField.SRID, request.getProjection().getSrid());
            }
        } else if(request.usesReflinkPosition()) {
            path.queryParam(RouteRequestField.START, request.getStartReflinkPosition());
            path.queryParam(RouteRequestField.END, request.getEndReflinkPosition());
        } else {
            Coordinates startCoordinates = request.getStartCoordinates();
            path.queryParam(RouteRequestField.START, startCoordinates);
            path.queryParam(RouteRequestField.END, request.getEndCoordinates());
            path.queryParam(RouteRequestField.DISTANCE, request.getDistance());
            path.queryParam(RouteRequestField.ENVELOPE, request.getEnvelope());

            if(startCoordinates.getProjection() != Projection.UTM33) {
                path.queryParam(RouteRequestField.SRID, startCoordinates.getProjection().getSrid());
            }
        }

        return getClient().target(path);
    }

    private UriBuilder endpoint() {
        return start().path("beta/vegnett/rute");
    }
}
