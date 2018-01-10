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

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SegmentationFilter {
    private final List<Integer> municipalities;
    private final List<Integer> counties;
    private final List<Integer> regions;
    private final List<Integer> roadDepartments;
    private final List<RoadRefFilter> roadRefFilters;

    public SegmentationFilter(List<Integer> municipalities, List<Integer> counties,
                              List<Integer> regions, List<Integer> roadDepartments,
                              List<RoadRefFilter> roadRefFilters) {
        this.roadRefFilters = Optional.ofNullable(roadRefFilters).orElse(Collections.emptyList());
        this.municipalities = Optional.ofNullable(municipalities).orElse(Collections.emptyList());
        this.counties = Optional.ofNullable(counties).orElse(Collections.emptyList());
        this.regions = Optional.ofNullable(regions).orElse(Collections.emptyList());
        this.roadDepartments = Optional.ofNullable(roadDepartments).orElse(Collections.emptyList());
    }

    public List<RoadRefFilter> getRoadRefFilters() {
        return roadRefFilters;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SegmentationFilter that = (SegmentationFilter) o;
        return Objects.equals(municipalities, that.municipalities) &&
                Objects.equals(counties, that.counties) &&
                Objects.equals(regions, that.regions) &&
                Objects.equals(roadDepartments, that.roadDepartments) &&
                Objects.equals(roadRefFilters, that.roadRefFilters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(municipalities, counties, regions, roadDepartments, roadRefFilters);
    }
}
