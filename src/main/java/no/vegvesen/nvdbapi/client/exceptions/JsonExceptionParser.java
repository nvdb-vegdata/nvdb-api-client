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

package no.vegvesen.nvdbapi.client.exceptions;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public final class JsonExceptionParser {
    private static final Logger LOG = LoggerFactory.getLogger(JsonExceptionParser.class);

    private JsonExceptionParser() {
    }

    public static ApiError parseFirst(String json) {
        List<ApiError> errors = parse(json);
        return errors.isEmpty() ? null : errors.get(0);
    }

    public static List<ApiError> parse(String json) {
        if(json.contains("<html>")) {
            return Collections.singletonList(
                new ApiError(504,
                    "Server did not respond correctly",
                    json, ""));
        }
        try {
            return StreamSupport.stream(
                JsonParser.parseString(json).getAsJsonArray().spliterator(), false
            )
                .map(n -> new ApiError(
                    readInt(n, "code"),
                    readText(n, "message"),
                    readText(n, "message_detailed"),
                    readText(n, "help_url")
                ))
                .collect(Collectors.toList());
        } catch (Exception ex) {
            LOG.warn("Could not parse '{}' as json (exception: {})", json, ex.getClass().getName());
            throw ex;
        }
    }

    private static Integer readInt(JsonElement e, String name) {
        return Optional.ofNullable(e)
                .map(JsonElement::getAsJsonObject)
                .filter(JsonExceptionParser::isNonNull)
                .map(o -> o.get(name))
                .filter(JsonExceptionParser::isNonNull)
                .map(JsonElement::getAsInt).orElse(null);
    }

    private static String readText(JsonElement e, String name) {
        return Optional.ofNullable(e)
                .map(JsonElement::getAsJsonObject)
                .filter(JsonExceptionParser::isNonNull)
                .map(o -> o.get(name))
                .filter(JsonExceptionParser::isNonNull)
                .map(JsonElement::getAsString).orElse(null);
    }

    private static boolean isNonNull(JsonElement elem) {
        return !elem.isJsonNull();
    }
}
