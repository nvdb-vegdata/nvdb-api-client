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
    private final Ltema ltema;
    private final TopologyLevel topologyLevel;
    private final Integer county;
    private final Integer municipality;
    private final Integer region;
    private final Integer roadDepartment;
    private final RoadRef roadRef;
    private final boolean isConnectionLink;
    private final LocalDate fromDate;
    private final LocalDate toDate;
    private final String startNode;
    private final String endNode;

    public SegmentedLink(long id, Long superLinkId, double start, double end,
                         String startNode, String endNode,
                         LocalDate fromDate, LocalDate toDate,
                         SosiMedium medium, Ltema ltema, TopologyLevel level, Integer region, Integer county,
                         Integer municipality, Integer roadDepartment,
                         Geometry geometry, RoadRef roadRef, boolean isConnectionLink) {
        this.id = id;
        this.superLinkId = superLinkId;
        this.start = start;
        this.end = end;
        this.medium = medium;
        this.ltema = ltema;
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
        this.geometry = geometry;
    }

    public long getId() {
        return id;
    }

    public Optional<Long> getSuperLinkId() {
        return Optional.ofNullable(superLinkId);
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

    public Ltema getLtema() {
        return ltema;
    }

    public TopologyLevel getTopologyLevel() {
        return topologyLevel;
    }

    public RoadRef getRoadRef() {
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
        SegmentedLink link = (SegmentedLink) o;
        return id == link.id &&
                Double.compare(link.start, start) == 0 &&
                Double.compare(link.end, end) == 0 &&
                isConnectionLink == link.isConnectionLink &&
                Objects.equals(geometry, link.geometry) &&
                Objects.equals(superLinkId, link.superLinkId) &&
                medium == link.medium &&
                ltema == link.ltema &&
                topologyLevel == link.topologyLevel &&
                Objects.equals(county, link.county) &&
                Objects.equals(municipality, link.municipality) &&
                Objects.equals(region, link.region) &&
                Objects.equals(roadDepartment, link.roadDepartment) &&
                Objects.equals(roadRef, link.roadRef) &&
                Objects.equals(fromDate, link.fromDate) &&
                Objects.equals(toDate, link.toDate) &&
                Objects.equals(startNode, link.startNode) &&
                Objects.equals(endNode, link.endNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(geometry, id, superLinkId, start, end, medium, ltema, topologyLevel, county, municipality,
                region, roadDepartment, roadRef, isConnectionLink, fromDate, toDate, startNode, endNode);
    }
}
