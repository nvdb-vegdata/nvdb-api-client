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
import no.vegvesen.nvdbapi.client.model.roadobjects.attribute.Attribute;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class RoadObject {
    private final long id;
    private final Integer typeId;
    private final Integer version;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Location location;
    private final Geometry geometry;
    private final List<Segment> segments;
    private final List<Attribute> attributes;
    private final List<Association> children;
    private final List<Association> parents;
    private final LocalDateTime lastModified;

    public RoadObject(long id, Integer typeId, Integer version, LocalDate startDate, LocalDate endDate,
                      List<Segment> segments,
                      Location location, Geometry geometry, LocalDateTime lastModified,
                      List<Attribute> attributes, List<Association> children, List<Association> parents) {
        this.id = id;
        this.typeId = typeId;
        this.version = version;
        this.startDate = startDate;
        this.endDate = endDate;
        this.segments = segments;
        this.location = location;
        this.geometry = geometry;
        this.lastModified = lastModified;
        this.attributes = attributes;
        this.children = children;
        this.parents = parents;
    }

    public boolean isMinimal() {
        return version == null;
    }

    public long getId() {
        return id;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public Integer getVersion() {
        return version;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public Attribute getAttribute(int attributeTypeId) {
        return attributes.stream()
            .filter(a -> a.getId() == attributeTypeId)
            .findAny()
            .orElse(null);
    }

    public <T extends Attribute> T getAttribute(int attributeTypeId, Class<T> attributeType) {
        return attributes.stream()
            .filter(a -> a.getId() == attributeTypeId)
            .filter(a -> attributeType.isAssignableFrom(a.getClass()))
            .map(attributeType::cast)
            .findAny()
            .orElse(null);
    }

    public Stream<Attribute> attributes() {
        return attributes.stream();
    }

    public List<Association> getChildren() {
        return children;
    }

    public List<Association> getParents() {
        return parents;
    }

    public Association getParents(int featureTypeId) {
        return parents.stream()
                .filter(a -> a.getTypeId() == featureTypeId)
                .findAny().orElse(null);
    }

    public Association getChildren(int featureTypeId) {
        return children.stream()
                .filter(a -> a.getTypeId() == featureTypeId)
                .findAny().orElse(null);
    }

    public Stream<Association> associations() {
        return children.stream();
    }

    public Location getLocation() {
        return location;
    }

    public List<Segment> getSegments() {
        return segments;
    }

    public Stream<Segment> segments() {
        return segments.stream();
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoadObject that = (RoadObject) o;
        return id == that.id &&
                Objects.equals(typeId, that.typeId) &&
                Objects.equals(version, that.version) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(location, that.location) &&
                Objects.equals(geometry, that.geometry) &&
                Objects.equals(segments, that.segments) &&
                Objects.equals(attributes, that.attributes) &&
                Objects.equals(children, that.children) &&
                Objects.equals(parents, that.parents) &&
                Objects.equals(lastModified, that.lastModified);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, typeId, version, startDate, endDate, location, geometry, segments,
                attributes, children, parents, lastModified);
    }
}
