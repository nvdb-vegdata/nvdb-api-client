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
import com.google.gson.JsonObject;
import no.vegvesen.nvdbapi.client.gson.AreaParser;
import no.vegvesen.nvdbapi.client.model.Page;
import no.vegvesen.nvdbapi.client.model.Projection;
import no.vegvesen.nvdbapi.client.model.areas.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static no.vegvesen.nvdbapi.client.gson.GsonUtil.rt;

public class AreaClient extends AbstractJerseyClient {

    AreaClient(String baseUrl, Client client, Consumer<AbstractJerseyClient> onClose) {
        super(baseUrl, client, onClose);
    }

    public List<Municipality> getMunicipalities(boolean includeBoundingBox, boolean includeCenterPoint, boolean includeObjectLink, Projection projection) {
        WebTarget target = getClient().target(areaRoot().path("kommuner"));

        return getAreas(includeBoundingBox, includeCenterPoint, includeObjectLink, projection, target)
            .map(rt(AreaParser::parseMun))
            .collect(Collectors.toList());
    }

    public List<Municipality> getMunicipalities() {
        return getMunicipalities(true, true, false, null);
    }

    public List<County> getCountys(boolean includeBoundingBox, boolean includeCenterPoint, boolean includeObjectLink, Projection projection) {
        WebTarget target = getClient().target(areaRoot().path("fylker"));

        return getAreas(includeBoundingBox, includeCenterPoint, includeObjectLink, projection, target)
            .map(rt(AreaParser::parseCounty))
            .collect(Collectors.toList());
    }

    public List<County> getCountys() {
        return getCountys(true, true, false, null);
    }

    public List<Route> getNationalRoutes(boolean includeObjectLink) {
        UriBuilder path = areaRoot().path("riksvegruter");

        if (includeObjectLink) path.queryParam("inkluder", getIncludeParameter(false, false, true));

        WebTarget target = getClient().target(path);

        return getAreas(target)
            .map(rt(AreaParser::parseRoute))
            .collect(Collectors.toList());
    }

    public StreetsResult getStreets(StreetRequest request) {
        WebTarget target = getStreetWebTarget(request.includeObjectLink());

        return new StreetsResult(target, Page.defaults().withCount(request.getPageSize()));
    }

    public AsyncStreetsResult getStreetsAsync(StreetRequest request) {
        WebTarget target = getStreetWebTarget(request.includeObjectLink());

        return new AsyncStreetsResult(target, Page.defaults().withCount(request.getPageSize()));
    }

    public StreetsResult getStreets() {
        return getStreets(new StreetRequest(1000, true));
    }

    private WebTarget getStreetWebTarget(boolean includeObjectLink) {
        UriBuilder path = areaRoot().path("gater");

        if (includeObjectLink) path.queryParam("inkluder", getIncludeParameter(false, false, true));

        return getClient().target(path);
    }

    public List<Route> getNationalRoutes() {
        return getNationalRoutes(false);
    }

    public List<ContractArea> getContractAreas(boolean includeObjectLink) {
        UriBuilder path = areaRoot().path("kontraktsomrader");

        if (includeObjectLink) path.queryParam("inkluder", getIncludeParameter(false, false, true));

        WebTarget target = getClient().target(path);

        return getAreas(target)
            .map(rt(AreaParser::parseContractArea))
            .collect(Collectors.toList());
    }

    public List<ContractArea> getContractAreas() {
        return getContractAreas(false);
    }

    private Stream<JsonObject> getAreas(boolean includeBoundingBox, boolean includeCenterPoint, boolean includeObjectLink, Projection projection, WebTarget target) {
        if (projection != null) {
            target = target.queryParam("srid", projection.getSrid());
        }

        if (includeCenterPoint || includeBoundingBox || includeObjectLink) {
            target = target.queryParam("inkluder", getIncludeParameter(includeBoundingBox, includeCenterPoint, includeObjectLink));
        }

        return getAreas(target);
    }

    private Stream<JsonObject> getAreas(WebTarget target) {
        JsonElement e = JerseyHelper.execute(target);
        JsonArray a = e.getAsJsonArray();
        return StreamSupport.stream(a.spliterator(), false).map(JsonElement::getAsJsonObject);
    }

    private static String getIncludeParameter(boolean includeBoundingbox, boolean includeCenterPoint, boolean includeObjectLink) {
        List<String> inkluder = new ArrayList<>();

        if (includeBoundingbox) {
            inkluder.add("kartutsnitt");
        }

        if (includeCenterPoint) {
            inkluder.add("senterpunkt");
        }

        if (includeObjectLink) {
            inkluder.add("vegobjekt");
        }

        return String.join(",", inkluder);
    }

    private UriBuilder areaRoot() {
        return start().path("omrader");
    }

    public static class StreetsResult extends GenericResultSet<ExtendedStreet> {

        public StreetsResult(WebTarget baseTarget,
                                 Page currentPage) {
            super(baseTarget, currentPage, "gater", rt(AreaParser::parseStreet));
        }
    }

    public static class AsyncStreetsResult extends AsyncResult<ExtendedStreet> {

        public AsyncStreetsResult(WebTarget baseTarget,
                                      Page currentPage) {
            super(baseTarget, currentPage, rt(AreaParser::parseStreet));
        }
    }
}
