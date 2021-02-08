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

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class ContractArea implements Serializable {

    private final Integer number;
    private final String name;
    private final String type;
    private final List<RoadObjectId> objects;
    private final List<Integer> counties;
    private final List<Integer> municipalities;

    public ContractArea(Integer number, String name, String type,
                        List<RoadObjectId> objects,
                        List<Integer> counties, List<Integer> municipalities) {
        this.number = number;
        this.name = name;
        this.type = type;
        this.objects = objects;
        this.counties = counties;
        this.municipalities = municipalities;
    }

    public Optional<Integer> getNumber() {
        return Optional.ofNullable(number);
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public List<RoadObjectId> getObjects() {
        return objects;
    }

    public List<Integer> getCounties() {
        return counties;
    }

    public List<Integer> getMunicipalities() {
        return municipalities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContractArea that = (ContractArea) o;
        return
            Objects.equals(number, that.number) &&
            Objects.equals(name, that.name) &&
            Objects.equals(type, that.type) &&
            Objects.equals(objects, that.objects) &&
            Objects.equals(counties, that.counties) &&
            Objects.equals(municipalities, that.municipalities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, name, type, objects, counties, municipalities);
    }
}
