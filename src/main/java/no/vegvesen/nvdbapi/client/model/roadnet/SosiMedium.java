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

import java.util.Arrays;

/**
  * Mulige verdier for medium er beskrevet i SOSI-standarden. Se
 * http://www.kartverket.no/Documents/Standard/SOSI-standarden%20del%201%20og%202/SOSI-standard%20Del%201/Generelle%20typer%204.5.pdf
  **/
public enum SosiMedium {
    T("På terrenget/på bakkenivå"),
    B("I bygning/bygningsmessig anlegg"),
    L("I luft"),
    U("Under terrenget"),
    S("På sjøbunnen"),
    O("På vannoverflaten"),
    V("Alltid i vann"),
    D("Tidvis under vann"),
    I("På isbre"),
    W("Under sjøbunnen"),
    J("Under isbre"),
    X("Ukjent");

    private final String description;

    SosiMedium(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static SosiMedium from(String name) {
        return Arrays.stream(values()).filter(v -> v.name().equalsIgnoreCase(name)).findAny().orElse(null);
    }
}
