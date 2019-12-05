package no.vegvesen.nvdbapi.client.clients;

import no.vegvesen.nvdbapi.client.model.Coordinates;
import no.vegvesen.nvdbapi.client.model.RefLinkPosition;

public class RoadNetRouteRequest {

    private final RefLinkPosition startReflinkPosition;
    private final RefLinkPosition endReflinkPosition;
    private final Coordinates startCoordinates;
    private final Coordinates endCoordinates;
    private final int distanceThreshold;
    private final int circumferenceAroundPoints;

    private RoadNetRouteRequest(RefLinkPosition startReflinkPosition,
                               RefLinkPosition endReflinkPosition,
                               Coordinates startCoordinates,
                               Coordinates endCoordinates,
                               int distanceThreshold,
                               int circumferenceAroundPoints) {
        this.startReflinkPosition = startReflinkPosition;
        this.endReflinkPosition = endReflinkPosition;
        this.startCoordinates = startCoordinates;
        this.endCoordinates = endCoordinates;
        this.distanceThreshold = distanceThreshold;
        this.circumferenceAroundPoints = circumferenceAroundPoints;
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

    public boolean usesReflinkPosition() {
        return startReflinkPosition != null;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private RefLinkPosition startReflinkPosition;
        private RefLinkPosition endReflinkPosition;
        private Coordinates startCoordinates;
        private Coordinates endCoordinates;
        private int distanceThreshold;
        private int circumferenceAroundPoints;

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

        public Builder withCircumferenceAroundPoints(int circumferenceAroundPoints) {
            this.circumferenceAroundPoints = circumferenceAroundPoints;
            return this;
        }

        public RoadNetRouteRequest build() {
            return new RoadNetRouteRequest(
                    startReflinkPosition,
                    endReflinkPosition,
                    startCoordinates,
                    endCoordinates,
                    distanceThreshold,
                    circumferenceAroundPoints);
        }
    }
}
