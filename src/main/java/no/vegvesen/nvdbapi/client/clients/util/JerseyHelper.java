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

package no.vegvesen.nvdbapi.client.clients.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import no.vegvesen.nvdbapi.client.exceptions.ApiError;
import no.vegvesen.nvdbapi.client.exceptions.ClientException;
import no.vegvesen.nvdbapi.client.exceptions.JsonExceptionParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class JerseyHelper {
    private static final Logger logger = LoggerFactory.getLogger(JerseyHelper.class);
    public static final String MEDIA_TYPE = "application/vnd.vegvesen.nvdb-v3+json";

    private JerseyHelper() {}

    public static boolean isSuccess(Response response) {
        return 200 <= response.getStatus() && response.getStatus() < 300;
    }

    public static ClientException parseError(Response response) {
        List<ApiError> errors = JsonExceptionParser.parse(response.readEntity(String.class));
        String requestId = response.getHeaderString("X-REQUEST-ID");
        return new ClientException(response.getStatus(), requestId, errors);
    }

    public static <T> T execute(Invocation inv, Class<T> responseClass) {
        try {
            return inv.invoke(responseClass);
        } catch (WebApplicationException ex) {
            logger.error("Got error on: {}", ex.getResponse().toString());
            throw parseError(ex.getResponse());
        }
    }

    public static JsonElement execute(WebTarget target) {
        return execute(target, MEDIA_TYPE);
    }

    public static JsonElement execute(WebTarget target, String mediaType) {
        Invocation invocation = target.request().accept(mediaType).buildGet();
        try(Response response = execute(invocation, Response.class)) {

            if (!isSuccess(response)) {
                throw parseError(response);
            }
            String requestId = response.getHeaderString("X-REQUEST-ID");
            try {
                return JsonParser.parseReader(
                        new InputStreamReader((InputStream) response.getEntity(), StandardCharsets.UTF_8));
            } catch (Exception e) {
                throw new ClientException(response.getStatus(), requestId, Collections.emptyList(), e);
            }
        }
    }

    public static <T> T execute(Invocation inv, GenericType<T> responseType) {
        try {
            return inv.invoke(responseType);
        } catch (WebApplicationException ex) {
            logger.error("Got error on: {}", ex.getResponse().toString());
            throw parseError(ex.getResponse());
        }
    }

    public static Optional<JsonElement> executeOptional(WebTarget target) {
        Invocation inv = target.request().buildGet();
        try(Response response = execute(inv, Response.class)) {

            if (!isSuccess(response)) {
                if (response.getStatus() == 404) {
                    return Optional.empty();
                }
                throw parseError(response);
            }

            if (response.getStatus() == 204) {
                return Optional.empty();
            }
            String requestId = response.getHeaderString("X-REQUEST-ID");

            try (InputStream is = response.readEntity(InputStream.class)) {
                return Optional.of(JsonParser.parseReader(new InputStreamReader(is, StandardCharsets.UTF_8)));
            } catch (Exception e) {
                throw new ClientException(response.getStatus(), requestId, Collections.emptyList(), e);
            }
        }
    }
}
