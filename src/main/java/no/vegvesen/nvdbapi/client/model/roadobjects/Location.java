/*
 * Copyright (c) 2015-2016, Statens vegvesen
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

import com.google.common.collect.ImmutableList;
import no.vegvesen.nvdbapi.client.model.Geometry;
import no.vegvesen.nvdbapi.client.model.areas.ContractArea;
import no.vegvesen.nvdbapi.client.model.areas.Route;

import static java.util.Objects.requireNonNull;

public class Location {
    private final ImmutableList<Integer> municipalities;
    private final ImmutableList<Integer> counties;
    private final ImmutableList<Integer> regions;
    private final ImmutableList<Integer> departments;
    private final ImmutableList<ContractArea> contractAreas;
    private final ImmutableList<Route> nationalRoutes;
    private final ImmutableList<RoadRef> roadRefs;
    private final ImmutableList<Placement> placements;
    private final Geometry geometry;

    private final Double length;

    public Location(ImmutableList<Integer> municipalities, ImmutableList<Integer> counties, ImmutableList<Integer> regions,
                    ImmutableList<Integer> departments, Double length, ImmutableList<Placement> placements, ImmutableList<RoadRef> roadRefs,
                    ImmutableList<ContractArea> contractAreas, ImmutableList<Route> nationalRoutes, Geometry geometry) {
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

    public ImmutableList<Integer> getMunicipalities() {
        return municipalities;
    }

    public ImmutableList<Integer> getCounties() {
        return counties;
    }

    public ImmutableList<Integer> getRegions() {
        return regions;
    }

    public ImmutableList<Integer> getDepartments() {
        return departments;
    }

    public ImmutableList<ContractArea> getContractAreas() {
        return contractAreas;
    }

    public ImmutableList<Route> getNationalRoutes() {
        return nationalRoutes;
    }

    public ImmutableList<RoadRef> getRoadRefs() {
        return roadRefs;
    }

    public ImmutableList<Placement> getPlacements() {
        return placements;
    }

    public Double getLength() {
        return length;
    }

    public Geometry getGeometry() {
        return geometry;
    }
}
