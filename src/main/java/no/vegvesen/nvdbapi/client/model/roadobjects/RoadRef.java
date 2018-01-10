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

public class RoadRef {
    private final Integer county;
    private final Integer municipality;

    private final String category;
    private final String status;
    private final int number;
    private final int fromHp;
    private final Integer toHp;
    private final int fromMeter;
    private final Integer toMeter;
    private final String shortName;

    private RoadRef(Integer county, Integer municipality, String category, String status, int number, int fromHp, Integer toHp, int fromMeter, Integer toMeter, String shortName) {
        this.county = county;
        this.municipality = municipality;
        this.category = category;
        this.status = status;
        this.number = number;
        this.fromHp = fromHp;
        this.toHp = toHp;
        this.fromMeter = fromMeter;
        this.toMeter = toMeter;
        this.shortName = shortName;
    }

    public static RoadRef merged(Integer county, Integer municipality, String category, String status, int number, int fromHp, Integer toHp, int fromMeter, Integer toMeter, String shortName) {
        return new RoadRef(county, municipality, category, status, number, fromHp, toHp, fromMeter, toMeter, shortName);
    }

    public static RoadRef stretch(Integer county, Integer municipality, String category, String status, int number, int hp, int fromMeter, Integer toMeter, String shortName) {
        return new RoadRef(county, municipality, category, status, number, hp, hp, fromMeter, toMeter, shortName);
    }

    public static RoadRef point(int county, int municipality, String roadCategory, String roadStatus, int roadNumber, int hp, int meter, String shortName) {
        return new RoadRef(county, municipality, roadCategory, roadStatus, roadNumber, hp, null, meter, null, shortName);
    }

    public boolean isPoint() {
        return toHp == null && toMeter == null;
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

    public int getNumber() {
        return number;
    }

    public int getFromHp() {
        return fromHp;
    }

    public Integer getToHp() {
        return toHp;
    }

    public int getFromMeter() {
        return fromMeter;
    }

    public Integer getToMeter() {
        return toMeter;
    }

    public String getShortName() {
        return shortName;
    }

    @Override
    public String toString() {
        return getShortName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoadRef roadRef = (RoadRef) o;
        return number == roadRef.number &&
                fromHp == roadRef.fromHp &&
                fromMeter == roadRef.fromMeter &&
                Objects.equals(county, roadRef.county) &&
                Objects.equals(municipality, roadRef.municipality) &&
                Objects.equals(category, roadRef.category) &&
                Objects.equals(status, roadRef.status) &&
                Objects.equals(toHp, roadRef.toHp) &&
                Objects.equals(toMeter, roadRef.toMeter) &&
                Objects.equals(shortName, roadRef.shortName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(county, municipality, category, status, number, fromHp, toHp, fromMeter, toMeter, shortName);
    }
}
