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

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.UriBuilder;

import com.google.gson.JsonElement;

import no.vegvesen.nvdbapi.client.gson.RoadNetParser;
import no.vegvesen.nvdbapi.client.model.Page;
import no.vegvesen.nvdbapi.client.model.roadnet.LinkSequence;
import no.vegvesen.nvdbapi.client.model.roadnet.Node;
import no.vegvesen.nvdbapi.client.model.roadnet.TopologyLevel;

import static no.vegvesen.nvdbapi.client.gson.GsonUtil.rt;

public class RoadNetClient extends AbstractJerseyClient {
    RoadNetClient(String baseUrl, Client client, Consumer<AbstractJerseyClient> onClose) {
        super(baseUrl, client, onClose);
    }

    public LinkSequence getLinkSequence(long linksequenceId) {
        UriBuilder path = endpoint().path("/veglenkesekvenser").path(Long.toString(linksequenceId));
        WebTarget target = getClient().target(path);
        JsonElement result = JerseyHelper.execute(target);

        return rt(RoadNetParser::parseLinkSequence).apply(result.getAsJsonObject());
    }

    public Node getNode(long nodeId) {
        UriBuilder path = endpoint().path("/noder").path(Long.toString(nodeId));
        WebTarget target = getClient().target(path);
        JsonElement result = JerseyHelper.execute(target);

        return rt(RoadNetParser::parseNode).apply(result.getAsJsonObject());
    }

    public LinkResult getLinkSequences() {
        return getLinkSequences(RoadNetRequest.DEFAULT);
    }

    public NodeResult getNodes() {
        return getNodes(RoadNetRequest.DEFAULT);
    }

    public AsyncLinkResult getLinkSequencesAsync() {
        return getLinkSequencesAsync(RoadNetRequest.DEFAULT);
    }

    public AsyncNodeResult getNodesAsync() {
        return getNodesAsync(RoadNetRequest.DEFAULT);
    }

    public LinkResult getLinkSequences(RoadNetRequest request) {
        WebTarget target = getWebTarget(request, "/veglenkesekvenser");
        return new LinkResult(target, request.getPage());
    }

    public AsyncLinkResult getLinkSequencesAsync(RoadNetRequest request) {
        WebTarget target = getWebTarget(request, "/veglenkesekvenser");
        return new AsyncLinkResult(target, request.getPage());
    }

    public NodeResult getNodes(RoadNetRequest request) {
        WebTarget target = getWebTarget(request, "/noder");
        return new NodeResult(target, request.getPage());
    }

    public AsyncNodeResult getNodesAsync(RoadNetRequest request) {
        WebTarget target = getWebTarget(request, "/noder");
        return new AsyncNodeResult(target, request.getPage());
    }

    private WebTarget getWebTarget(RoadNetRequest request, String p) {
        Objects.requireNonNull(request, "Missing page info argument.");

        UriBuilder path = endpoint().path(p);
        addParameters(request, path);

        return getClient().target(path);
    }

    protected static String join(List<?> list) {
        if (list == null) {
            return null;
        }
        return list.stream()
                .map(Objects::toString)
                .collect(Collectors.joining(","));
    }


    private void addParameters(RoadNetRequest request, UriBuilder path) {
        if (!request.getCounties().isEmpty()) path.queryParam("fylke", join(request.getCounties()));
        if (!request.getTopologyLevel().isEmpty()) {
            path.queryParam("topologiniva",
                    join(request.getTopologyLevel()
                            .stream()
                            .map(TopologyLevel::getApiValue)
                            .collect(Collectors.toList())));
        }
        if (!request.getSuperId().isEmpty()) path.queryParam("superid", join(request.getSuperId()));
        if (!request.getMunicipalities().isEmpty()) path.queryParam("kommune", join(request.getMunicipalities()));
        if (!request.getId().isEmpty()) path.queryParam("ider", join(request.getId()));
        request.getBbox().ifPresent(v -> path.queryParam("kartutsnitt", v));
        request.getBpolygon().ifPresent(v -> path.queryParam("polygon", v));
        request.getProjection().ifPresent(v -> path.queryParam("srid", v.getSrid()));
        request.getRoadRefFilter().ifPresent(v -> path.queryParam("vegsystemreferanse", v));
        request.getContractArea().ifPresent(v -> path.queryParam("kontraktsomrade", v));
        request.getNationalRoute().ifPresent(v -> path.queryParam("riksvegrute", v));
        request.getStreet().ifPresent(v -> path.queryParam("gate", v));
    }

    private UriBuilder endpoint() {
        return start().path("vegnett");
    }

    public static final class AsyncLinkResult extends AsyncResult<LinkSequence> {
        AsyncLinkResult(WebTarget baseTarget, Page currentPage) {
            super(baseTarget, currentPage, rt(RoadNetParser::parseLinkSequence));
        }
    }

    public static final class LinkResult extends GenericResultSet<LinkSequence> {
        LinkResult(WebTarget baseTarget, Page currentPage) {
            super(baseTarget, currentPage, rt(RoadNetParser::parseLinkSequence));
        }
    }

    public static final class NodeResult extends GenericResultSet<Node> {
        NodeResult(WebTarget baseTarget, Page currentPage) {
            super(baseTarget, currentPage, rt(RoadNetParser::parseNode));
        }
    }

    public static final class AsyncNodeResult extends AsyncResult<Node> {
        AsyncNodeResult(WebTarget baseTarget, Page currentPage) {
            super(baseTarget, currentPage, rt(RoadNetParser::parseNode));
        }
    }

}
