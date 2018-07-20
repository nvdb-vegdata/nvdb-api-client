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

import java.util.Objects;

public class CenterLineProjection {
    private final long linkId;
    private final Double startPosition;
    private final Double endPosition;

    public CenterLineProjection(long linkId, Double startPosition, Double endPosition) {
        this.linkId = linkId;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    public long getLinkId() {
        return linkId;
    }

    public Double getStartPosition() {
        return startPosition;
    }

    public Double getEndPosition() {
        return endPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CenterLineProjection that = (CenterLineProjection) o;
        return Objects.equals(getLinkId(), that.getLinkId()) &&
            Objects.equals(getStartPosition(), that.getStartPosition()) &&
            Objects.equals(getEndPosition(), that.getEndPosition());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getLinkId(), getStartPosition(), getEndPosition());
    }

    @Override
    public String toString() {
        return "CenterLineProjection{" +
            "linkId=" + linkId +
            ", startPosition=" + startPosition +
            ", endPosition=" + endPosition +
            '}';
    }
}
