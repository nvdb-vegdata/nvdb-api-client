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

package no.vegvesen.nvdbapi.client.model;

import no.vegvesen.nvdbapi.client.model.roadnet.roadsysref.RoadSysRef;
import no.vegvesen.nvdbapi.client.model.roadobjects.RoadRef;

import java.util.List;
import java.util.Objects;

public class Position {
    private final List<Result> results;

    public Position(List<Result> results) {
        this.results = results;
    }

    public List<Result> getResults() {
        return results;
    }

    public final static class Result {
        private final RoadPlacement placement;
        private final Double distance;

        public Result(RoadPlacement placement, Double distance) {
            this.placement = placement;
            this.distance = distance;
        }

        public RoadPlacement getPlacement() {
            return placement;
        }

        public Geometry getGeometry() {
            return placement.getPoint();
        }

        public RoadSysRef getRoadRef() {
            return placement.getRoadSysRef();
        }

        public RoadRef getRoadRefInHpMeter() {
            return placement.getRoadSysRef();
        }

        public RefLinkPosition getRefLink() {
            return placement.getRefLinkPosition();
        }

        public Double getDistance() {
            return distance;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Result result = (Result) o;
            return Objects.equals(placement, result.placement) &&
                    Objects.equals(distance, result.distance);
        }

        @Override
        public int hashCode() {
            return Objects.hash(placement, distance);
        }

        @Override
        public String toString() {
            return "Result{" +
                    "placement=" + placement +
                    ", distance=" + distance +
                    '}';
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(results, position.results);
    }

    @Override
    public int hashCode() {
        return Objects.hash(results);
    }

    @Override
    public String toString() {
        return "Position{" +
                "results=" + results +
                '}';
    }
}
