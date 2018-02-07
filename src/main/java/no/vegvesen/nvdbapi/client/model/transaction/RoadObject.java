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

package no.vegvesen.nvdbapi.client.model.transaction;

import java.util.Objects;

public class RoadObject {

    private final int id;
    private final RoadObjectMetadata roadObjectMetadata;
    private final Type type;

    public RoadObject(int id, RoadObjectMetadata roadObjectMetadata, Type type) {
        this.id = id;
        this.roadObjectMetadata = roadObjectMetadata;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public RoadObjectMetadata getRoadObjectMetadata() {
        return roadObjectMetadata;
    }

    public Type getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoadObject that = (RoadObject) o;
        return getId() == that.getId() &&
            Objects.equals(getRoadObjectMetadata(), that.getRoadObjectMetadata()) &&
            getType() == that.getType();
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getRoadObjectMetadata(), getType());
    }

    @Override
    public String toString() {
        return "RoadObject{" +
            "id=" + id +
            ", roadObjectMetadata=" + roadObjectMetadata +
            ", type=" + type +
            '}';
    }
}
