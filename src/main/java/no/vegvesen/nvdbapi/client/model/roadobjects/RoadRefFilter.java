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

import java.util.Objects;

public class RoadRefFilter {
    private final Integer county;
    private final Integer municipality;
    private final String category;
    private final String status;
    private final Integer number;
    private final Integer fromHp;
    private final Integer toHp;

    public RoadRefFilter(Integer county, Integer municipality, String category, String status, Integer number, Integer fromHp, Integer toHp) {
        this.county = county;
        this.municipality = municipality;
        this.category = category;
        this.status = status;
        this.number = number;
        this.fromHp = fromHp;
        this.toHp = toHp;
    }

    public Integer getCounty() {
        return county;
    }

    public Integer getMunicipality() {
        return municipality;
    }

    public String getCategory() {
        return category;
    }

    public String getStatus() {
        return status;
    }

    public Integer getNumber() {
        return number;
    }

    public Integer getFromHp() {
        return fromHp;
    }

    public Integer getToHp() {
        return toHp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoadRefFilter that = (RoadRefFilter) o;
        return Objects.equals(county, that.county) &&
                Objects.equals(municipality, that.municipality) &&
                Objects.equals(category, that.category) &&
                Objects.equals(status, that.status) &&
                Objects.equals(number, that.number) &&
                Objects.equals(fromHp, that.fromHp) &&
                Objects.equals(toHp, that.toHp);
    }

    @Override
    public int hashCode() {

        return Objects.hash(county, municipality, category, status, number, fromHp, toHp);
    }
}
