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

public class RoadPlacement {

    private final Object roadRef;
    private final RefLinkPosition refLinkPosition;
    private final Geometry point;
    private final Integer municipality;
    private final LocalDate startDate;
    private final LocalDate endDate;


    public <T> RoadPlacement(T roadSysRef,
                         RefLinkPosition refLinkPosition,
                         Geometry point,
                         Integer municipality,
                         LocalDate startDate, LocalDate endDate) {
        this.roadRef = roadSysRef;
        this.refLinkPosition = refLinkPosition;
        this.point = point;
        this.municipality = municipality;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public <T> T getRoadSysRef() {
        return (T) roadRef;
    }

    public RefLinkPosition getRefLinkPosition() {
        return refLinkPosition;
    }

    public Geometry getPoint() {
        return point;
    }

    public Integer getMunicipality() {
        return municipality;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return "RoadPlacement{" +
                "roadRef=" + roadRef +
                ", refLinkPosition=" + refLinkPosition +
                ", point=" + point +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoadPlacement that = (RoadPlacement) o;
        return Objects.equals(roadRef, that.roadRef) &&
                Objects.equals(refLinkPosition, that.refLinkPosition) &&
                Objects.equals(point, that.point) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(municipality, that.municipality);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roadRef, refLinkPosition, point, municipality, startDate, endDate);
    }
}
