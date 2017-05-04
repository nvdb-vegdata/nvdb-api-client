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

import java.util.List;

import static java.util.Objects.requireNonNull;

public class Location {
    private final List<Integer> municipalities;
    private final List<Integer> counties;
    private final List<Integer> regions;
    private final List<Integer> departments;
    private final List<ContractArea> contractAreas;
    private final List<Route> nationalRoutes;
    private final List<RoadRef> roadRefs;
    private final List<Placement> placements;
    private final Geometry geometry;

    private final Double length;

    public Location(List<Integer> municipalities, List<Integer> counties, List<Integer> regions,
                    List<Integer> departments, Double length, List<Placement> placements, List<RoadRef> roadRefs,
                    List<ContractArea> contractAreas, List<Route> nationalRoutes, Geometry geometry) {
        this.placements = placements;
        this.contractAreas = contractAreas;
        this.nationalRoutes = nationalRoutes;
        this.geometry = geometry;
        this.municipalities = requireNonNull(municipalities);
        this.counties = requireNonNull(counties);
        this.regions = requireNonNull(regions);
        this.departments = requireNonNull(departments);
        this.length = length;
        this.roadRefs = requireNonNull(roadRefs);
    }

    public List<Integer> getMunicipalities() {
        return municipalities;
    }

    public List<Integer> getCounties() {
        return counties;
    }

    public List<Integer> getRegions() {
        return regions;
    }

    public List<Integer> getDepartments() {
        return departments;
    }

    public List<ContractArea> getContractAreas() {
        return contractAreas;
    }

    public List<Route> getNationalRoutes() {
        return nationalRoutes;
    }

    public List<RoadRef> getRoadRefs() {
        return roadRefs;
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
}
