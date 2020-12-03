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
import no.vegvesen.nvdbapi.client.model.areas.ContractArea;
import no.vegvesen.nvdbapi.client.model.areas.Route;
import no.vegvesen.nvdbapi.client.model.areas.Street;
import no.vegvesen.nvdbapi.client.model.roadnet.roadsysref.RoadSysRef;

import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class Location {
    private final List<Integer> municipalities;
    private final List<Integer> counties;
    private final List<ContractArea> contractAreas;
    private final List<Route> nationalRoutes;
    private final List<RoadSysRef> roadSysRefs;
    private final List<Placement> placements;
    private final List<Street> streets;
    private final Geometry geometry;

    private final Double length;

    public Location(List<Integer> municipalities, List<Integer> counties,
                    Double length, List<Placement> placements, List<RoadSysRef> RoadSysRefs,
                    List<ContractArea> contractAreas, List<Route> nationalRoutes, List<Street> streets, Geometry geometry) {
        this.placements = placements;
        this.contractAreas = contractAreas;
        this.nationalRoutes = nationalRoutes;
        this.streets = streets;
        this.geometry = geometry;
        this.municipalities = requireNonNull(municipalities);
        this.counties = requireNonNull(counties);
        this.length = length;
        this.roadSysRefs = requireNonNull(RoadSysRefs);
    }

    public List<Integer> getMunicipalities() {
        return municipalities;
    }

    public List<Integer> getCounties() {
        return counties;
    }

    public List<ContractArea> getContractAreas() {
        return contractAreas;
    }

    public List<Route> getNationalRoutes() {
        return nationalRoutes;
    }

    public List<Street> getStreets() {
        return streets;
    }

    public List<RoadSysRef> getRoadSysRefs() {
        return roadSysRefs;
    }

    public List<Placement> getPlacements() {
        return placements;
    }

    public Double getLength() {
        return length;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(municipalities, location.municipalities) &&
                Objects.equals(counties, location.counties) &&
                Objects.equals(contractAreas, location.contractAreas) &&
                Objects.equals(nationalRoutes, location.nationalRoutes) &&
                Objects.equals(streets, location.streets) &&
                Objects.equals(roadSysRefs, location.roadSysRefs) &&
                Objects.equals(placements, location.placements) &&
                Objects.equals(geometry, location.geometry) &&
                Objects.equals(length, location.length);
    }

    @Override
    public int hashCode() {
        return Objects.hash(municipalities, counties, contractAreas, nationalRoutes, roadSysRefs,
                streets, placements, geometry, length);
    }
}
