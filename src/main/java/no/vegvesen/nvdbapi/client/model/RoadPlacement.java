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

import no.vegvesen.nvdbapi.client.model.roadnet.roadsysref.RoadSysRef;

import java.util.Objects;

public class RoadPlacement {

    private final RoadSysRef roadSysRef;
    private final RefLinkPosition refLinkPosition;
    private final Geometry point;
    private final Integer municipality;

    public RoadPlacement(RoadSysRef roadSysRef,
                         RefLinkPosition refLinkPosition,
                         Geometry point,
                         Integer municipality) {
        this.roadSysRef = roadSysRef;
        this.refLinkPosition = refLinkPosition;
        this.point = point;
        this.municipality = municipality;
    }

    public RoadSysRef getRoadSysRef() {
        return roadSysRef;
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

    @Override
    public String toString() {
        return "RoadPlacement{" +
                "roadSysRef=" + roadSysRef +
                ", refLinkPosition=" + refLinkPosition +
                ", point=" + point +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoadPlacement that = (RoadPlacement) o;
        return Objects.equals(roadSysRef, that.roadSysRef) &&
                Objects.equals(refLinkPosition, that.refLinkPosition) &&
                Objects.equals(point, that.point) &&
                Objects.equals(municipality, that.municipality);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roadSysRef, refLinkPosition, point, municipality);
    }
}
