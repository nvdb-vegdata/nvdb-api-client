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

import com.google.common.collect.ImmutableList;

import java.util.Optional;

public class SegmentationFilter {
    private final ImmutableList<Integer> municipalities;
    private final ImmutableList<Integer> counties;
    private final ImmutableList<Integer> regions;
    private final ImmutableList<Integer> roadDepartments;
    private final ImmutableList<RoadRefFilter> roadRefFilters;

    public SegmentationFilter(ImmutableList<Integer> municipalities, ImmutableList<Integer> counties,
                              ImmutableList<Integer> regions, ImmutableList<Integer> roadDepartments,
                              ImmutableList<RoadRefFilter> roadRefFilters) {
        this.roadRefFilters = Optional.ofNullable(roadRefFilters).orElse(ImmutableList.of());
        this.municipalities = Optional.ofNullable(municipalities).orElse(ImmutableList.of());
        this.counties = Optional.ofNullable(counties).orElse(ImmutableList.of());
        this.regions = Optional.ofNullable(regions).orElse(ImmutableList.of());
        this.roadDepartments = Optional.ofNullable(roadDepartments).orElse(ImmutableList.of());
    }

    public ImmutableList<RoadRefFilter> getRoadRefFilters() {
        return roadRefFilters;
    }

    public ImmutableList<Integer> getMunicipalities() {
        return municipalities;
    }

    public ImmutableList<Integer> getCounties() {
        return counties;
    }

    public ImmutableList<Integer> getRegions() {
        return regions;
    }

    public ImmutableList<Integer> getRoadDepartments() {
        return roadDepartments;
    }
}
