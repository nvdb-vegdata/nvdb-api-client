package no.vegvesen.nvdbapi.client.clients;

import no.vegvesen.nvdbapi.client.model.Coordinates;
import no.vegvesen.nvdbapi.client.model.Geometry;
import no.vegvesen.nvdbapi.client.model.RefLinkPosition;

import java.util.Optional;

import static java.util.Objects.nonNull;

public class RoadNetRouteRequest {

    private final RefLinkPosition startReflinkPosition;
    private final RefLinkPosition endReflinkPosition;
    private final Coordinates startCoordinates;
    private final Coordinates endCoordinates;
    private final Geometry geometry;
    private final int distanceThreshold;
    private final int circumferenceAroundPoints;
    private final Optional<String> roadRefFilter;
    private final boolean briefResponse;

    private RoadNetRouteRequest(Builder b) {
        this.startReflinkPosition = b.startReflinkPosition;
        this.endReflinkPosition = b.endReflinkPosition;
        this.startCoordinates = b.startCoordinates;
        this.endCoordinates = b.endCoordinates;
        this.geometry = b.geometry;
        this.distanceThreshold = b.distanceThreshold;
        this.circumferenceAroundPoints = b.circumferenceAroundPoints;
        this.roadRefFilter = b.roadRefFilter;
        this.briefResponse = b.briefReponse;
    }

    public RefLinkPosition getStartReflinkPosition() {
        return startReflinkPosition;
    }

    public RefLinkPosition getEndReflinkPosition() {
        return endReflinkPosition;
    }

    public Coordinates getStartCoordinates() {
        return startCoordinates;
    }

    public Coordinates getEndCoordinates() {
        return endCoordinates;
    }

    public int getDistanceThreshold() {
        return distanceThreshold;
    }

    public int getCircumferenceAroundPoints() {
        return circumferenceAroundPoints;
    }

    public static Builder builder() {
        return new Builder();
    }

    public boolean usesReflinkPosition() {
        return startReflinkPosition != null;
    }

    public boolean usesGeometry() {
        return nonNull(geometry);
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public boolean isBriefResponse() {
        return briefResponse;
    }

    public static class Builder {
        private RefLinkPosition startReflinkPosition;
        private RefLinkPosition endReflinkPosition;
        private Coordinates startCoordinates;
        private Coordinates endCoordinates;
        private int distanceThreshold;
        private int circumferenceAroundPoints;
        private Geometry geometry;
        private Optional<String> roadRefFilter;
        private boolean briefReponse = false;

        public Builder between(RefLinkPosition startReflinkPosition,
                               RefLinkPosition endReflinkPosition) {
            this.startReflinkPosition = startReflinkPosition;
            this.endReflinkPosition = endReflinkPosition;
            return this;
        }

        public Builder between(Coordinates startCoordinates,
                               Coordinates endCoordinates) {
            this.startCoordinates = startCoordinates;
            this.endCoordinates = endCoordinates;
            return this;
        }

        public Builder withDistanceThreshold(int distanceThreshold) {
            this.distanceThreshold = distanceThreshold;
            return this;
        }

        public Builder withRoadRefFilter(String filter) {
            this.roadRefFilter = Optional.ofNullable(filter);
            return this;
        }

        public Builder withCircumferenceAroundPoints(int circumferenceAroundPoints) {
            this.circumferenceAroundPoints = circumferenceAroundPoints;
            return this;
        }

        public Builder fromGeometry(Geometry geometry) {
            this.geometry = geometry;
            return this;
        }

        public Builder withBriefResponse(boolean briefResponse) {
            this.briefReponse = briefResponse;
            return this;
        }

        public RoadNetRouteRequest build() {
            return new RoadNetRouteRequest(this);
        }
    }
}
