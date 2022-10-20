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
import java.time.LocalDateTime;
import java.util.*;

@SuppressWarnings({"WeakerAccess", "UnusedReturnValue", "unused"})
public class RoadObjectRequest {

    public static final RoadObjectRequest DEFAULT = new Builder().build();

    private final Page page;
    private final Boolean segmented;
    private final Boolean allVersions;
    private final String depth;
    private final Projection projection;
    private final Integer distanceTolerance;
    private final String attributeFilter;
    private final String bbox;
    private final String bpolygon;
    private final String roadRefFilter;
    private final String refLinkFilter;
    private final Set<RoadObjectClient.Include> includes;
    private final Set<RoadObjectClient.IncludeGeometry> includeGeometries;
    private final Set<RoadObjectClient.IncludeAttribute> includeAttributes;
    private final List<OverlapFilter> overlapFilters;
    private final List<Integer> municipalities;
    private final List<Integer> counties;
    private final List<String> contractAreas;
    private final List<String> nationalRoutes;
    private final List<String> streets;
    private final List<Long> roadobjectIds;
    private final LocalDate pointInTime;
    private final LocalDateTime modifiedAfter;
    private final Boolean armFilter;
    private final Boolean sideAreaFilter;
    private final Boolean intersectionFilter;
    private final RoadUserGroup roadUserGroupFilter;
    private final Set<SeparatePassages> separatePassagesFilter;
    private final Set<DetailLevel> detailLevelFilter;
    private final Set<TypeOfRoad> typeOfRoadFilter;
    private final RefLinkPartType refLinkPartType;

    private RoadObjectRequest(Builder b) {
        page = b.page;
        segmented = b.segmented;
        depth = b.depth;
        projection = b.projection;
        includes = b.includes;
        includeGeometries = b.includeGeometries;
        includeAttributes = b.includeAttributes;
        distanceTolerance = b.distanceTolerance;
        attributeFilter = b.attributeFilter;
        bbox = b.bbox;
        bpolygon = b.bpolygon;
        roadRefFilter = b.roadRefFilter;
        refLinkFilter = b.refLinkFilter;
        overlapFilters = b.overlapFilters;
        municipalities = b.municipalities;
        counties = b.counties;
        contractAreas = b.contractAreas;
        nationalRoutes = b.nationalRoutes;
        streets = b.streets;
        allVersions = b.allVersions;
        roadobjectIds = b.roadobjectIds;
        pointInTime = b.pointInTime;
        modifiedAfter = b.modifiedAfter;
        armFilter = b.armFilter;
        sideAreaFilter = b.sideAreaFilter;
        intersectionFilter = b.intersectionFilter;
        detailLevelFilter = b.detailLevelFilter;
        typeOfRoadFilter = b.typeOfRoadFilter;
        refLinkPartType = b.refLinkPartType;
        roadUserGroupFilter = b.roadUserGroupFilter;
        separatePassagesFilter = b.separatePassagesFilter;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Page getPage() {
        return page;
    }

    public Optional<Boolean> getSegmented() {
        return Optional.ofNullable(segmented);
    }

    public Optional<Boolean> getAllVersions() {
        return Optional.ofNullable(allVersions);
    }

    public Optional<String> getDepth() {
        return Optional.ofNullable(depth);
    }

    public Optional<Projection> getProjection() {
        return Optional.ofNullable(projection);
    }

    public Optional<Integer> getDistanceTolerance() {
        return Optional.ofNullable(distanceTolerance);
    }

    public Set<RoadObjectClient.Include> getIncludes() {
        return includes;
    }

    public Set<RoadObjectClient.IncludeGeometry> getIncludeGeometries() {
        return this.includeGeometries;
    }

    public Set<RoadObjectClient.IncludeAttribute> getIncludeAttributes() {
        return includeAttributes;
    }

    public List<OverlapFilter> getOverlapFilters() {
        return overlapFilters;
    }

    public Optional<String> getAttributeFilter() {
        return Optional.ofNullable(attributeFilter);
    }

    public Optional<String> getBbox() {
        return Optional.ofNullable(bbox);
    }

    public Optional<String> getBpolygon() {
        return Optional.ofNullable(bpolygon);
    }

    public Optional<String> getRoadRefFilter() {
        return Optional.ofNullable(roadRefFilter);
    }

    public Optional<String> getRefLinkFilter() {
        return Optional.ofNullable(refLinkFilter);
    }

    public List<Integer> getMunicipalities() {
        return municipalities;
    }

    public List<Integer> getCounties() {
        return counties;
    }

    public List<String> getContractAreas() {
        return contractAreas;
    }

    public List<String> getNationalRoutes() {
        return nationalRoutes;
    }

    public List<String> getStreets() {
        return streets;
    }

    public List<Long> getRoadobjectIds() {
        return roadobjectIds;
    }

    public Optional<LocalDate> getPointInTime() {
        return Optional.ofNullable(pointInTime);
    }

    public Optional<Boolean> getArmFilter() {
        return Optional.ofNullable(armFilter);
    }

    public Optional<Boolean> getSideAreaFilter() {
        return Optional.ofNullable(sideAreaFilter);
    }

    public Optional<Boolean> getIntersectionFilter() {
        return Optional.ofNullable(intersectionFilter);
    }

    public Optional<RoadUserGroup> getRoadUserGroupFilter() {
        return Optional.ofNullable(roadUserGroupFilter);
    }

    public Set<SeparatePassages> getSeparatePassagesFilter() {
        return separatePassagesFilter;
    }

    public Optional<LocalDateTime> getModifiedAfter() {
        return Optional.ofNullable(modifiedAfter);
    }

    public Set<DetailLevel> getDetailLevel() {
        return detailLevelFilter;
    }

    public Set<TypeOfRoad> getTypeOfRoadFilter() {
        return typeOfRoadFilter;
    }

    public Optional<RefLinkPartType> getRefLinkPartType() {
        return Optional.ofNullable(refLinkPartType);
    }

    /**
     * This method strips any parameters that are not supported by the API
     * for statistics requests
     * @return {@code {@link RoadObjectRequest}} for statistics about {@code this} query
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
        Builder b = new Builder()
                .withPage(page)
                .withSegmented(segmented)
                .withDepth(depth)
                .withProjection(projection)
                .withDistanceTolerance(distanceTolerance)
                .withIncludes(includes)
                .withAttributeFilter(attributeFilter)
                .withBbox(bbox)
                .withBpolygon(bpolygon)
                .withRoadRefFilter(roadRefFilter)
                .withRefLinkFilter(refLinkFilter)
                .withMunicipalities(municipalities)
                .withCounties(counties)
                .withContractAreas(contractAreas)
                .withAllVersions(allVersions)
                .withPointInTime(pointInTime)
                .withModifiedAfter(modifiedAfter)
                .withIds(roadobjectIds)
                .withArmFilter(armFilter)
                .withSideAreaFilter(sideAreaFilter)
                .withIntersectionFilter(intersectionFilter)
                .withSeparatePassagesFilter(separatePassagesFilter)
                .withRoadUserGroupFilter(roadUserGroupFilter)
                .withRefLinkPartType(refLinkPartType)
                .withTypeOfRoadFilter(typeOfRoadFilter)
                .withDetailLevelFilter(detailLevelFilter);

        overlapFilters.forEach(of -> b.addOverlapFilter(of.filter, of.typeId));
        return b;
    }

    public static class Builder {

        private RefLinkPartType refLinkPartType;
        private Set<DetailLevel> detailLevelFilter = Collections.emptySet();
        private Set<TypeOfRoad> typeOfRoadFilter = Collections.emptySet();

        private Page page = Page.count(1000);
        private Boolean segmented;
        private Boolean allVersions;
        private String depth;
        private Projection projection;
        private Integer distanceTolerance;
        private Set<RoadObjectClient.Include> includes = Collections.emptySet();
        private Set<RoadObjectClient.IncludeGeometry> includeGeometries = Collections.emptySet();
        private Set<RoadObjectClient.IncludeAttribute> includeAttributes = Collections.emptySet();
        private String attributeFilter;
        private String bbox;
        private String bpolygon;
        private String roadRefFilter;
        private String refLinkFilter;
        private List<OverlapFilter> overlapFilters = new ArrayList<>();
        private List<Integer> municipalities = Collections.emptyList();
        private List<Integer> counties = Collections.emptyList();
        private List<String> contractAreas = Collections.emptyList();
        private List<String> nationalRoutes = Collections.emptyList();
        private List<String> streets = Collections.emptyList();
        private List<Long> roadobjectIds = Collections.emptyList();
        private LocalDate pointInTime;
        private Boolean armFilter;
        private Boolean sideAreaFilter;
        private Boolean intersectionFilter;
        private RoadUserGroup roadUserGroupFilter;
        private Set<SeparatePassages> separatePassagesFilter;
        private LocalDateTime modifiedAfter;

        private Builder() { }

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
            this.segmented = segmented;
            return this;
        }

        public Builder withDetailLevelFilter(Set<DetailLevel> detailLevelFilter) {
            this.detailLevelFilter = detailLevelFilter;
            return this;
        }

        public Builder withDetailLevelFilter(DetailLevel... detailLevel) {
            this.detailLevelFilter = new HashSet<>(Arrays.asList(detailLevel));
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

        public Builder withRefLinkPartType(RefLinkPartType refLinkPartType) {
            this.refLinkPartType = refLinkPartType;
            return this;
        }

        public Builder withProjection(Projection projection) {
            this.projection = projection;
            return this;
        }

        public Builder withDepth(Integer depth) {
            this.depth = Optional.ofNullable(depth).map(Object::toString).orElse(null);
            return this;
        }

        public Builder fullDepth() {
            return withDepth("full");
        }

        private Builder withDepth(String depth) {
            this.depth = depth;
            return this;
        }

        public Builder withIncludes(Set<RoadObjectClient.Include> includes) {
            this.includes = includes;
            return this;
        }

        public Builder withIncludes(RoadObjectClient.Include... includes) {
            this.includes = new HashSet<>(Arrays.asList(includes));
            return this;
        }

        public Builder withIncludeGeometries(Set<RoadObjectClient.IncludeGeometry> includes) {
            this.includeGeometries = includes;
            return this;
        }

        public Builder withIncludeAttributes(Set<RoadObjectClient.IncludeAttribute> includeAttributes) {
            this.includeAttributes = includeAttributes;
            return this;
        }

        public Builder withIncludeAttributes(RoadObjectClient.IncludeAttribute... includeAttributes) {
            this.includeAttributes = new HashSet<>(Arrays.asList(includeAttributes));
            return this;
        }

        public Builder withIncludeGeometries(RoadObjectClient.IncludeGeometry... includes) {
            this.includeGeometries = new HashSet<>(Arrays.asList(includes));
            return this;
        }

        public Builder includeAll() {
            return withIncludes(EnumSet.of(RoadObjectClient.Include.ALL));
        }

        public Builder withDistanceTolerance(Integer distanceTolerance) {
            this.distanceTolerance = distanceTolerance;
            return this;
        }

        public Builder withAttributeFilter(String attributeFilter) {
            this.attributeFilter = attributeFilter;
            return this;
        }

        public Builder withBbox(String bbox) {
            this.bbox = bbox;
            return this;
        }

        public Builder withBpolygon(String bpolygon) {
            this.bpolygon = bpolygon;
            return this;
        }

        public Builder withRoadRefFilter(String filter) {
            this.roadRefFilter = filter;
            return this;
        }

        public Builder withRefLinkFilter(String filter) {
            this.refLinkFilter = filter;
            return this;
        }

        public Builder withArmFilter(Boolean arm) {
            this.armFilter = arm;
            return this;
        }

        public Builder withSideAreaFilter(Boolean sideAreaFilter) {
            this.sideAreaFilter = sideAreaFilter;
            return this;
        }

        public Builder withIntersectionFilter(Boolean intersectionFilter) {
            this.intersectionFilter = intersectionFilter;
            return this;
        }

        public Builder withSeparatePassagesFilter(Set<SeparatePassages> separatePassagesFilter) {
            this.separatePassagesFilter = separatePassagesFilter;
            return this;
        }

        public Builder withSeparatePassagesFilter(SeparatePassages... separatePassagesFilter) {
            this.separatePassagesFilter = new HashSet<>(Arrays.asList(separatePassagesFilter));
            return this;
        }

        public Builder withRoadUserGroupFilter(RoadUserGroup roadUserGroupFilter) {
            this.roadUserGroupFilter = roadUserGroupFilter;
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
        public Builder withContractAreas(List<String> contractAreas) {
            this.contractAreas = contractAreas;
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

        public Builder withStreet(String street) {
            this.streets = Collections.singletonList(street);
            return this;
        }

        public Builder withStreets(String... streets) {
            this.streets = Arrays.asList(streets);
            return this;
        }

        public Builder withAllVersions(Boolean allVersions) {
            this.allVersions = allVersions;
            return this;
        }

        public Builder withIds(List<Long> roadobjectIds) {
            this.roadobjectIds = roadobjectIds;
            return this;
        }

        public Builder withPointInTime(LocalDate date) {
            this.pointInTime = date;
            return this;
        }

        public Builder withModifiedAfter(LocalDateTime date) {
            this.modifiedAfter = date;
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
