/*
 * Copyright (c) 2015-2016, Statens vegvesen
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

public class RoadRefRequest {

    private final int county;
    private final int municipality;
    private final String roadCategory;
    private final String roadStatus;
    private final int roadNumber;
    private final int hp;
    private final int meter;

    public RoadRefRequest(Builder builder) {
        this.county = builder.county;
        this.municipality = builder.municipality;
        this.roadCategory = builder.roadCategory;
        this.roadStatus = builder.roadStatus;
        this.roadNumber = builder.roadNumber;
        this.hp = builder.hp;
        this.meter = builder.meter;
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

        public Builder setMunicipality(int municipality) {
            this.municipality = municipality;
            return this;
        }

        public Builder setRoadCategory(String roadCategory) {
            this.roadCategory = roadCategory;
            return this;
        }

        public Builder setRoadStatus(String roadStatus) {
            this.roadStatus = roadStatus;
            return this;
        }

        public Builder setRoadNumber(int roadNumber) {
            this.roadNumber = roadNumber;
            return this;
        }

        public Builder setCounty(int county) {
            this.county = county;
            return this;
        }

        public Builder setHp(int hp) {
            this.hp = hp;
            return this;
        }

        public Builder setMeter(int meter) {
            this.meter = meter;
            return this;
        }

        public RoadRefRequest build() {
            return new RoadRefRequest(this);
        }
    }
}
