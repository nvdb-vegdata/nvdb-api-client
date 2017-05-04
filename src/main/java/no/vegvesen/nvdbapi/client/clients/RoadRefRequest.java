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

package no.vegvesen.nvdbapi.client.clients;

import no.vegvesen.nvdbapi.client.model.Projection;

import java.util.Optional;

public class RoadRefRequest {

    private final int county;
    private final int municipality;
    private final String roadCategory;
    private final String roadStatus;
    private final int roadNumber;
    private final int hp;
    private final int meter;
    private final Projection projection;

    public RoadRefRequest(Builder builder) {
        this.county = builder.county;
        this.municipality = builder.municipality;
        this.roadCategory = builder.roadCategory;
        this.roadStatus = builder.roadStatus;
        this.roadNumber = builder.roadNumber;
        this.hp = builder.hp;
        this.meter = builder.meter;
        this.projection = builder.projection;
    }

    public int getCounty() { return county; }

    public int getMunicipality() {
        return municipality;
    }

    public String getRoadCategory() {
        return roadCategory;
    }

    public String getRoadStatus() {
        return roadStatus;
    }

    public int getRoadNumber() {return roadNumber; }

    public int getHp() {
        return hp;
    }

    public int getMeter() {
        return meter;
    }

    public Optional<Projection> getProjection() {
        return Optional.ofNullable(projection);
    }

    public String getQueryParam() {
        String county = String.format("%02d", this.getCounty());
        String municipality = String.format("%02d", this.getMunicipality());

        return county + municipality + this.getRoadCategory() + this.getRoadStatus() + this.getRoadNumber() +
                "hp" + this.getHp() + "m" + this.getMeter();
    }

    @Override
    public String toString() {
        return this.getQueryParam();
    }

    public final static class Builder {
        private int county;
        private int municipality;
        private String roadCategory;
        private String roadStatus;
        private int roadNumber;
        private int hp;
        private int meter;
        private Projection projection;

        @Deprecated
        public Builder setMunicipality(int municipality) {
            return withMunicipality(municipality);
        }

        @Deprecated
        public Builder setRoadCategory(String roadCategory) {
            return withRoadCategory(roadCategory);
        }

        @Deprecated
        public Builder setRoadStatus(String roadStatus) {
            return withRoadStatus(roadStatus);
        }

        @Deprecated
        public Builder setRoadNumber(int roadNumber) {
            return withRoadNumber(roadNumber);
        }

        @Deprecated
        public Builder setCounty(int county) {
            return withCounty(county);
        }

        @Deprecated
        public Builder setHp(int hp) {
            return withHp(hp);
        }

        @Deprecated
        public Builder setMeter(int meter) {
            return withMeter(meter);
        }

        public Builder withMunicipality(int municipality) {
            this.municipality = municipality;
            return this;
        }

        public Builder withRoadCategory(String roadCategory) {
            this.roadCategory = roadCategory;
            return this;
        }

        public Builder withRoadStatus(String roadStatus) {
            this.roadStatus = roadStatus;
            return this;
        }

        public Builder withRoadNumber(int roadNumber) {
            this.roadNumber = roadNumber;
            return this;
        }

        public Builder withCounty(int county) {
            this.county = county;
            return this;
        }

        public Builder withHp(int hp) {
            this.hp = hp;
            return this;
        }

        public Builder withMeter(int meter) {
            this.meter = meter;
            return this;
        }

        public Builder withProjection(Projection projection) {
            this.projection = projection;
            return this;
        }

        public RoadRefRequest build() {
            return new RoadRefRequest(this);
        }
    }
}
