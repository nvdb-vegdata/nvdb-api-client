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

import java.time.LocalDate;
import java.util.Optional;

public class Quality {
    private final Optional<LocalDate> verifiedDate;
    private final int method;
    private final Integer accuracy;
    private final int visibility;
    private final Integer heightMethod;
    private final int heightAccuracy;
    private final int tolerance;

    public Quality(int method, Integer accuracy, Integer heightMethod, int heightAccuracy, int tolerance, int visibility, LocalDate verifiedDate) {
        this.verifiedDate = Optional.ofNullable(verifiedDate);
        this.method = method;
        this.accuracy = accuracy;
        this.visibility = visibility;
        this.heightMethod = heightMethod;
        this.heightAccuracy = heightAccuracy;
        this.tolerance = tolerance;
    }

    public Optional<LocalDate> getVerifiedDate() {
        return verifiedDate;
    }

    public int getMethod() {
        return method;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public int getVisibility() {
        return visibility;
    }

    public int getHeightMethod() {
        return heightMethod;
    }

    public int getHeightAccuracy() {
        return heightAccuracy;
    }

    public int getTolerance() {
        return tolerance;
    }
}