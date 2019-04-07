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

import static java.util.Objects.nonNull;

public class RoadSysRefRequest {

    // RoadSystem
    private final Integer roadNumber;
    private final String roadCategory;
    private final String phase;

    // Secion
    private final Integer sectionNumber;
    private final Integer sectionPartNumber;
    private final Integer sectionMeter;

    // Intersection
    private final Integer interSectionNumber;
    private final Integer interSectionPart;
    private final Integer interSectionMeter;

    // Sidearea
    private final Integer sideAreaNumber;
    private final Integer sideAreaPart;
    private final Integer sideAreaMeter;

    // Other
    private final Integer municipality;
    private final Projection projection;

    public RoadSysRefRequest(Builder builder) {
        this.roadNumber = builder.roadNumber;
        this.roadCategory = builder.roadCategory;
        this.phase = builder.phase;
        this.sectionNumber = builder.sectionNumber;
        this.sectionPartNumber = builder.sectionPartNumber;
        this.sectionMeter = builder.sectionMeter;
        this.interSectionNumber = builder.interSectionNumber;
        this.interSectionPart = builder.interSectionPart;
        this.interSectionMeter = builder.interSectionMeter;
        this.sideAreaNumber = builder.sideAreaNumber;
        this.sideAreaPart = builder.sideAreaPart;
        this.sideAreaMeter = builder.sideAreaMeter;
        this.municipality = builder.municipality;
        this.projection = builder.projection;
    }

    public Integer getRoadNumber() {
        return roadNumber;
    }

    public String getRoadCategory() {
        return roadCategory;
    }

    public String getPhase() {
        return phase;
    }

    public Integer getSectionNumber() {
        return sectionNumber;
    }

    public Integer getSectionPartNumber() {
        return sectionPartNumber;
    }

    public Integer getSectionMeter() {
        return sectionMeter;
    }

    public Integer getInterSectionNumber() {
        return interSectionNumber;
    }

    public Integer getInterSectionPart() {
        return interSectionPart;
    }

    public Integer getInterSectionMeter() {
        return interSectionMeter;
    }

    public Integer getSideAreaNumber() {
        return sideAreaNumber;
    }

    public Integer getSideAreaPart() {
        return sideAreaPart;
    }

    public Integer getSideAreaMeter() {
        return sideAreaMeter;
    }

    public Optional<Integer> getMunicipality() {
        return Optional.ofNullable(municipality);
    }

    public Optional<Projection> getProjection() {
        return Optional.ofNullable(projection);
    }

    public String getQueryParam() {
        return this.getRoadCategory() + this.getPhase() + this.getRoadNumber() +
                "S" + this.getSectionNumber() + "D" + this.getSectionPartNumber() + "M" + this.getSectionMeter() +
                (nonNull(this.getInterSectionPart()) ? "KD" + this.getInterSectionPart() + "M" + this.getInterSectionMeter() : "") +
                (nonNull(this.getSideAreaPart()) ? "SD" + this.getSideAreaPart() + "M" + this.getSideAreaMeter() : "");
    }

    @Override
    public String toString() {
        return this.getQueryParam();
    }

    public final static class Builder {
        private Integer roadNumber;
        private String roadCategory;
        private String phase;
        private Integer sectionNumber;
        private Integer sectionPartNumber;
        private Integer sectionMeter;
        private Integer interSectionNumber;
        private Integer interSectionPart;
        private Integer interSectionMeter;
        private Integer sideAreaNumber;
        private Integer sideAreaPart;
        private Integer sideAreaMeter;
        private Integer municipality;
        private Projection projection;

        public Builder withRoadNumber(Integer roadNumber) {
            this.roadNumber = roadNumber;
            return this;
        }

        public Builder withRoadCategory(String roadCategory) {
            this.roadCategory = roadCategory;
            return this;
        }

        public Builder withPhase(String phase) {
            this.phase = phase;
            return this;
        }

        public Builder withSectionNumber(Integer sectionNumber) {
            this.sectionNumber = sectionNumber;
            return this;
        }

        public Builder withSectionPartNumber(Integer sectionPartNumber) {
            this.sectionPartNumber = sectionPartNumber;
            return this;
        }

        public Builder withSectionMeter(Integer sectionMeter) {
            this.sectionMeter = sectionMeter;
            return this;
        }

        public Builder withInterSectionNumber(Integer interSectionNumber) {
            this.interSectionNumber = interSectionNumber;
            return this;
        }

        public Builder withInterSectionPart(Integer interSectionPart) {
            this.interSectionPart = interSectionPart;
            return this;
        }

        public Builder withInterSectionMeter(Integer interSectionMeter) {
            this.interSectionMeter = interSectionMeter;
            return this;
        }

        public Builder withSideAreaNumber(Integer sideAreaNumber) {
            this.sideAreaNumber = sideAreaNumber;
            return this;
        }

        public Builder withSideAreaPart(Integer sideAreaPart) {
            this.sideAreaPart = sideAreaPart;
            return this;
        }

        public Builder withSideAreaMeter(Integer sideAreaMeter) {
            this.sideAreaMeter = sideAreaMeter;
            return this;
        }

        public Builder withMunicipality(Integer municipality) {
            this.municipality = municipality;
            return this;
        }

        public Builder withProjection(Projection projection) {
            this.projection = projection;
            return this;
        }

        public RoadSysRefRequest build() {
            return new RoadSysRefRequest(this);
        }
    }
}
