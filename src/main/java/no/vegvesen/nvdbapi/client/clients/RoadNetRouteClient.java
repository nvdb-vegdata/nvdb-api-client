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

import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.google.gson.JsonObject;
import no.vegvesen.nvdbapi.client.gson.RouteParser;
import no.vegvesen.nvdbapi.client.model.Coordinates;
import no.vegvesen.nvdbapi.client.model.Geometry;
import no.vegvesen.nvdbapi.client.model.Projection;
import no.vegvesen.nvdbapi.client.model.roadnet.TypeOfRoad;
import no.vegvesen.nvdbapi.client.model.roadnet.route.RouteField;
import no.vegvesen.nvdbapi.client.model.roadnet.route.RouteOnRoadNet;

public class RoadNetRouteClient extends AbstractJerseyClient {
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
        Entity<Map<String, String>> entity = Entity.entity(request.getJsonObject(), MediaType.APPLICATION_JSON);
        JsonObject result = JerseyHelper.execute(target, entity).getAsJsonObject();
        if (request.isBriefResponse()) {
            return RouteParser.parseBrief(result);
        } else {
            return RouteParser.parseDetailed(result);
        }
    }

    private WebTarget getWebTarget() {
        return getClient().target(endpoint());
    }

    private WebTarget getWebTarget(RoadNetRouteRequest request) {
        Objects.requireNonNull(request, "Missing page info argument.");

        UriBuilder path = endpoint();

        request.getPointInTime().ifPresent(v -> path.queryParam(RouteField.POINT_IN_TIME.getName(), v));
        request.getStartPointInTime().ifPresent(v -> path.queryParam(RouteField.START_POINT_IN_TIME.getName(), v));
        request.getEndPointInTime().ifPresent(v -> path.queryParam(RouteField.END_POINT_IN_TIME.getName(), v));
        path.queryParam(RouteField.BRIEF_RESPONSE.getName(), request.isBriefResponse());
        path.queryParam(RouteField.CONNECTION_LINKS.getName(), request.isConnectionLinks());
        path.queryParam(RouteField.DETAILED_LINKS.getName(), request.isDetailedLinks());
        request.getRoadRefFilter().ifPresent(v -> path.queryParam(RouteField.ROAD_SYS_REFS.getName(), v));
        request.getRoadUserGroup().ifPresent(v -> path.queryParam(RouteField.ROAD_USER_GROUP.getName(), v.getTextValue()));
        if (!request.getTypeOfRoad().isEmpty()) path.queryParam(RouteField.TYPE_OF_ROAD.getName(), request.getTypeOfRoad()
                .stream()
                .map(TypeOfRoad::getTypeOfRoadSosi)
                .collect(Collectors.joining(",")));

        if (request.usesGeometry()) {
            Geometry geometry = request.getGeometry();
            path.queryParam(RouteField.GEOMETRY.getName(), geometry.getWkt());
            path.queryParam(RouteField.DISTANCE.getName(), request.getDistance());
            if (geometry.getProjection() != Projection.UTM33) {
                path.queryParam(RouteField.SRID.getName(), geometry.getProjection().getSrid());
            }
        } else if(request.usesReflinkPosition()) {
            path.queryParam(RouteField.START.getName(), request.getStartReflinkPosition());
            path.queryParam(RouteField.END.getName(), request.getEndReflinkPosition());
        } else {
            Coordinates startCoordinates = request.getStartCoordinates();
            path.queryParam(RouteField.START.getName(), startCoordinates);
            path.queryParam(RouteField.END.getName(), request.getEndCoordinates());
            path.queryParam(RouteField.DISTANCE.getName(), request.getDistance());
            path.queryParam(RouteField.ENVELOPE.getName(), request.getEnvelope());

            if(startCoordinates.getProjection() != Projection.UTM33) {
                path.queryParam(RouteField.SRID.getName(), startCoordinates.getProjection().getSrid());
            }
        }

        return getClient().target(path);
    }

    private UriBuilder endpoint() {
        return start().path("beta/vegnett/rute");
    }
}
