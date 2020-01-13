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
import no.vegvesen.nvdbapi.client.model.areas.ContractArea;
import no.vegvesen.nvdbapi.client.model.areas.Route;
import no.vegvesen.nvdbapi.client.model.roadnet.roadsysref.RoadSysRef;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class SegmentedLink implements Serializable {

    private final long id;
    private final double start;
    private final double end;
    private final Geometry geometry;
    private final double length;
    private final int linkNumber;
    private final int segmentNumber;
    private final RefLinkPartType linkType;
    private final List<ContractArea> contractAreas;
    private final List<Route> routes;
    private final DetailLevel detailLevel;
    private final TypeOfRoad roadType;


    private final Long superLinkId;
    private final Integer county;
    private final Integer municipality;
    private final RoadSysRef roadRef;
    private final LocalDate fromDate;
    private final LocalDate toDate;
    private final String startNode;
    private final String endNode;

    public SegmentedLink(long id, Long superLinkId, double start, double end,
                         int linkNumber, int segmentNumber, DetailLevel detailLevel, TypeOfRoad roadType,
                         String startNode, String endNode,
                         LocalDate fromDate, LocalDate toDate,
                         Integer county,
                         Integer municipality,
                         Geometry geometry, double length, RoadSysRef roadRef,
                         RefLinkPartType linkType,
                         List<ContractArea> contractAreas,
                         List<Route> routes) {
        this.id = id;
        this.superLinkId = superLinkId;
        this.start = start;
        this.end = end;
        this.linkNumber = linkNumber;
        this.segmentNumber = segmentNumber;
        this.detailLevel = detailLevel;
        this.roadType = roadType;
        this.county = county;
        this.municipality = municipality;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.startNode = startNode;
        this.endNode = endNode;
        this.length = length;
        this.roadRef = roadRef;
        this.geometry = geometry;
        this.linkType = linkType;
        this.contractAreas = contractAreas;
        this.routes = routes;
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

    public int getLinkNumber() {
        return linkNumber;
    }

    public int getSegmentNumber() {
        return segmentNumber;
    }

    public RefLinkPartType getLinkType() {
        return linkType;
    }

    public DetailLevel getDetailLevel() {
        return detailLevel;
    }

    public TypeOfRoad getRoadType() {
        return roadType;
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

    public double getLength() {
        return length;
    }

    public List<ContractArea> getContractAreas() {
        return contractAreas;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SegmentedLink that = (SegmentedLink) o;
        return id == that.id &&
                Double.compare(that.start, start) == 0 &&
                Double.compare(that.end, end) == 0 &&
                Double.compare(that.length, length) == 0 &&
                linkNumber == that.linkNumber &&
                segmentNumber == that.segmentNumber &&
                Objects.equals(geometry, that.geometry) &&
                Objects.equals(linkType, that.linkType) &&
                Objects.equals(contractAreas, that.contractAreas) &&
                Objects.equals(routes, that.routes) &&
                Objects.equals(detailLevel, that.detailLevel) &&
                Objects.equals(roadType, that.roadType) &&
                Objects.equals(superLinkId, that.superLinkId) &&
                Objects.equals(county, that.county) &&
                Objects.equals(municipality, that.municipality) &&
                Objects.equals(roadRef, that.roadRef) &&
                Objects.equals(fromDate, that.fromDate) &&
                Objects.equals(toDate, that.toDate) &&
                Objects.equals(startNode, that.startNode) &&
                Objects.equals(endNode, that.endNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, start, end, geometry, length, linkNumber, segmentNumber, linkType, contractAreas, routes, detailLevel, roadType, superLinkId, county, municipality, roadRef, fromDate, toDate, startNode, endNode);
    }
}
