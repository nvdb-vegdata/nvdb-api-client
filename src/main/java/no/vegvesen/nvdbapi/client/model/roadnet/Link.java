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

package no.vegvesen.nvdbapi.client.model.roadnet;

import no.vegvesen.nvdbapi.client.model.Geometry;
import no.vegvesen.nvdbapi.client.model.roadobjects.RoadRef;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Optional;

public final class Link implements Serializable {

    private final Optional<Geometry> geometry;
    private final long id;
    private final double start;
    private final double end;
    private final SosiMedium medium;
    private final TopologyLevel topologyLevel;
    private final int county;
    private final int municipality;
    private final int region;
    private final int roadDepartment;
    private final RoadRef roadRef;
    private final boolean isConnectionLink;
    private final LocalDate fromDate;
    private final LocalDate toDate;
    private final String startNode;
    private final String endNode;

    public Link(long id, double start, double end,
                String startNode, String endNode,
                LocalDate fromDate, LocalDate toDate,
                SosiMedium medium, TopologyLevel level, int region, int county, int municipality, int roadDepartment,
                Geometry geometry, RoadRef roadRef, boolean isConnectionLink) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.medium = medium;
        this.topologyLevel = level;
        this.county = county;
        this.municipality = municipality;
        this.region = region;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.roadDepartment = roadDepartment;
        this.startNode = startNode;
        this.endNode = endNode;
        this.roadRef = roadRef;
        this.isConnectionLink = isConnectionLink;
        this.geometry = Optional.ofNullable(geometry);
    }

    public long getId() {
        return id;
    }

    public boolean isConnectionLink() {
        return isConnectionLink;
    }

    public double getStart() {
        return start;
    }

    public double getEnd() {
        return end;
    }

    public SosiMedium getMedium() {
        return medium;
    }

    public TopologyLevel getTopologyLevel() {
        return topologyLevel;
    }

    public RoadRef getRoadRef() {
        return roadRef;
    }

    public int getCounty() {
        return county;
    }

    public int getMunicipality() {
        return municipality;
    }

    public int getRegion() {
        return region;
    }

    public int getRoadDepartment() {
        return roadDepartment;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public String getEndNode() {
        return endNode;
    }

    public String getStartNode() {
        return startNode;
    }

    public Optional<Geometry> getGeometry() {
        return geometry;
    }
}
