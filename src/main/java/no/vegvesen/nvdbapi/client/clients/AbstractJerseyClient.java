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

import com.google.gson.Gson;
import no.vegvesen.nvdbapi.client.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;
import java.io.Serializable;
import java.net.URL;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

abstract class AbstractJerseyClient implements AutoCloseable, Serializable {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final String baseUrl;
    private final Client client;
    private boolean isClosed;

    protected AbstractJerseyClient(String baseUrl, Client client) {
        this.baseUrl = baseUrl;
        this.client = client;
    }

    protected Client getClient() {
        return client;
    }

    protected UriBuilder start() {
        return UriBuilder.fromUri(baseUrl);
    }

    protected void logEntity(Object obj) {
        logEntity("{}", obj);
    }

    protected void logEntity(String logMessage, Object obj) {
        log.debug(logMessage, Optional.ofNullable(obj).map(o -> new Gson().toJson(o)));
    }

    protected static Map<String, String> splitQuery(String url) {
        try {
            Map<String, String> query_pairs = new LinkedHashMap<>();
            String query = new URL(url).getQuery();
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
            }
            return query_pairs;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private String extractLink(MultivaluedMap<String, String> headers, String rel) {
        if (!headers.containsKey("Link")) {
            return null;
        }

        return headers.get("Link")
                .stream().filter(l -> isLink(l, rel))
                .findFirst().map(s -> s.substring(0, s.lastIndexOf(";")).trim()).orElse(null);
    }

    private boolean isLink(String val, String rel) {
        if (Strings.isNullOrEmpty(val)) {
            return false;
        }

        String withoutSpaces = val.replaceAll(" ", "");
        int idx = withoutSpaces.lastIndexOf("rel=");
        if (idx < 0) {
            return false;
        }

        String actualRel = withoutSpaces.substring(idx + "rel=".length());
        return actualRel.equalsIgnoreCase(rel);
    }

    public boolean isClosed() {
        return isClosed;
    }

    @Override
    public void close() throws Exception {
        if (isClosed) {
            return;
        }
        client.close();
        isClosed = true;
    }
}
