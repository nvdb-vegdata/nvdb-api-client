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

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.UriBuilder;

import com.google.gson.JsonElement;

import no.vegvesen.nvdbapi.client.gson.SegmentedLinkParser;
import no.vegvesen.nvdbapi.client.model.Page;
import no.vegvesen.nvdbapi.client.model.roadnet.DetailLevel;
import no.vegvesen.nvdbapi.client.model.roadnet.SegmentedLink;
import no.vegvesen.nvdbapi.client.model.roadnet.TopologyLevel;
import no.vegvesen.nvdbapi.client.model.roadnet.TypeOfRoad;

import static no.vegvesen.nvdbapi.client.gson.GsonUtil.rt;

public class SegmentedRoadNetClient extends AbstractJerseyClient {

    SegmentedRoadNetClient(String baseUrl, Client client, Consumer<AbstractJerseyClient> onClose) {
        super(baseUrl, client, onClose);
    }

    public List<SegmentedLink> getLinks(long linksequenceId) {
        UriBuilder path = endpoint().path("/veglenkesekvenser/segmentert").path(Long.toString(linksequenceId));

        WebTarget target = getClient().target(path);
        return getLinks(target);
    }

    public List<SegmentedLink> getLinks(long linksequenceId, RoadNetRequest request) {
        WebTarget target = getWebTarget(linksequenceId, request);

        return getLinks(target);
    }

    public SegmentedLinkResult getLinks() {
        return getLinks(RoadNetRequest.DEFAULT);
    }

    public AsyncSegmentedLinkResult getLinksAsync() {
        return getLinksAsync(RoadNetRequest.DEFAULT);
    }

    public SegmentedLinkResult getLinks(RoadNetRequest request) {
        WebTarget target = getWebTarget(request);
        return new SegmentedLinkResult(target, request.getPage());
    }

    public AsyncSegmentedLinkResult getLinksAsync(RoadNetRequest request) {
        WebTarget target = getWebTarget(request);
        return new AsyncSegmentedLinkResult(target, request.getPage());
    }

    private List<SegmentedLink> getLinks(WebTarget target) {
        JsonElement result = JerseyHelper.execute(target);
        if (result.isJsonArray()) {
            return StreamSupport.stream(result.getAsJsonArray().spliterator(), false)
                .map(JsonElement::getAsJsonObject)
                .map(rt(SegmentedLinkParser::parse))
                .collect(Collectors.toList());
        } else {
            return Collections.singletonList(SegmentedLinkParser.parse(result.getAsJsonObject()));
        }
    }

    private WebTarget getWebTarget(long linksequenceId, RoadNetRequest request) {
        return getWebTarget(request, endpoint().path("/veglenkesekvenser/segmentert").path(Long.toString(linksequenceId)));
    }

    private WebTarget getWebTarget(RoadNetRequest request) {
        return getWebTarget(request, endpoint().path("/veglenkesekvenser/segmentert"));
    }

    private WebTarget getWebTarget(RoadNetRequest request, UriBuilder path) {
        Objects.requireNonNull(request, "Missing page info argument.");

        if (!request.getId().isEmpty()) path.queryParam("ider", join(request.getId()));
        if (!request.getCounties().isEmpty()) path.queryParam("fylke", join(request.getCounties()));
        if (!request.getMunicipalities().isEmpty()) path.queryParam("kommune", join(request.getMunicipalities()));
        request.getContractArea().ifPresent(v -> path.queryParam("kontraktsomrade", v));
        request.getNationalRoute().ifPresent(v -> path.queryParam("riksvegrute", v));
        request.getStreet().ifPresent(v -> path.queryParam("gate", v));
        request.getBbox().ifPresent(v -> path.queryParam("kartutsnitt", v));
        request.getBpolygon().ifPresent(v -> path.queryParam("polygon", v));
        request.getProjection().ifPresent(v -> path.queryParam("srid", v.getSrid()));
        request.getRoadRefFilter().ifPresent(v -> path.queryParam("vegsystemreferanse", v));
        request.getArmFilter().ifPresent(v -> path.queryParam("arm", v));
        request.getIntersectionFilter().ifPresent(v -> path.queryParam("kryssystem", v));
        request.getSideAreaFilter().ifPresent(v -> path.queryParam("sideanlegg", v));
        request.getRoadUserGroupFilter().ifPresent(v -> path.queryParam("trafikantgruppe", v.getTextValue()));
        request.getSeparatePassagesFilter().ifPresent(v -> path.queryParam("adskiltelop", v.getTextValue()));
        serializeSet(request.getTypeOfRoadFilter(), TypeOfRoad::getTypeOfRoadSosi)
                .ifPresent(v -> path.queryParam("typeveg", v));
        request.getRefLinkPartTypeFilter().ifPresent(v -> path.queryParam("veglenketype", v.getRefLinkPartType()));
        serializeSet(request.getDetailLevelFilter(), DetailLevel::getSosi)
                .ifPresent(v -> path.queryParam("detaljniva", v));
        if (!request.getTopologyLevel().isEmpty()) {
            String topologiniva = request.getTopologyLevel()
                .stream()
                .map(TopologyLevel::getApiValue)
                .collect(Collectors.joining(","));
            path.queryParam("topologiniva", topologiniva);
        }

        path.queryParam("historisk", request.isHistory());
        request.getDateFilter()
            .ifPresent(v -> path.queryParam("tidspunkt", v.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));

        return getClient().target(path);
    }

    private static <T> Optional<String> serializeSet(Set<T> values, Function<T, String> serialize){
        // Defaults
        if (values == null || values.isEmpty()) {
            return Optional.empty();
        }
        String val = values.stream()
                .map(serialize)
                .collect(Collectors.joining(","));
        return Optional.of(val);

    }

    private static String join(List<?> list) {
        if (list == null) {
            return null;
        }
        return list.stream()
            .map(Objects::toString)
            .collect(Collectors.joining(","));
    }

    private UriBuilder endpoint() {
        return start().path("vegnett");
    }

    public static final class SegmentedLinkResult extends GenericResultSet<SegmentedLink> {

        SegmentedLinkResult(WebTarget baseTarget, Page currentPage) {
            super(baseTarget, currentPage, rt(SegmentedLinkParser::parse));
        }
    }

    public static final class AsyncSegmentedLinkResult extends AsyncResult<SegmentedLink> {

        AsyncSegmentedLinkResult(WebTarget baseTarget, Page currentPage) {
            super(baseTarget, currentPage, rt(SegmentedLinkParser::parse));
        }
    }
}
