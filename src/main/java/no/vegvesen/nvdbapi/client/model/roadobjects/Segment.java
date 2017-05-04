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

package no.vegvesen.nvdbapi.client.model.roadobjects;

import no.vegvesen.nvdbapi.client.model.Geometry;

public class Segment {

    private final Geometry geometry;
    private final int municipality;
    private final int county;
    private final int region;
    private final int roadDepartment;
    private final Placement placement;
    private final RoadRef roadRef;
    private final Integer length;

    public Segment(Geometry geometry, int municipality, int county, int region, int roadDepartment,
                   Placement placement, RoadRef roadRef, Integer length) {
        this.geometry = geometry;
        this.municipality = municipality;
        this.county = county;
        this.region = region;
        this.roadDepartment = roadDepartment;
        this.placement = placement;
        this.roadRef = roadRef;
        this.length = length;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public int getMunicipality() {
        return municipality;
    }

    public int getCounty() {
        return county;
    }

    public int getRegion() {
        return region;
    }

    public int getRoadDepartment() {
        return roadDepartment;
    }

    public Placement getPlacement() {
        return placement;
    }

    public RoadRef getRoadRef() {
        return roadRef;
    }

    public Integer getLength() {
        return length;
    }
}
