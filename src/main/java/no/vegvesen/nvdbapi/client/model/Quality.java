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

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Quality {
    private final Integer measurementMethod;
    private final Integer accuracy;
    private final Integer visibility;
    private final Integer heightMethod;
    private final Integer heightAccuracy;
    private final Integer tolerance;
    private final String captureMethod;
    private final String heightCaptureMethod;

    public Quality(Integer measurementMethod,
                   Integer accuracy,
                   Integer heightMethod,
                   Integer heightAccuracy,
                   Integer tolerance,
                   Integer visibility,
                   String captureMethod,
                   String heightCaptureMethod) {
        this.measurementMethod = measurementMethod;
        this.accuracy = accuracy;
        this.visibility = visibility;
        this.heightMethod = heightMethod;
        this.heightAccuracy = heightAccuracy;
        this.tolerance = tolerance;
        this.captureMethod = captureMethod;
        this.heightCaptureMethod = heightCaptureMethod;
    }

    public Integer getMethod() {
        return measurementMethod;
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

    public String getCaptureMethod() {
        return captureMethod;
    }

    public String getHeightCaptureMethod() {
        return heightCaptureMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quality quality = (Quality) o;
        return Objects.equals(measurementMethod, quality.measurementMethod) &&
            Objects.equals(accuracy, quality.accuracy) &&
            Objects.equals(visibility, quality.visibility) &&
            Objects.equals(heightMethod, quality.heightMethod) &&
            Objects.equals(heightAccuracy, quality.heightAccuracy) &&
            Objects.equals(tolerance, quality.tolerance) &&
            Objects.equals(captureMethod, quality.captureMethod) &&
            Objects.equals(heightCaptureMethod, quality.heightCaptureMethod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(measurementMethod, accuracy, visibility, heightMethod, heightAccuracy, tolerance, captureMethod, heightCaptureMethod);
    }

    @Override
    public String toString() {
        return "Quality{" +
            "method=" + measurementMethod +
            ", accuracy=" + accuracy +
            ", visibility=" + visibility +
            ", heightMethod=" + heightMethod +
            ", heightAccuracy=" + heightAccuracy +
            ", tolerance=" + tolerance +
            ", captureMethod=" + captureMethod +
            ", heightCaptureMethod=" + heightCaptureMethod +
            '}';
    }
}
