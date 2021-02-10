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

public final class Route implements Serializable {

    private final String number;
    private final String name;
    private final String description;
    private final String period;
    private final List<RoadObjectId> objects;
    private final List<Integer> counties;
    private final List<Integer> municipalities;

    public Route(String number, String name, String description, String period,
                 List<RoadObjectId> objects,
                 List<Integer> counties, List<Integer> municipalities) {
        this.number = number;
        this.name = name;
        this.description = description;
        this.period = period;
        this.objects = objects;
        this.counties = counties;
        this.municipalities = municipalities;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPeriod() {
        return period;
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
        Route route = (Route) o;
        return
            Objects.equals(number, route.number) &&
            Objects.equals(name, route.name) &&
            Objects.equals(description, route.description) &&
            Objects.equals(period, route.period) &&
            Objects.equals(objects, route.objects) &&
            Objects.equals(counties, route.counties) &&
            Objects.equals(municipalities, route.municipalities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, name, description, period, objects, counties, municipalities);
    }
}
