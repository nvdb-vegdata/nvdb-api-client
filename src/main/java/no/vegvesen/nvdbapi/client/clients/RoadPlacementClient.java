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
import com.google.gson.JsonObject;
import no.vegvesen.nvdbapi.client.clients.util.JerseyHelper;
import no.vegvesen.nvdbapi.client.gson.RoadPlacementParser;
import no.vegvesen.nvdbapi.client.model.Projection;
import no.vegvesen.nvdbapi.client.model.RoadPlacement;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;
import java.util.ArrayList;
import java.util.List;

public class RoadPlacementClient extends AbstractJerseyClient {

    protected RoadPlacementClient(String baseUrl, Client client) {
        super(baseUrl, client);
    }

    public RoadPlacement getRoadPlacement(RoadRefRequest request) {
        return getResults("vegreferanse", request.getQueryParam(), Projection.UTM33);
    }

    public RoadPlacement getRoadPlacement(RoadRefRequest request, Projection projection) {
        return getResults("vegreferanse", request.getQueryParam(), projection);
    }

    public RoadPlacement getRoadPlacement(RefLinkRequest request) {
        return getResults("veglenke", request.getQueryParam(), Projection.UTM33);
    }

    public RoadPlacement getRoadPlacement(RefLinkRequest request, Projection projection) {
        return getResults("veglenke", request.getQueryParam(), projection);
    }

    public List<RoadPlacement> getRoadPlacementsInBulk(List<RoadRefRequest> requests, Projection projection) {
        String queryParam = Joiner.on(",").join(requests);
        return getRoadPlacementsInBatch("vegreferanser", queryParam, projection);
    }

    public List<RoadPlacement> getRoadPlacementsInBulkFromReflinks(List<RefLinkRequest> requests, Projection projection) {
        String queryParam = Joiner.on(",").join(requests);
        return getRoadPlacementsInBatch("veglenker", queryParam, projection);
    }

    private List<RoadPlacement> getRoadPlacementsInBatch(String paramName, String queryParam, Projection projection) {
        UriBuilder url = getRoadPlacementBulkEndpoint();

        url.queryParam(paramName, queryParam);
        url.queryParam("srid", projection.getSrid());

        WebTarget target = getClient().target(url);

        JsonArray results = JerseyHelper.execute(target).getAsJsonArray();

        List<RoadPlacement> placements = new ArrayList<>();
        results.forEach(result -> placements.add(RoadPlacementParser.parseRoadPlacement(result.getAsJsonObject())));

        return placements;
    }

    private RoadPlacement getResults(String paramName, String queryParam, Projection projection) {
        UriBuilder url = getRoadPlacementEndpoint();

        url.queryParam(paramName, queryParam);
        url.queryParam("srid", projection.getSrid());

        WebTarget target = getClient().target(url);

        JsonObject result = JerseyHelper.execute(target).getAsJsonObject();

        return RoadPlacementParser.parseRoadPlacement(result);
    }

    private UriBuilder getRoadPlacementEndpoint() {
        return getRoot().path("/veg");
    }

    private UriBuilder getRoadPlacementBulkEndpoint() {
        return getRoadPlacementEndpoint().path("/batch");
    }

    private UriBuilder getRoot() {
        return start();
    }
}
