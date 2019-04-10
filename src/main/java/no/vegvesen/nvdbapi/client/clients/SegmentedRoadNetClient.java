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
import no.vegvesen.nvdbapi.client.clients.util.JerseyHelper;
import no.vegvesen.nvdbapi.client.gson.SegmentedLinkParser;
import no.vegvesen.nvdbapi.client.model.Page;
import no.vegvesen.nvdbapi.client.model.roadnet.SegmentedLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class SegmentedRoadNetClient extends AbstractJerseyClient {
    private static final Logger LOG = LoggerFactory.getLogger(RoadNetClient.class);

    public SegmentedRoadNetClient(String baseUrl, Client client) {
        super(baseUrl, client);
    }

    public List<SegmentedLink> getLinks(int id) {
        UriBuilder path = endpoint().path("/veglenkesekvenser/segmentert").path(Integer.toString(id));

        WebTarget target = getClient().target(path);
        JsonElement result = JerseyHelper.execute(target);
        if (result.isJsonArray()) {
            return StreamSupport.stream(result.getAsJsonArray().spliterator(), false)
                    .map(e -> SegmentedLinkParser.parse(e.getAsJsonObject()))
                    .collect(Collectors.toList());
        } else {
            return Collections.singletonList(SegmentedLinkParser.parse(result.getAsJsonObject()));
        }
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

    private WebTarget getWebTarget(RoadNetRequest request) {
        Objects.requireNonNull(request, "Missing page info argument.");

        UriBuilder path = endpoint().path("/veglenkesekvenser/segmentert");
        if (!request.getCounties().isEmpty()) path.queryParam("fylke", join(request.getCounties()));
        if (!request.getMunicipalities().isEmpty()) path.queryParam("kommune", join(request.getMunicipalities()));
        request.getBbox().ifPresent(v -> path.queryParam("kartutsnitt", v));
        request.getBpolygon().ifPresent(v -> path.queryParam("polygon", v));
        request.getProjection().ifPresent(v -> path.queryParam("srid", v.getSrid()));
        request.getRoadRefFilter().ifPresent(v -> path.queryParam("vegsystemreferanse", v));
        path.queryParam("historisk", request.isHistory());

        return getClient().target(path);
    }

    private static String join(List<Integer> list) {
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

    public final class SegmentedLinkResult extends GenericResultSet<SegmentedLink> {

        SegmentedLinkResult(WebTarget baseTarget, Page currentPage) {
            super(baseTarget, currentPage, SegmentedLinkParser::parse);
        }
    }

    public final class AsyncSegmentedLinkResult extends AsyncResult<SegmentedLink> {

        AsyncSegmentedLinkResult(WebTarget baseTarget, Page currentPage) {
            super(baseTarget, currentPage, SegmentedLinkParser::parse);
        }
    }
}
