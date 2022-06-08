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

import no.vegvesen.nvdbapi.client.model.Page;
import no.vegvesen.nvdbapi.client.model.Projection;
import no.vegvesen.nvdbapi.client.model.roadnet.*;

import java.time.LocalDate;
import java.util.*;

public class RoadNetRequest {
    public static final RoadNetRequest DEFAULT = newBuilder().build();

    private final Optional<Page> page;
    private final Optional<String> roadRefFilter;
    private final List<Integer> counties;
    private final List<Integer> municipalities;
    private final List<TopologyLevel> topologyLevel;
    private final List<Long> superId;
    private final List<Long> id;
    private final Optional<Projection> projection;
    private final Optional<String> bbox;
    private final Optional<String> contractArea;
    private final Optional<String> nationalRoute;
    private final Optional<String> street;
    private final Optional<String> bpolygon;
    private final boolean history;
    private final Optional<LocalDate> dateFilter;
    private final Optional<Boolean> armFilter;
    private final Optional<Boolean> sideAreaFilter;
    private final Optional<Boolean> intersectionFilter;
    private final Optional<RoadUserGroup> roadUserGroupFilter;
    private final Optional<SeparatePassages> separatePassagesFilter;
    private final Set<TypeOfRoad> typeOfRoadFilter;
    private final Optional<RefLinkPartType> refLinkPartTypeFilter;
    private final Set<DetailLevel> detailLevelFilter;

    private RoadNetRequest(Builder b) {
        page = b.page;
        roadRefFilter = b.roadRefFilter;
        counties = b.counties;
        municipalities = b.municipalities;
        topologyLevel = b.topologyLevel;
        this.superId = b.superId;
        projection = b.projection;
        bbox = b.bbox;
        bpolygon = b.bpolygon;
        contractArea = b.contractArea;
        nationalRoute = b.nationalRoute;
        street = b.street;
        id = b.id;
        history = b.history;
        dateFilter = b.dateFilter;
        armFilter = b.armFilter;
        sideAreaFilter = b.sideAreaFilter;
        intersectionFilter = b.intersectionFilter;
        roadUserGroupFilter = b.roadUserGroupFilter;
        separatePassagesFilter = b.separatePassagesFilter;
        refLinkPartTypeFilter = b.refLinkPartTypeFilter;
        detailLevelFilter = b.detailLevelFilter;
        typeOfRoadFilter = b.typeOfRoadFilter;

    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Page getPage() {
        return page.orElse(Page.defaults());
    }

    public Optional<Boolean> getArmFilter() {
        return armFilter;
    }

    public Optional<Boolean> getSideAreaFilter() {
        return sideAreaFilter;
    }

    public Optional<Boolean> getIntersectionFilter() {
        return intersectionFilter;
    }

    public Optional<RoadUserGroup> getRoadUserGroupFilter() {
        return roadUserGroupFilter;
    }

    public Optional<SeparatePassages> getSeparatePassagesFilter() {
        return separatePassagesFilter;
    }

    public Set<TypeOfRoad> getTypeOfRoadFilter() {
        return typeOfRoadFilter;
    }

    public Optional<RefLinkPartType> getRefLinkPartTypeFilter() {
        return refLinkPartTypeFilter;
    }

    public Set<DetailLevel> getDetailLevelFilter() {
        return detailLevelFilter;
    }

    public Optional<String> getRoadRefFilter() {
        return roadRefFilter;
    }

    public List<Integer> getCounties() {
        return counties;
    }

    public List<Integer> getMunicipalities() {
        return municipalities;
    }

    public List<TopologyLevel> getTopologyLevel() {
        return topologyLevel;
    }

    public List<Long> getSuperId() {
        return superId;
    }

    public Optional<String> getBbox() {
        return bbox;
    }

    public Optional<Projection> getProjection() {
        return projection;
    }

    public Optional<String> getContractArea() {
        return contractArea;
    }

    public Optional<String> getNationalRoute() {
        return nationalRoute;
    }

    public Optional<String> getStreet() {
        return street;
    }

    public List<Long> getId() {
        return id;
    }

    public Optional<String> getBpolygon() {
        return bpolygon;
    }

    public boolean isHistory() {
        return history;
    }

    public Optional<LocalDate> getDateFilter() {
        return dateFilter;
    }

    public static class Builder {
        private Optional<String> bpolygon = Optional.empty();
        private Optional<Page> page = Optional.empty();
        private Optional<String> roadRefFilter = Optional.empty();
        private List<Integer> counties = Collections.emptyList();
        private List<Integer> municipalities = Collections.emptyList();
        private List<TopologyLevel> topologyLevel = Collections.emptyList();
        private List<Long> superId = Collections.emptyList();
        private List<Long> id = Collections.emptyList();
        private Optional<String> bbox = Optional.empty();
        private Optional<Projection> projection = Optional.empty();
        private Optional<String> contractArea = Optional.empty();
        private Optional<String> nationalRoute = Optional.empty();
        private Optional<String> street = Optional.empty();
        private boolean history = false;
        private Optional<LocalDate> dateFilter = Optional.empty();
        private Optional<Boolean> armFilter = Optional.empty();
        private Optional<Boolean> sideAreaFilter = Optional.empty();
        private Optional<Boolean> intersectionFilter = Optional.empty();
        private Optional<RoadUserGroup> roadUserGroupFilter = Optional.empty();
        private Optional<SeparatePassages> separatePassagesFilter = Optional.empty();
        private Optional<RefLinkPartType> refLinkPartTypeFilter = Optional.empty();
        private Set<DetailLevel> detailLevelFilter = Collections.emptySet();
        private Set<TypeOfRoad> typeOfRoadFilter = Collections.emptySet();

        private Builder() {
        }

        public RoadNetRequest build() {
            return new RoadNetRequest(this);
        }

        public Builder withArmFilter(Boolean armFilter) {
            this.armFilter = Optional.ofNullable(armFilter);
            return this;
        }

        public Builder withSideAreaFilter(Boolean sideAreaFilter) {
            this.sideAreaFilter = Optional.ofNullable(sideAreaFilter);
            return this;
        }

        public Builder withIntersectionFilter(Boolean intersectionFilter) {
            this.intersectionFilter = Optional.ofNullable(intersectionFilter);
            return this;
        }

        public Builder withRoadUserGroupFilter(RoadUserGroup roadUserGroupFilter) {
            this.roadUserGroupFilter = Optional.ofNullable(roadUserGroupFilter);
            return this;
        }

        public Builder withSeparatePassagesFilter(SeparatePassages separatePassagesFilter) {
            this.separatePassagesFilter = Optional.ofNullable(separatePassagesFilter);
            return this;
        }

        public Builder withRefLinkPartTypeFilter(RefLinkPartType refLinkPartTypeFilter) {
            this.refLinkPartTypeFilter = Optional.ofNullable(refLinkPartTypeFilter);
            return this;
        }

        public Builder withDetailLevelFilter(Set<DetailLevel> detailLevelFilter) {
            this.detailLevelFilter = detailLevelFilter;
            return this;
        }
        public Builder withDetailLevelFilter(DetailLevel... detailLevelFilter) {
            this.detailLevelFilter = new HashSet<>(Arrays.asList(detailLevelFilter));
            return this;
        }

        public Builder withTypeOfRoadFilter(Set<TypeOfRoad> typeOfRoadFilter) {
            this.typeOfRoadFilter = typeOfRoadFilter;
            return this;
        }

        public Builder withTypeOfRoadFilter(TypeOfRoad... typeOfRoad) {
            this.typeOfRoadFilter = new HashSet<>(Arrays.asList(typeOfRoad));
            return this;
        }

        public Builder withBoundingPolygon(String boundingPolygon) {
            this.bpolygon = Optional.ofNullable(boundingPolygon);
            return this;
        }
        public Builder withPage(Page page) {
            this.page = Optional.ofNullable(page);
            return this;
        }

        public Builder withRoadRefFilter(String filter) {
            this.roadRefFilter = Optional.ofNullable(filter);
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

        public Builder withTopologyLevel(List<TopologyLevel> topologyLevel) {
            this.topologyLevel = Optional.ofNullable(topologyLevel).orElse(Collections.emptyList());
            return this;
        }

        public Builder withSuperId(List<Long> superId) {
            this.superId = Optional.ofNullable(superId).orElse(Collections.emptyList());
            return this;
        }

        public Builder withId(List<Long> id) {
            this.id = Optional.ofNullable(id).orElse(Collections.emptyList());
            return this;
        }

        public Builder withBbox(String bbox) {
            this.bbox = Optional.ofNullable(bbox);
            return this;
        }

        public Builder withBpolygon(String bpolygon) {
            this.bpolygon = Optional.ofNullable(bpolygon);
            return this;
        }

        public Builder withProjection(Projection projection) {
            this.projection = Optional.ofNullable(projection);
            return this;
        }

        public Builder withContractArea(String contractArea) {
            this.contractArea = Optional.ofNullable(contractArea);
            return this;
        }

        public Builder withNationalRoute(String nationalRoute) {
            this.nationalRoute = Optional.ofNullable(nationalRoute);
            return this;
        }

        public Builder withStreet(String street) {
            this.street = Optional.ofNullable(street);
            return this;
        }

        public Builder withDateFilter(LocalDate localDate){
            this.dateFilter = Optional.ofNullable(localDate);
            return this;
        }

        public Builder withHistory(boolean history){
            this.history = history;
            return this;
        }
    }
}
