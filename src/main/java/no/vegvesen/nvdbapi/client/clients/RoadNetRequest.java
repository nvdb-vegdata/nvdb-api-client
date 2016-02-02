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

import no.vegvesen.nvdbapi.client.model.Page;
import no.vegvesen.nvdbapi.client.model.Projection;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class RoadNetRequest {
    public static final RoadNetRequest DEFAULT = newBuilder().build();

    private final Optional<Page> page;
    private final Optional<String> roadRefFilter;
    private final List<Integer> regions;
    private final List<Integer> counties;
    private final List<Integer> municipalities;
    private final List<Integer> roadDepartments;
    private final Optional<Projection> projection;
    private final Optional<String> bbox;

    private RoadNetRequest(Builder b) {
        page = b.page;
        roadRefFilter = b.roadRefFilter;
        regions = b.regions;
        counties = b.counties;
        municipalities = b.municipalities;
        roadDepartments = b.roadDepartments;
        projection = b.projection;
        bbox = b.bbox;
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }

    public Optional<Page> getPage() {
        return page;
    }

    public Optional<String> getRoadRefFilter() {
        return roadRefFilter;
    }

    public List<Integer> getRegions() {
        return regions;
    }

    public List<Integer> getCounties() {
        return counties;
    }

    public List<Integer> getMunicipalities() {
        return municipalities;
    }

    public List<Integer> getRoadDepartments() {
        return roadDepartments;
    }

    public Optional<String> getBbox() {
        return bbox;
    }

    public Optional<Projection> getProjection() {
        return projection;
    }

    public static class Builder {
        private Optional<Page> page = Optional.empty();
        private Optional<String> roadRefFilter = Optional.empty();
        private List<Integer> regions = Collections.emptyList();
        private List<Integer> counties = Collections.emptyList();
        private List<Integer> municipalities = Collections.emptyList();
        private List<Integer> roadDepartments = Collections.emptyList();
        private Optional<String> bbox = Optional.empty();
        private Optional<Projection> projection = Optional.empty();

        private Builder() {
        }

        public RoadNetRequest build() {
            return new RoadNetRequest(this);
        }

        public Builder withPage(Page page) {
            this.page = Optional.ofNullable(page);
            return this;
        }

        public Builder withRoadRefFilter(String filter) {
            this.roadRefFilter = Optional.ofNullable(filter);
            return this;
        }

        public Builder withRegions(List<Integer> regions) {
            this.regions = Optional.ofNullable(regions).orElse(Collections.emptyList());
            return this;
        }

        public Builder withRegion(Integer region) {
            this.regions = Optional.ofNullable(region).map(Collections::singletonList).orElse(Collections.emptyList());
            return this;
        }

        public Builder withCounties(List<Integer> counties) {
            this.counties = Optional.ofNullable(counties).orElse(Collections.emptyList());
            return this;
        }

        public Builder withCounty(Integer county) {
            this.counties = Optional.ofNullable(county).map(Collections::singletonList).orElse(Collections.emptyList());
            return this;
        }

        public Builder withMunicipalities(List<Integer> municipalities) {
            this.municipalities = Optional.ofNullable(municipalities).orElse(Collections.emptyList());
            return this;
        }

        public Builder withMunicipality(Integer municipality) {
            this.municipalities = Optional.ofNullable(municipality).map(Collections::singletonList).orElse(Collections.emptyList());
            return this;
        }

        public Builder withRoadDepartments(List<Integer> roadDepartments) {
            this.roadDepartments = Optional.ofNullable(roadDepartments).orElse(Collections.emptyList());
            return this;
        }

        public Builder withRoadDepartment(Integer roadDepartment) {
            this.roadDepartments = Optional.ofNullable(roadDepartment).map(Collections::singletonList).orElse(Collections.emptyList());
            return this;
        }

        public Builder withBbox(String bbox) {
            this.bbox = Optional.ofNullable(bbox);
            return this;
        }

        public Builder withProjection(Projection projection) {
            this.projection = Optional.ofNullable(projection);
            return this;
        }
    }
}
