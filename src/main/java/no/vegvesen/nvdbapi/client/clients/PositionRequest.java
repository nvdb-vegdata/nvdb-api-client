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
import no.vegvesen.nvdbapi.client.model.roadnet.RoadUserGroup;

import java.time.LocalDate;
import java.util.Optional;

public class PositionRequest {

    private final Optional<Double> north;
    private final Optional<Double> east;
    private final Optional<Double> lat;
    private final Optional<Double> lon;
    private final Optional<Projection> projection;
    private final Optional<Integer> maxDistance;
    private final Optional<Integer> maxResults;
    private final Optional<Boolean> connectionLinks;
    private final Optional<Boolean> detailedLinks;
    private final Optional<String> roadRefFilters;
    private final Optional<LocalDate> dateFilter;

    private final Optional<RoadUserGroup> roadUserGroup;

    private PositionRequest(Builder builder) {
        north = builder.north;
        east = builder.east;
        lat = builder.lat;
        lon = builder.lon;
        projection = builder.projection;
        maxDistance = builder.maxDistance;
        maxResults = builder.maxResults;
        connectionLinks = builder.connectionLinks;
        detailedLinks = builder.detailedLinks;
        roadRefFilters = builder.roadRefFilters;
        dateFilter = builder.dateFilter;
        roadUserGroup = builder.roadUserGroup;
    }

    public static Builder utm33(double northing, double easting) {
        return new Builder().withUtm(northing, easting);
    }

    public static Builder wgs84(double lat, double lon) {
        return new Builder().withWgs84(lat, lon);
    }

    public Optional<Double> getNorth() {
        return north;
    }

    public Optional<Double> getEast() {
        return east;
    }

    public Optional<Double> getLat() {
        return lat;
    }

    public Optional<Double> getLon() {
        return lon;
    }

    public Optional<Projection> getProjection() {
        return projection;
    }

    public Optional<Integer> getMaxDistance() {
        return maxDistance;
    }

    public Optional<Integer> getMaxResults() {
        return maxResults;
    }

    public Optional<Boolean> getConnectionLinks() {
        return connectionLinks;
    }

    public Optional<Boolean> getDetailedLinks() {
        return detailedLinks;
    }

    public Optional<String> getRoadRefFilters() {
        return roadRefFilters;
    }

    public Optional<LocalDate> getDateFilter() {
        return dateFilter;
    }

    public Optional<RoadUserGroup> getRoadUserGroup(){ return roadUserGroup; }

    public static class Builder {
        private Optional<Double> north = Optional.empty();
        private Optional<Double> east = Optional.empty();
        private Optional<Double> lat = Optional.empty();
        private Optional<Double> lon = Optional.empty();
        private Optional<Projection> projection = Optional.of(Projection.UTM33);
        private Optional<Integer> maxDistance = Optional.of(30);
        private Optional<Integer> maxResults = Optional.of(1);
        private Optional<Boolean> connectionLinks = Optional.of(false);
        private Optional<Boolean> detailedLinks = Optional.of(false);
        private Optional<String> roadRefFilters = Optional.empty();
        private Optional<LocalDate> dateFilter = Optional.empty();

        private Optional<RoadUserGroup> roadUserGroup = Optional.empty();

        private Builder() {
        }

        public PositionRequest build() {
            return new PositionRequest(this);
        }

        private Builder withUtm(Double north, Double east) {
            this.north = Optional.ofNullable(north);
            this.east = Optional.ofNullable(east);
            return this;
        }

        private Builder withWgs84(Double lat, Double lon) {
            this.lat = Optional.ofNullable(lat);
            this.lon = Optional.ofNullable(lon);
            return this;
        }

        public Builder withProjection(Projection projection) {
            this.projection = Optional.ofNullable(projection);
            return this;
        }

        public Builder withMaxDistance(Integer maxDistance) {
            this.maxDistance = Optional.ofNullable(maxDistance);
            return this;
        }

        public Builder withMaxResults(Integer maxResults) {
            this.maxResults = Optional.ofNullable(maxResults);
            return this;
        }

        public Builder withConnectionLinks(Boolean connectionLinks) {
            this.connectionLinks = Optional.ofNullable(connectionLinks);
            return this;
        }

        public Builder withDetailedLinks(Boolean detailedLinks) {
            this.detailedLinks = Optional.ofNullable(detailedLinks);
            return this;
        }

        public Builder withRoadRefFilters(String roadRefFilters) {
            this.roadRefFilters = Optional.ofNullable(roadRefFilters);
            return this;
        }

        public Builder withDateFilter(LocalDate dateFilter) {
            this.dateFilter = Optional.ofNullable(dateFilter);
            return this;
        }

        public Builder withRoadUserGroup(RoadUserGroup roadUserGroup) {
            this.roadUserGroup = Optional.ofNullable(roadUserGroup);
            return this;
        }
    }
}


