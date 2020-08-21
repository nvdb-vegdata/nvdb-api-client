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

import com.google.gson.JsonArray;

import no.vegvesen.nvdbapi.client.clients.util.JerseyHelper;
import no.vegvesen.nvdbapi.client.gson.RouteParser;
import no.vegvesen.nvdbapi.client.model.Coordinates;
import no.vegvesen.nvdbapi.client.model.Geometry;
import no.vegvesen.nvdbapi.client.model.Projection;
import no.vegvesen.nvdbapi.client.model.roadnet.TypeOfRoad;
import no.vegvesen.nvdbapi.client.model.roadnet.route.RouteOnRoadNet;

public class RoadNetRouteClient extends AbstractJerseyClient {
    RoadNetRouteClient(String baseUrl, Client client, Consumer<AbstractJerseyClient> onClose) {
        super(baseUrl, client, onClose);
    }

    public RouteOnRoadNet getRouteOnRoadnet(RoadNetRouteRequest request) {
        WebTarget target = getWebTarget(request);
        JsonArray result = JerseyHelper.execute(target).getAsJsonArray();
        if (request.isBriefResponse()) {
            return RouteParser.parseBrief(result);
        } else {
            return RouteParser.parseDetailed(result);
        }
    }

    public RouteOnRoadNet postRouteOnRoadnet(RoadNetRouteRequest request) {
        WebTarget target = getWebTarget();
        Entity<Map<String, String>> entity = Entity.entity(request.getJsonObject(), MediaType.APPLICATION_JSON);
        JsonArray result = JerseyHelper.execute(target, entity).getAsJsonArray();
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

        request.getPointInTime().ifPresent(v -> path.queryParam("tidspunkt", v));
        request.getStartPointInTime().ifPresent(v -> path.queryParam("tidspunkt_start", v));
        request.getEndPointInTime().ifPresent(v -> path.queryParam("tidspunkt_slutt", v));
        path.queryParam("kortform", request.isBriefResponse());
        path.queryParam("konnekteringslenker", request.isConnectionLinks());
        path.queryParam("detaljerte_lenker", request.isDetailedLinks());
        request.getRoadRefFilter().ifPresent(v -> path.queryParam("vegsystemreferanse", v));
        request.getRoadUserGroup().ifPresent(v -> path.queryParam("trafikantgruppe", v.getTextValue()));
        if (!request.getTypeOfRoad().isEmpty()) path.queryParam("typeveg", request.getTypeOfRoad()
                .stream()
                .map(TypeOfRoad::getTypeOfRoadSosi)
                .collect(Collectors.joining(",")));

        if (request.usesGeometry()) {
            Geometry geometry = request.getGeometry();
            path.queryParam("geometri", geometry.getWkt());
            path.queryParam("maks_avstand", request.getDistanceThreshold());
            if (geometry.getProjection() != Projection.UTM33) {
                path.queryParam("srid", geometry.getProjection().getSrid());
            }
        } else if(request.usesReflinkPosition()) {
            path.queryParam("start", request.getStartReflinkPosition());
            path.queryParam("slutt", request.getEndReflinkPosition());
        } else {
            Coordinates startCoordinates = request.getStartCoordinates();
            path.queryParam("start", startCoordinates);
            path.queryParam("slutt", request.getEndCoordinates());
            path.queryParam("maks_avstand", request.getDistanceThreshold());
            path.queryParam("omkrets", request.getCircumferenceAroundPoints());

            if(startCoordinates.getProjection() != Projection.UTM33) {
                path.queryParam("srid", startCoordinates.getProjection().getSrid());
            }
        }

        return getClient().target(path);
    }

    private UriBuilder endpoint() {
        return start().path("beta/vegnett/rute");
    }
}
