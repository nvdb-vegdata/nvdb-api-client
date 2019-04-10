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

package no.vegvesen.nvdbapi.client.model.areas;

import no.vegvesen.nvdbapi.client.model.Geometry;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

public final class Municipality implements Serializable {

    private final Optional<RoadObjectId> id;
    private final int number;
    private final String name;
    private final Optional<Geometry> boundingBox;
    private final Optional<Geometry> centerPoint;
    private final int county;


    public Municipality(RoadObjectId id,
                        int number,
                        String name,
                        int county,
                        Geometry boundingBox,
                        Geometry centerPoint) {
        this.id = Optional.ofNullable(id);
        this.number = number;
        this.name = name;
        this.boundingBox = Optional.ofNullable(boundingBox);
        this.centerPoint = Optional.ofNullable(centerPoint);
        this.county = county;
    }

    public Optional<RoadObjectId> getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public Optional<Geometry> getBoundingBox() {
        return boundingBox;
    }

    public Optional<Geometry> getCenterPoint() {
        return centerPoint;
    }

    public int getCounty() {
        return county;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Municipality that = (Municipality) o;
        return number == that.number &&
                county == that.county &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(boundingBox, that.boundingBox) &&
                Objects.equals(centerPoint, that.centerPoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, name, boundingBox, centerPoint, county);
    }
}
