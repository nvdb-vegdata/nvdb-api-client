/*
 * Copyright (c) 2015-2018, Statens vegvesen
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

import java.util.Map;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public enum Ltema {

    VEGSENTERLINJE(7001, "VegSenterlinje"),
    KJOREFELT(7010, "Kjørefelt"),
    KJOREBANE(7011, "Kjørebane"),
    VEGTRASE(7012, "Vegtrasé"),
    BILFERJE(7201, "Bilferjestrekning"),
    GANG_OG_SYKKELVEG(7042, "GangSykkelVegSenterlinje"),
    FORTAU(7046, "Fortau"),
    TRAPP(6304, "FrittståendeTrapp"),
    SVINGEKONNEKTERINGLENKE(7004,"Svingekonnekteringslenke"),
    SYKKELVEG_SENTERLINJE(7043, "SykkelvegSenterlinje");

    private final int code;
    private final String description;

    private static final Map<Integer, Ltema> mapping = Stream.of(Ltema.values()).collect(toMap(Ltema::code, identity()));

    Ltema(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int code() {
        return this.code;
    }

    public String description() {
        return this.description;
    }

    public static Ltema from(Integer code) {
        return mapping.get(code);
    }
}
