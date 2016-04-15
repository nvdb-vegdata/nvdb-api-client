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

import java.util.*;

public class RoadObjectRequest {

    public static final RoadObjectRequest DEFAULT = new Builder().build();

    private final Page page;
    private final Optional<Boolean> segmented;
    private final Optional<String> depth;
    private final Optional<Projection> projection;
    private final Optional<Integer> distanceTolerance;
    private final Optional<String> attributeFilter;
    private final Optional<String> bbox;
    private final Optional<String> roadRefFilter;
    private final Optional<String> refLinkFilter;
    private final Set<RoadObjectClient.Include> includes;
    private final List<OverlapFilter> overlapFilters;
    private final List<Integer> municipalities;
    private final List<Integer> counties;
    private final List<Integer> regions;
    private final List<Integer> roadDepartments;
    private final List<String> contractAreas;
    private final List<String> nationalRoutes;

    private RoadObjectRequest(Builder b) {
        page = b.page;
        segmented = b.segmented;
        depth = b.depth;
        projection = b.projection;
        includes = b.includes;
        distanceTolerance = b.distanceTolerance;
        attributeFilter = b.attributeFilter;
        bbox = b.bbox;
        roadRefFilter = b.roadRefFilter;
        refLinkFilter = b.refLinkFilter;
        overlapFilters = b.overlapFilters;
        municipalities = b.municipalities;
        counties = b.counties;
        regions = b.regions;
        roadDepartments = b.roadDepartments;
        contractAreas = b.contractAreas;
        nationalRoutes = b.nationalRoutes;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Page getPage() {
        return page;
    }

    public Optional<Boolean> getSegmented() {
        return segmented;
    }

    public Optional<String> getDepth() {
        return depth;
    }

    public Optional<Projection> getProjection() {
        return projection;
    }

    public Optional<Integer> getDistanceTolerance() {
        return distanceTolerance;
    }

    public Set<RoadObjectClient.Include> getIncludes() {
        return includes;
    }

    public List<OverlapFilter> getOverlapFilters() {
        return overlapFilters;
    }

    public Optional<String> getAttributeFilter() {
        return attributeFilter;
    }

    public Optional<String> getBbox() {
        return bbox;
    }

    public Optional<String> getRoadRefFilter() {
        return roadRefFilter;
    }

    public Optional<String> getRefLinkFilter() {
        return refLinkFilter;
    }

    public List<Integer> getMunicipalities() {
        return municipalities;
    }

    public List<Integer> getCounties() {
        return counties;
    }

    public List<Integer> getRegions() {
        return regions;
    }

    public List<Integer> getRoadDepartments() {
        return roadDepartments;
    }

    public List<String> getContractAreas() {
        return contractAreas;
    }

    public List<String> getNationalRoutes() {
        return nationalRoutes;
    }

    /**
     * This method strips any parameters that are not supported by the API
     * for statistics requests
     * @return
     */
    public RoadObjectRequest forStatistics() {
        return toMutable()
                .withIncludes(Collections.emptySet())
                .withPage(null)
                .withDistanceTolerance(null)
                .withProjection(null)
                .withDepth((String) null)
                .build();
    }

    public Builder toMutable() {
        Builder b = new Builder();
        b.withPage(page);
        segmented.ifPresent(b::withSegmented);
        depth.ifPresent(b::withDepth);
        projection.ifPresent(b::withProjection);
        distanceTolerance.ifPresent(b::withDistanceTolerance);
        b.withIncludes(includes);
        attributeFilter.ifPresent(b::withAttributeFilter);
        bbox.ifPresent(b::withBbox);
        roadRefFilter.ifPresent(b::withRoadRefFilter);
        refLinkFilter.ifPresent(b::withRefLinkFilter);
        overlapFilters.forEach(of -> b.addOverlapFilter(of.filter, of.typeId));
        b.withMunicipalities(municipalities);
        b.withCounties(counties);
        b.withRegions(regions);
        b.withRoadDepartments(roadDepartments);
        return b;
    }

    public static class Builder {

        private Page page = Page.count(1000);
        private Optional<Boolean> segmented = Optional.empty();
        private Optional<String> depth = Optional.empty();
        private Optional<Projection> projection = Optional.empty();
        private Optional<Integer> distanceTolerance = Optional.empty();
        private Set<RoadObjectClient.Include> includes = Collections.emptySet();
        private Optional<String> attributeFilter = Optional.empty();
        private Optional<String> bbox = Optional.empty();
        private Optional<String> roadRefFilter = Optional.empty();
        public Optional<String> refLinkFilter = Optional.empty();
        private List<OverlapFilter> overlapFilters = new ArrayList<>();
        private List<Integer> municipalities = Collections.emptyList();
        private List<Integer> counties = Collections.emptyList();
        private List<Integer> regions = Collections.emptyList();
        private List<Integer> roadDepartments = Collections.emptyList();
        private List<String> contractAreas = Collections.emptyList();
        private List<String> nationalRoutes = Collections.emptyList();

        private Builder() {
        }

        public RoadObjectRequest build() {
            return new RoadObjectRequest(this);
        }

        public Builder withPage(Page page) {
            this.page = page;
            return this;
        }

        public Builder segmented() {
            return withSegmented(true);
        }

        public Builder withSegmented(Boolean segmented) {
            this.segmented = Optional.ofNullable(segmented);
            return this;
        }

        public Builder withProjection(Projection projection) {
            this.projection = Optional.ofNullable(projection);
            return this;
        }

        public Builder withDepth(Integer depth) {
            this.depth = Optional.ofNullable(depth).map(d -> d.toString());
            return this;
        }

        public Builder fullDepth() {
            return withDepth("full");
        }

        private Builder withDepth(String depth) {
            this.depth = Optional.ofNullable(depth);
            return this;
        }

        public Builder withIncludes(Set<RoadObjectClient.Include> includes) {
            this.includes = includes;
            return this;
        }

        public Builder withIncludes(RoadObjectClient.Include... includes) {
            this.includes = new TreeSet<>(Arrays.asList(includes));
            return this;
        }

        public Builder includeAll() {
            return withIncludes(EnumSet.of(RoadObjectClient.Include.ALL));
        }

        public Builder withDistanceTolerance(Integer distanceTolerance) {
            this.distanceTolerance = Optional.ofNullable(distanceTolerance);
            return this;
        }

        public Builder withAttributeFilter(String attributeFilter) {
            this.attributeFilter = Optional.ofNullable(attributeFilter);
            return this;
        }

        public Builder withBbox(String bbox) {
            this.bbox = Optional.ofNullable(bbox);
            return this;
        }

        public Builder withRoadRefFilter(String filter) {
            this.roadRefFilter = Optional.ofNullable(filter);
            return this;
        }

        public Builder withRefLinkFilter(String filter) {
            this.refLinkFilter = Optional.ofNullable(filter);
            return this;
        }

        public Builder withRoadDepartments(List<Integer> roadDepartments) {
            this.roadDepartments = roadDepartments;
            return this;
        }

        public Builder withRoadDepartments(Integer... roadDepartments) {
            this.roadDepartments = Arrays.asList(roadDepartments);
            return this;
        }

        public Builder withRoadDepartment(Integer roadDepartment) {
            this.roadDepartments = Collections.singletonList(roadDepartment);
            return this;
        }

        public Builder withRegions(List<Integer> regions) {
            this.regions = regions;
            return this;
        }

        public Builder withRegions(Integer... regions) {
            this.regions = Arrays.asList(regions);
            return this;
        }

        public Builder withRegion(Integer region) {
            this.regions = Collections.singletonList(region);
            return this;
        }

        public Builder withCounties(List<Integer> counties) {
            this.counties = counties;
            return this;
        }

        public Builder withCounties(Integer... counties) {
            this.counties = Arrays.asList(counties);
            return this;
        }

        public Builder withCounty(Integer county) {
            this.counties = Collections.singletonList(county);
            return this;
        }

        public Builder withMunicipalities(List<Integer> municipalities) {
            this.municipalities = municipalities;
            return this;
        }

        public Builder withMunicipalities(Integer... municipalities) {
            this.municipalities = Arrays.asList(municipalities);
            return this;
        }

        public Builder withMunicipality(Integer municipality) {
            this.municipalities = Collections.singletonList(municipality);
            return this;
        }

        public Builder addOverlapFilter(String filter, int typeId) {
            this.overlapFilters.add(new OverlapFilter(filter, typeId));
            return this;
        }

        public Builder addOverlapFilter(int typeId) {
            this.overlapFilters.add(new OverlapFilter(null, typeId));
            return this;
        }

        public Builder withContractArea(String contractArea) {
            this.contractAreas = Collections.singletonList(contractArea);
            return this;
        }

        public Builder withContractAreas(String... contractAreas) {
            this.contractAreas = Arrays.asList(contractAreas);
            return this;
        }

        public Builder withNationalRoute(String nationalRoute) {
            this.nationalRoutes = Collections.singletonList(nationalRoute);
            return this;
        }

        public Builder withNationalRoutes(String... nationalRoutes) {
            this.nationalRoutes = Arrays.asList(nationalRoutes);
            return this;
        }
    }

    public static class OverlapFilter {
        private final String filter;
        private final int typeId;

        private OverlapFilter(String filter, int typeId) {
            this.filter = filter;
            this.typeId = typeId;
        }

        public String getFilter() {
            return filter;
        }

        public int getTypeId() {
            return typeId;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder().append(typeId);
            if (filter != null) sb.append("(").append(filter).append(")");
            return sb.toString();
        }
    }
}
