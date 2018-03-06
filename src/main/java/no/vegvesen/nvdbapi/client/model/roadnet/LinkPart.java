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

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class LinkPart {

    private final Integer partId;
    private final Boolean isConnectionLink;
    private final Boolean isDetailed;
    private final TopologyLevel topologyLevel;
    private final Integer startPort;
    private final Integer endPort;
    private final Integer municipality;
    private final Integer municipalityGeometry;
    private final Double length;
    private final Integer measureMethod;
    private final LocalDate measureDate;
    private final SosiMedium sosiMedium;
    private final Ltema ltema;
    private final CenterLineProjection centerLineProjection;
    private final TypeRoad typeRoad;
    private final Geometry geometry;
    private final List<String> fields;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public LinkPart(Integer partId, Boolean isConnectionLink, Boolean isDetailed, TopologyLevel topologyLevel, Integer startPort, Integer endPort, Integer municipality, Integer municipalityGeometry, Double length, Integer measureMethod, LocalDate measureDate, SosiMedium sosiMedium, Ltema ltema, CenterLineProjection centerLineProjection, TypeRoad typeRoad, Geometry geometry, List<String> fields, LocalDate startDate, LocalDate endDate) {
        this.partId = partId;
        this.isConnectionLink = isConnectionLink;
        this.isDetailed = isDetailed;
        this.topologyLevel = topologyLevel;
        this.startPort = startPort;
        this.endPort = endPort;
        this.municipality = municipality;
        this.municipalityGeometry = municipalityGeometry;
        this.length = length;
        this.measureMethod = measureMethod;
        this.measureDate = measureDate;
        this.sosiMedium = sosiMedium;
        this.ltema = ltema;
        this.centerLineProjection = centerLineProjection;
        this.typeRoad = typeRoad;
        this.geometry = geometry;
        this.fields = fields;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Integer getPartId() {
        return partId;
    }

    public Boolean getConnectionLink() {
        return isConnectionLink;
    }

    public Boolean getDetailed() {
        return isDetailed;
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

    public CenterLineProjection getCenterLineProjection() {
        return centerLineProjection;
    }

    public TypeRoad getTypeRoad() {
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

    public Integer getMeasureMethod() {
        return measureMethod;
    }

    public LocalDate getMeasureDate() {
        return measureDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkPart linkPart = (LinkPart) o;
        return Objects.equals(getPartId(), linkPart.getPartId()) &&
            Objects.equals(isConnectionLink, linkPart.isConnectionLink) &&
            Objects.equals(isDetailed, linkPart.isDetailed) &&
            getTopologyLevel() == linkPart.getTopologyLevel() &&
            Objects.equals(getStartPort(), linkPart.getStartPort()) &&
            Objects.equals(getEndPort(), linkPart.getEndPort()) &&
            Objects.equals(getMunicipality(), linkPart.getMunicipality()) &&
            Objects.equals(getMunicipalityGeometry(), linkPart.getMunicipalityGeometry()) &&
            Objects.equals(getLength(), linkPart.getLength()) &&
            Objects.equals(getMeasureMethod(), linkPart.getMeasureMethod()) &&
            Objects.equals(getMeasureDate(), linkPart.getMeasureDate()) &&
            getSosiMedium() == linkPart.getSosiMedium() &&
            getLtema() == linkPart.getLtema() &&
            Objects.equals(getCenterLineProjection(), linkPart.getCenterLineProjection()) &&
            getTypeRoad() == linkPart.getTypeRoad() &&
            Objects.equals(getGeometry(), linkPart.getGeometry()) &&
            Objects.equals(getFields(), linkPart.getFields()) &&
            Objects.equals(getStartDate(), linkPart.getStartDate()) &&
            Objects.equals(getEndDate(), linkPart.getEndDate());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getPartId(), isConnectionLink, isDetailed, getTopologyLevel(), getStartPort(), getEndPort(), getMunicipality(), getMunicipalityGeometry(), getLength(), getMeasureMethod(), getMeasureDate(), getSosiMedium(), getLtema(), getCenterLineProjection(), getTypeRoad(), getGeometry(), getFields(), getStartDate(), getEndDate());
    }

    @Override
    public String toString() {
        return "LinkPart{" +
            "partId=" + partId +
            ", isConnectionLink=" + isConnectionLink +
            ", isDetailed=" + isDetailed +
            ", topologyLevel=" + topologyLevel +
            ", startPort=" + startPort +
            ", endPort=" + endPort +
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
