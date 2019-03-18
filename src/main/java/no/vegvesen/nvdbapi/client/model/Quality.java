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
import java.util.Objects;
import java.util.Optional;

public class Quality {
    private final Optional<LocalDate> verifiedDate;
    private final Integer method;
    private final Integer accuracy;
    private final Integer visibility;
    private final Integer heightMethod;
    private final Integer heightAccuracy;
    private final Integer tolerance;

    public Quality(Integer method, Integer accuracy, Integer heightMethod, Integer heightAccuracy, Integer tolerance, Integer visibility, LocalDate verifiedDate) {
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

    public Integer getMethod() {
        return method;
    }

    public Integer getAccuracy() {
        return accuracy;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public Integer getHeightMethod() {
        return heightMethod;
    }

    public Integer getHeightAccuracy() {
        return heightAccuracy;
    }

    public Integer getTolerance() {
        return tolerance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quality quality = (Quality) o;
        return method == quality.method &&
                visibility == quality.visibility &&
                heightAccuracy == quality.heightAccuracy &&
                tolerance == quality.tolerance &&
                Objects.equals(verifiedDate, quality.verifiedDate) &&
                Objects.equals(accuracy, quality.accuracy) &&
                Objects.equals(heightMethod, quality.heightMethod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(verifiedDate, method, accuracy, visibility, heightMethod, heightAccuracy, tolerance);
    }
}
