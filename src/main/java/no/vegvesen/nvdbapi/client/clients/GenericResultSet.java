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

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import no.vegvesen.nvdbapi.client.clients.util.JerseyHelper;
import no.vegvesen.nvdbapi.client.gson.GsonUtil;
import no.vegvesen.nvdbapi.client.model.Page;
import no.vegvesen.nvdbapi.client.model.ResultSet;
import no.vegvesen.nvdbapi.client.util.ResultSetCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class GenericResultSet<T> implements ResultSet<T> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private JsonObject currentResponse;
    private final WebTarget baseTarget;
    private final Function<JsonObject, T> parser;
    private Page currentPage;
    private boolean hasNext = true;

    protected GenericResultSet(WebTarget baseTarget, Optional<Page> currentPage, Function<JsonObject, T> parser) {
        this.baseTarget = baseTarget;
        this.parser = parser;
        this.currentPage = currentPage.orElse(null);
    }

    public List<T> getAll() {
        return ResultSetCollector.getAll(this);
    }

    public Stream<T> stream() {
        return getAll().stream();
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public List<T> next() {
        if (!hasNext()) {
            throw new IllegalStateException("No more results!");
        }

        // Setup and execute request
        WebTarget actualTarget = baseTarget;
        if (currentPage != null) actualTarget = applyPage(currentPage, baseTarget);
        logger.debug("Invoking {}", actualTarget.getUri());
        Invocation inv = actualTarget.request().accept(JerseyHelper.MEDIA_TYPE).buildGet();
        Response response = JerseyHelper.execute(inv, Response.class);

        if (!JerseyHelper.isSuccess(response)) {
            throw JerseyHelper.parseError(response);
        }

        // Consume and parse response
        String json = response.readEntity(String.class);
        this.currentResponse = new JsonParser().parse(json).getAsJsonObject();

        int pageSizeParam = GsonUtil.parseIntMember(currentResponse, "metadata.antall");
        logger.debug("Page size returned was {}.", pageSizeParam);

        logger.debug("Response: {}", currentResponse.toString());
        List<JsonObject> l = StreamSupport.stream(currentResponse.getAsJsonArray("objekter").spliterator(), false)
                .map(JsonElement::getAsJsonObject).collect(Collectors.toList());

        // Prepare next request
        Optional<String> nextMarker = GsonUtil.getNode(currentResponse, "metadata.neste.start").map(JsonElement::getAsString);
        this.currentPage = nextMarker.map(m -> {
            if (currentPage != null) {
                return currentPage.withStart(m);
            } else {
                return Page.subPage(pageSizeParam, m);
            }
        }).orElse(null);
        this.hasNext = currentPage != null;
        logger.debug("Got {} features.", l.size());
        if (!hasNext) {
            logger.debug("Result set exhausted.");
        }
        return l.stream().map(parser).collect(Collectors.toList());
    }

    private static WebTarget applyPage(Page page, WebTarget target) {
        if (Objects.nonNull(page.getCount())) {
            target = target.queryParam("antall", page.getCount());
        }
        if (page.getStart().isPresent()) {
            target = target.queryParam("start", page.getStart().get());
        }

        return target;
    }
}
