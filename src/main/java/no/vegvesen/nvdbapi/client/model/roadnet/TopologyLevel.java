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

package no.vegvesen.nvdbapi.client.model.roadnet;

public enum TopologyLevel {
    VEGTRASE("vegtrase", "Vegtrasé"),
    KJOREBANE("kjørebane", "Kjørebane"),
    KJOREFELT("kjørefelt", "Kjørefelt");

    private final String apiValue;
    private final String description;

    TopologyLevel(String apiValue, String description) {
        this.apiValue = apiValue;
        this.description = description;
    }

    public String getApiValue() {
        return apiValue;
    }

    public String getDescription() {
        return description;
    }

    public static TopologyLevel fromValue(String val) {
        if(val == null) return null;
        switch (val.toLowerCase()) {
            case "vegtrase":
            case "vegtrasé": return VEGTRASE;
            case "kjorebane":
            case "kjørebane": return KJOREBANE;
            case "kjorefelt":
            case "kjørefelt": return KJOREFELT;
            default: return null;
        }
    }
}
