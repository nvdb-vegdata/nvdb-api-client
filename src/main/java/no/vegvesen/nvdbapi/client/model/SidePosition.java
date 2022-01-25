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

package no.vegvesen.nvdbapi.client.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum SidePosition {
    LEFT("V"),
    RIGHT("H"),
    LEFT_AND_RIGHT("HV"),
    MIDDLE("M"),
    CROSSING("K"),
    MIDDLE_LEFT("MV"),
    MIDDLE_RIGHT("MH"),
    LEFT_ACCESS("VT"),
    RIGHT_ACCESS("HT"),
    ROUNDABOUT_CENTRE("R0"),
    LONGITUDINAL("L");

    private final String apiValue;

    SidePosition(String apiValue) {
        this.apiValue = apiValue;
    }

    public String getApiValue() {
        return apiValue;
    }

    private static Map<String, SidePosition> mapping = new HashMap<>();
    public static SidePosition from(String apiValue) {
        return mapping.computeIfAbsent(
            apiValue,
            v -> Arrays.stream(values())
                .filter(d -> d.apiValue.equalsIgnoreCase(v))
                .findAny()
                .orElse(null));
    }

}
