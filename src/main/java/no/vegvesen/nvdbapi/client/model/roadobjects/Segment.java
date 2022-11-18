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

import no.vegvesen.nvdbapi.client.model.Direction;
import no.vegvesen.nvdbapi.client.model.Geometry;
import no.vegvesen.nvdbapi.client.model.roadnet.DetailLevel;
import no.vegvesen.nvdbapi.client.model.roadnet.RefLinkPartType;
import no.vegvesen.nvdbapi.client.model.roadnet.TypeOfRoad;
import no.vegvesen.nvdbapi.client.model.roadnet.roadsysref.RoadSysRef;

import java.time.LocalDate;
import java.util.Objects;

public class Segment {

    private final long netElementId;
    private final double startPosition;
    private final double endPosition;
    private final Direction direction;
    private final Geometry geometry;
    private final int municipality;
    private final int county;
    private final RoadSysRef roadSysRef;
    private final Double length;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final RefLinkPartType refLinkPartType;
    private final DetailLevel detailLevel;
    private final TypeOfRoad typeOfRoad;

    public Segment(long netElementId,
                   double startPosition,
                   double endPosition,
                   Direction direction,
                   Geometry geometry,
                   int municipality,
                   int county,
                   RoadSysRef roadSysRef,
                   Double length,
                   LocalDate startDate,
                   LocalDate endDate,
                   RefLinkPartType refLinkPartType,
                   DetailLevel detailLevel,
                   TypeOfRoad typeOfRoad) {
        this.netElementId = netElementId;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.direction = direction;
        this.geometry = geometry;
        this.municipality = municipality;
        this.county = county;
        this.roadSysRef = roadSysRef;
        this.length = length;
        this.startDate = startDate;
        this.endDate = endDate;
        this.refLinkPartType = refLinkPartType;
        this.detailLevel = detailLevel;
        this.typeOfRoad = typeOfRoad;
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

    public long getNetElementId() {
        return netElementId;
    }

    public double getStartPosition() {
        return startPosition;
    }

    public double getEndPosition() {
        return endPosition;
    }

    public Direction getDirection() {
        return direction;
    }

    public RoadSysRef getRoadSysRef() {
        return roadSysRef;
    }

    public Double getLength() {
        return length;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public DetailLevel getDetailLevel() {
        return detailLevel;
    }

    public TypeOfRoad getTypeOfRoad() {
        return typeOfRoad;
    }

    public RefLinkPartType getRefLinkPartType() {
        return refLinkPartType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Segment segment = (Segment) o;
        return netElementId == segment.netElementId &&
                Double.compare(segment.startPosition, startPosition) == 0 &&
                Double.compare(segment.endPosition, endPosition) == 0 &&
                segment.direction == this.direction &&
                municipality == segment.municipality &&
                county == segment.county &&
                Objects.equals(geometry, segment.geometry) &&
                Objects.equals(roadSysRef, segment.roadSysRef) &&
                Objects.equals(length, segment.length) &&
                Objects.equals(refLinkPartType, segment.refLinkPartType) &&
                Objects.equals(detailLevel, segment.detailLevel) &&
                Objects.equals(typeOfRoad, segment.typeOfRoad) &&
                Objects.equals(startDate, segment.startDate) &&
                Objects.equals(endDate, segment.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(netElementId, startPosition, endPosition, direction, geometry, municipality, county,
                roadSysRef, length, startDate, endDate, typeOfRoad, detailLevel, refLinkPartType);
    }
}
