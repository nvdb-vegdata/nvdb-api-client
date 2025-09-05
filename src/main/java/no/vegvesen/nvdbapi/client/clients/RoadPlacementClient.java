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

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import no.vegvesen.nvdbapi.client.gson.RoadPlacementParser;
import no.vegvesen.nvdbapi.client.model.Projection;
import no.vegvesen.nvdbapi.client.model.RoadPlacement;
import no.vegvesen.nvdbapi.client.model.RoadPlacementBulkResult;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static no.vegvesen.nvdbapi.client.gson.GsonUtil.rt;

public class RoadPlacementClient extends AbstractJerseyClient {

    RoadPlacementClient(String baseUrl, Client client, Consumer<AbstractJerseyClient> onClose) {
        super(baseUrl, client, onClose);
    }

    /**
     * Search for a placement by road sys ref.
     * @param request search parameters
     * @return {@code Optional<RoadPlacement>} if query had result, otherwise {@code Optional.empty()}
     */
    public Optional<RoadPlacement> findPlacement(RoadSysRefRequest request) {
        return getResults("vegsystemreferanse", request.getQueryParam(), request.getMunicipality().orElse(null),
                request.getProjection().orElse(null), request.getDateFilter().orElse(null));
    }

    /**
     * Search for a placement by road sys ref with municipality in the roadsysref.
     * @param request search parameters
     * @param doubleMunicipality is used to set municipality as 'kommune' parameter as well as in the
     *                           roadSysRef, to trigger exception.
     * @return {@code Optional<RoadPlacement>} if query had result, otherwise {@code Optional.empty()}
     */
    public Optional<RoadPlacement> findPlacementWithMunicipality(RoadSysRefRequest request, boolean doubleMunicipality) {
        return getResults("vegsystemreferanse", request.getQueryParamWithMunicipality(),
                doubleMunicipality ? request.getMunicipality().orElse(null) : null,
                request.getProjection().orElse(null), request.getDateFilter().orElse(null));
    }

    /**
     * Search for a placement by ref link.
     * @param request search parameters
     * @return {@code Optional<RoadPlacement>} if query had result, otherwise {@code Optional.empty()}
     */
    public Optional<RoadPlacement> findPlacement(RefLinkRequest request) {
        return getResults("veglenkesekvens", request.getQueryParam(), null,
                request.getProjection().orElse(null), request.getDateFilter().orElse(null));
    }

    public List<RoadPlacementBulkResult> getRoadPlacementsInBulk(List<RoadSysRefRequest> requests, Integer municipality, Projection projection, LocalDate dateFilter) {
        String queryParam = requests.stream().map(RoadSysRefRequest::getQueryParam).collect(Collectors.joining(","));
        return getRoadPlacementsInBatch("vegsystemreferanser", queryParam, municipality, projection, dateFilter);
    }

    public List<RoadPlacementBulkResult> getRoadPlacementsInBulk(List<RoadSysRefRequest> requests, Integer municipality, Projection projection) {
        String queryParam = requests.stream().map(RoadSysRefRequest::getQueryParam).collect(Collectors.joining(","));
        return getRoadPlacementsInBatch("vegsystemreferanser", queryParam, municipality, projection, null);
    }

    public List<RoadPlacementBulkResult> getRoadPlacementsInBulkFromReflinks(List<RefLinkRequest> requests, Projection projection) {
        String queryParam = requests.stream().map(RefLinkRequest::getQueryParam).collect(Collectors.joining(","));
        return getRoadPlacementsInBatch("veglenkesekvenser", queryParam, null, projection, null);
    }

    private List<RoadPlacementBulkResult> getRoadPlacementsInBatch(String paramName, String queryParam, Integer municipality, Projection projection, LocalDate dateFilter) {
        UriBuilder url = bulkEndpoint();

        url.queryParam(paramName, queryParam);
        Optional.ofNullable(municipality).ifPresent(p -> url.queryParam("kommune", municipality));
        Optional.ofNullable(projection).ifPresent(p -> url.queryParam("srid", projection.getSrid()));
        Optional.ofNullable(dateFilter).ifPresent(p -> url.queryParam("tidspunkt", dateFilter));

        WebTarget target = getClient().target(url);

        JsonObject resultMap = JerseyHelper.execute(target).getAsJsonObject();

        return resultMap.entrySet().stream()
            .map(r -> RoadPlacementParser.parseRoadPlacementBulkResult(r.getKey(), r.getValue()))
            .collect(Collectors.toList());
    }

    private Optional<RoadPlacement> getResults(String paramName, String queryParam, Integer municipality, Projection projection, LocalDate dateFilter) {
        UriBuilder url = endpoint();

        url.queryParam(paramName, queryParam);
        Optional.ofNullable(municipality).ifPresent(p -> url.queryParam("kommune", municipality));
        Optional.ofNullable(projection).ifPresent(p -> url.queryParam("srid", projection.getSrid()));
        Optional.ofNullable(dateFilter).ifPresent(p -> url.queryParam("tidspunkt", dateFilter));

        WebTarget target = getClient().target(url);

        return JerseyHelper.executeOptional(target)
                .map(JsonElement::getAsJsonObject)
                .map(rt(RoadPlacementParser::parseRoadPlacement));
    }

    private UriBuilder endpoint() {
        return start().path("/veg");
    }

    private UriBuilder bulkEndpoint() {
        return endpoint().path("/batch");
    }

}
