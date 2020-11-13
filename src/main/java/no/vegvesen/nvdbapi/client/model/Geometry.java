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

package no.vegvesen.nvdbapi.client.model;

import java.util.Objects;

public class Geometry {
    private final String wkt;
    private final Projection projection;
    private final GeometryAttributes geometryAttributes;
    private final boolean isSimplified;
    private final boolean isOwnGeometry;

    public Geometry(String wkt, Projection projection, boolean isSimplified, boolean isOwnGeometry) {
        this(wkt, projection, isSimplified, isOwnGeometry, null);
    }

    public Geometry(String wkt,
                    Projection projection,
                    boolean isSimplified,
                    boolean isOwnGeometry,
                    GeometryAttributes geometryAttributes) {
        this.wkt = wkt;
        this.projection = projection;
        this.isSimplified = isSimplified;
        this.isOwnGeometry = isOwnGeometry;
        this.geometryAttributes = geometryAttributes;
    }

    public GeometryAttributes getGeometryAttributes() {
        return geometryAttributes;
    }

    public boolean isSimplified() {
        return isSimplified;
    }

    public boolean isOwnGeometry() {
        return isOwnGeometry;
    }

    public String getWkt() {
        return wkt;
    }

    public Projection getProjection() {
        return projection;
    }

    @Override
    public String toString() {
        return toString(true);
    }

    public String toString(boolean extended) {
        return extended ? String.format("SRID=%d;%s", projection.getSrid(), wkt) : wkt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Geometry geometry = (Geometry) o;
        return isSimplified == geometry.isSimplified &&
                isOwnGeometry == geometry.isOwnGeometry &&
                Objects.equals(wkt, geometry.wkt) &&
                Objects.equals(projection, geometry.projection) &&
                Objects.equals(geometryAttributes, geometry.geometryAttributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wkt, projection, isSimplified, isOwnGeometry, geometryAttributes);
    }
}
