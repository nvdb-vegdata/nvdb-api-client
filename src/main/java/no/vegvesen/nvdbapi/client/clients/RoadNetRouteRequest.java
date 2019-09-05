package no.vegvesen.nvdbapi.client.clients;

import no.vegvesen.nvdbapi.client.model.Coordinates;
import no.vegvesen.nvdbapi.client.model.RefLinkPosition;

public class RoadNetRouteRequest {

    private final RefLinkPosition startReflinkPosition;
    private final RefLinkPosition endReflinkPosition;
    private final Coordinates startCoordinates;
    private final Coordinates endCoordinates;

    private RoadNetRouteRequest(RefLinkPosition startReflinkPosition,
                               RefLinkPosition endReflinkPosition,
                               Coordinates startCoordinates,
                               Coordinates endCoordinates) {
        this.startReflinkPosition = startReflinkPosition;
        this.endReflinkPosition = endReflinkPosition;
        this.startCoordinates = startCoordinates;
        this.endCoordinates = endCoordinates;
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

    public static Builder builder() {
        return new Builder();
    }

    public boolean usesReflinkPosition() {
        return startReflinkPosition != null;
    }

    public static class Builder {
        private RefLinkPosition startReflinkPosition;
        private RefLinkPosition endReflinkPosition;
        private Coordinates startCoordinates;
        private Coordinates endCoordinates;

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

        public RoadNetRouteRequest build() {
            return new RoadNetRouteRequest(
                startReflinkPosition,
                endReflinkPosition,
                startCoordinates,
                endCoordinates
            );
        }
    }
}
