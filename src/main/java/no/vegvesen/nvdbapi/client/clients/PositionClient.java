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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import no.vegvesen.nvdbapi.client.gson.PlacementParser;
import no.vegvesen.nvdbapi.client.model.Position;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.UriBuilder;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static no.vegvesen.nvdbapi.client.gson.GsonUtil.rt;

public class PositionClient extends AbstractJerseyClient {

    PositionClient(String baseurl, Client client, Consumer<AbstractJerseyClient> onClose) {
        super(baseurl, client, onClose);
    }

    public Position getPlacement(PositionRequest req) {
        UriBuilder url = getPositionEndpoint();

        req.getNorth().ifPresent(v -> url.queryParam("nord", v));
        req.getEast().ifPresent(v -> url.queryParam("ost", v));
        req.getLat().ifPresent(v -> url.queryParam("lat", v));
        req.getLon().ifPresent(v -> url.queryParam("lon", v));
        req.getProjection().ifPresent(v -> url.queryParam("srid", v.getSrid()));
        req.getMaxResults().ifPresent(v -> url.queryParam("maks_antall", v));
        req.getMaxDistance().ifPresent(v -> url.queryParam("maks_avstand", v));
        req.getConnectionLinks().ifPresent(v -> url.queryParam("konnekteringslenker", v));
        req.getDetailedLinks().ifPresent(v -> url.queryParam("detaljerte_lenker", v));
        req.getRoadRefFilters().ifPresent(v -> url.queryParam("vegsystemreferanse", v));
        req.getDateFilter().ifPresent(v -> url.queryParam("tidspunkt", v));
        req.getRoadUserGroup().ifPresent(v -> url.queryParam("trafikantgruppe", v.getTextValue()));

        WebTarget target = getClient().target(url);

        JsonArray results = JerseyHelper.execute(target).getAsJsonArray();

        List<Position.Result> collect =
                StreamSupport.stream(results.spliterator(), false)
                             .map(JsonElement::getAsJsonObject)
                             .map(rt(PlacementParser::parsePosition))
                             .collect(Collectors.toList());
        return new Position(collect);
    }

    private UriBuilder getPositionEndpoint() {
        return rootEndpoint().path("posisjon");
    }

    private UriBuilder rootEndpoint() {
        return start();
    }


}
