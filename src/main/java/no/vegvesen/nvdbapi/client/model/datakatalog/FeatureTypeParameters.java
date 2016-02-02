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

package no.vegvesen.nvdbapi.client.model.datakatalog;

import java.io.Serializable;

public class FeatureTypeParameters implements Serializable {

    private final boolean timeRelevance;
    private final Boolean sekType2Ok;
    private final Boolean abstractType;
    private final Boolean filtration;
    private final Boolean derived;
    private final Boolean parentRequired;
    private final Boolean measureSet;
    private final String coverage;

    private final FeatureType.Relevant laneRelevance;
    private final FeatureType.Relevant sidePositionRelevance;
    private final Boolean directionRelevant;
    private final Boolean heightRelevant;
    private final Boolean overlaps;
    private final Boolean movable;
    private final FeatureType.Survivability ajourholdI;
    private final FeatureType.Splitability ajourholdSplitt;

    public FeatureTypeParameters(Boolean timeRelevance, Boolean sekType2Ok, Boolean abstractType, Boolean filtration,
                                 Boolean derived, Boolean parentRequired, Boolean measureSet, String coverage,
                                 FeatureType.Relevant laneRelevance,
                                 FeatureType.Relevant sidePositionRelevance, Boolean directionRelevant,
                                 Boolean heightRelevant, Boolean overlaps, Boolean movable,
                                 FeatureType.Survivability ajourholdI,
                                 FeatureType.Splitability ajourholdSplitt) {
        this.timeRelevance = timeRelevance;
        this.sekType2Ok = sekType2Ok;
        this.abstractType = abstractType;
        this.filtration = filtration;
        this.derived = derived;
        this.parentRequired = parentRequired;
        this.measureSet = measureSet;
        this.coverage = coverage;
        this.laneRelevance = laneRelevance;
        this.sidePositionRelevance = sidePositionRelevance;
        this.directionRelevant = directionRelevant;
        this.heightRelevant = heightRelevant;
        this.overlaps = overlaps;
        this.movable = movable;
        this.ajourholdI = ajourholdI;
        this.ajourholdSplitt = ajourholdSplitt;
    }

    public boolean isTimeRelevant() {
        return timeRelevance;
    }

    public boolean isSekType2Ok() {
        return sekType2Ok;
    }

    public boolean isAbstractType() {
        return abstractType;
    }

    public boolean isFilteringOn() {
        return filtration;
    }

    public boolean isDerived() {
        return derived;
    }

    public boolean isParentRequired() {
        return parentRequired;
    }

    public boolean isMeasureSet() {
        return measureSet;
    }

    public String getCoverage() {
        return coverage;
    }

    public FeatureType.Relevant getLaneRelevance() {
        return laneRelevance;
    }

    public FeatureType.Relevant getSidePositionRelevance() {
        return sidePositionRelevance;
    }

    public boolean isDirectionRelevant() {
        return directionRelevant;
    }

    public boolean isHeightRelevant() {
        return heightRelevant;
    }

    public boolean isOverlap() {
        return overlaps;
    }

    public boolean isMovable() {
        return movable;
    }

    public FeatureType.Survivability getSurvivability() {
        return ajourholdI;
    }

    public FeatureType.Splitability getSplitability() {
        return ajourholdSplitt;
    }
}
