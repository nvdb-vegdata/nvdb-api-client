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

package no.vegvesen.nvdbapi.client.model.roadnet;

import no.vegvesen.nvdbapi.client.model.Geometry;
import no.vegvesen.nvdbapi.client.model.roadnet.roadsysref.RoadSysRef;
import no.vegvesen.nvdbapi.client.model.roadobjects.RoadRef;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

public final class SegmentedLink implements Serializable {

    private final Geometry geometry;
    private final long id;
    private final Long superLinkId;
    private final double start;
    private final double end;
    private final SosiMedium medium;
    private final TopologyLevel topologyLevel;
    private final Integer county;
    private final Integer municipality;
    private final Integer region;
    private final Integer roadDepartment;
    private final RoadSysRef roadRef;
    private final LocalDate fromDate;
    private final LocalDate toDate;
    private final String startNode;
    private final String endNode;
    private final Integer reflinkPartType;

    public SegmentedLink(long id, Long superLinkId, double start, double end,
                         String startNode, String endNode,
                         LocalDate fromDate, LocalDate toDate,
                         SosiMedium medium, TopologyLevel level, Integer region, Integer county,
                         Integer municipality, Integer roadDepartment,
                         Geometry geometry, RoadSysRef roadRef, Integer reflinkPartType) {
        this.id = id;
        this.superLinkId = superLinkId;
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
        this.geometry = geometry;
        this.reflinkPartType = reflinkPartType;
    }

    public long getId() {
        return id;
    }

    public Optional<Long> getSuperLinkId() {
        return Optional.ofNullable(superLinkId);
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

    public RoadSysRef getRoadRef() {
        return roadRef;
    }

    public Integer getCounty() {
        return county;
    }

    public Integer getMunicipality() {
        return municipality;
    }

    public Integer getRegion() {
        return region;
    }

    public Integer getRoadDepartment() {
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
        return Optional.ofNullable(geometry);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SegmentedLink that = (SegmentedLink) o;
        return id == that.id &&
                Double.compare(that.start, start) == 0 &&
                Double.compare(that.end, end) == 0 &&
                Objects.equals(geometry, that.geometry) &&
                Objects.equals(superLinkId, that.superLinkId) &&
                medium == that.medium &&
                topologyLevel == that.topologyLevel &&
                Objects.equals(county, that.county) &&
                Objects.equals(municipality, that.municipality) &&
                Objects.equals(region, that.region) &&
                Objects.equals(roadDepartment, that.roadDepartment) &&
                Objects.equals(roadRef, that.roadRef) &&
                Objects.equals(fromDate, that.fromDate) &&
                Objects.equals(toDate, that.toDate) &&
                Objects.equals(startNode, that.startNode) &&
                Objects.equals(endNode, that.endNode) &&
                Objects.equals(reflinkPartType, that.reflinkPartType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(geometry, id, superLinkId, start, end, medium, topologyLevel, county, municipality, region, roadDepartment, roadRef, fromDate, toDate, startNode, endNode, reflinkPartType);
    }
}
