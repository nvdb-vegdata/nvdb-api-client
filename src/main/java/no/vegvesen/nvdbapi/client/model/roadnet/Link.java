/*
 * Copyright (c) 2015-2018, Statens vegvesen
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
import no.vegvesen.nvdbapi.client.model.roadobjects.RefLinkExtentPlacement;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Link {

    private final Integer partId;
    private final LinkType linkType;
    private final TopologyLevel topologyLevel;
    private final Integer startPort;
    private final Integer endPort;
    private final Double startPos;
    private final Double endPos;
    private final Integer municipality;
    private final Integer municipalityGeometry;
    private final Double length;
    private final String measureMethod;
    private final LocalDate measureDate;
    private final SosiMedium sosiMedium;
    private final Ltema ltema;
    private final RefLinkExtentPlacement centerLineProjection;
    private final TypeOfRoad typeRoad;
    private final String detailLevel;
    private final Geometry geometry;
    private final List<String> fields;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Link(Integer partId, LinkType linkType, TopologyLevel topologyLevel,
                Integer startPort, Integer endPort, Double startPos, Double endPos, Integer municipality,
                Integer municipalityGeometry, Double length, String measureMethod, LocalDate measureDate,
                SosiMedium sosiMedium, Ltema ltema, RefLinkExtentPlacement centerLineProjection, TypeOfRoad typeRoad,
                String detailLevel, Geometry geometry, List<String> fields, LocalDate startDate, LocalDate endDate) {
        this.partId = partId;
        this.linkType = linkType;
        this.topologyLevel = topologyLevel;
        this.startPort = startPort;
        this.endPort = endPort;
        this.startPos = startPos;
        this.endPos = endPos;
        this.municipality = municipality;
        this.municipalityGeometry = municipalityGeometry;
        this.length = length;
        this.measureMethod = measureMethod;
        this.measureDate = measureDate;
        this.sosiMedium = sosiMedium;
        this.ltema = ltema;
        this.centerLineProjection = centerLineProjection;
        this.typeRoad = typeRoad;
        this.detailLevel = detailLevel;
        this.geometry = geometry;
        this.fields = fields;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Integer getPartId() {
        return partId;
    }

    public LinkType getLinkType() {
        return linkType;
    }

    public TopologyLevel getTopologyLevel() {
        return topologyLevel;
    }

    public Integer getStartPort() {
        return startPort;
    }

    public Integer getEndPort() {
        return endPort;
    }

    public Double getStartPos() {
        return startPos;
    }

    public Double getEndPos() {
        return endPos;
    }

    public Integer getMunicipality() {
        return municipality;
    }

    public Double getLength() {
        return length;
    }

    public SosiMedium getSosiMedium() {
        return sosiMedium;
    }

    public Ltema getLtema() {
        return ltema;
    }

    public RefLinkExtentPlacement getCenterLineProjection() {
        return centerLineProjection;
    }

    public TypeOfRoad getTypeRoad() {
        return typeRoad;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public List<String> getFields() {
        return fields;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Integer getMunicipalityGeometry() {
        return municipalityGeometry;
    }

    public String getMeasureMethod() {
        return measureMethod;
    }

    public LocalDate getMeasureDate() {
        return measureDate;
    }

    public String getDetailLevel() {
        return detailLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return Objects.equals(partId, link.partId) &&
                linkType == link.linkType &&
                topologyLevel == link.topologyLevel &&
                Objects.equals(startPort, link.startPort) &&
                Objects.equals(endPort, link.endPort) &&
                Objects.equals(startPos, link.startPos) &&
                Objects.equals(endPos, link.endPos) &&
                Objects.equals(municipality, link.municipality) &&
                Objects.equals(municipalityGeometry, link.municipalityGeometry) &&
                Objects.equals(length, link.length) &&
                Objects.equals(measureMethod, link.measureMethod) &&
                Objects.equals(measureDate, link.measureDate) &&
                sosiMedium == link.sosiMedium &&
                ltema == link.ltema &&
                Objects.equals(centerLineProjection, link.centerLineProjection) &&
                Objects.equals(typeRoad, link.typeRoad) &&
                Objects.equals(detailLevel, link.detailLevel) &&
                Objects.equals(geometry, link.geometry) &&
                Objects.equals(fields, link.fields) &&
                Objects.equals(startDate, link.startDate) &&
                Objects.equals(endDate, link.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(partId, linkType, topologyLevel, startPort, endPort, municipality,
                municipalityGeometry, length, measureMethod, measureDate, sosiMedium, ltema, centerLineProjection,
                typeRoad, detailLevel, geometry, fields, startDate, endDate, startPos, endPos);
    }

    @Override
    public String toString() {
        return "Link{" +
            "partId=" + partId +
            ", linkType=" + linkType +
            ", topologyLevel=" + topologyLevel +
            ", startPort=" + startPort +
            ", endPort=" + endPort +
            ", startPos=" + startPos +
            ", endPos=" + endPos +
            ", municipality=" + municipality +
            ", municipalityGeometry=" + municipalityGeometry +
            ", length=" + length +
            ", measureMethod=" + measureMethod +
            ", measureDate=" + measureDate +
            ", sosiMedium=" + sosiMedium +
            ", ltema=" + ltema +
            ", centerLineProjection=" + centerLineProjection +
            ", typeRoad=" + typeRoad +
            ", geometry=" + geometry +
            ", fields=" + fields +
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            '}';
    }
}
