package no.vegvesen.nvdbapi.client.clients;

import no.vegvesen.nvdbapi.client.model.Coordinates;
import no.vegvesen.nvdbapi.client.model.Projection;
import no.vegvesen.nvdbapi.client.model.RefLinkPosition;
import no.vegvesen.nvdbapi.client.model.roadnet.RoadUserGroup;
import no.vegvesen.nvdbapi.client.model.roadnet.TypeOfRoad;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Objects.nonNull;

public class RoadNetRouteRequest {

    private final RefLinkPosition startReflinkPosition;
    private final RefLinkPosition endReflinkPosition;
    private final Coordinates startCoordinates;
    private final Coordinates endCoordinates;
    private final String geometry;
    private final int distance;
    private final int envelope;
    private final boolean briefResponse;
    private final boolean connectionLinks;
    private final boolean detailedLinks;
    private final Optional<String> roadRefFilter;
    private final Optional<RoadUserGroup> roadUserGroup;
    private final List<TypeOfRoad> typeOfRoad;
    private final Optional<LocalDate> pointInTime;
    private final Optional<LocalDate> startPointInTime;
    private final Optional<LocalDate> endPointInTime;
    private final boolean keepRoadUserGroup;
    private final Projection projection;

    private RoadNetRouteRequest(Builder b) {
        this.startReflinkPosition = b.startReflinkPosition;
        this.endReflinkPosition = b.endReflinkPosition;
        this.startCoordinates = b.startCoordinates;
        this.endCoordinates = b.endCoordinates;
        this.geometry = b.geometry;
        this.projection = b.projection;
        this.distance = b.distance;
        this.envelope = b.envelope;
        this.briefResponse = b.briefResponse;
        this.connectionLinks = b.connectionLinks;
        this.detailedLinks = b.detailedLinks;
        this.roadRefFilter = b.roadRefFilter;
        this.roadUserGroup = b.roadUserGroup;
        this.typeOfRoad = b.typeOfRoad;
        this.pointInTime = b.pointInTime;
        this.startPointInTime = b.startPointInTime;
        this.endPointInTime = b.endPointInTime;
        this.keepRoadUserGroup = b.keepRoadUserGroup;
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

    public String getGeometry() {
        return geometry;
    }

    public int getDistance() {
        return distance;
    }

    public int getEnvelope() {
        return envelope;
    }

    public boolean isBriefResponse() {
        return briefResponse;
    }

    public boolean isConnectionLinks() {
        return connectionLinks;
    }

    public boolean isDetailedLinks() {
        return detailedLinks;
    }

    public Optional<String> getRoadRefFilter() {
        return roadRefFilter;
    }

    public Optional<RoadUserGroup> getRoadUserGroup() {
        return roadUserGroup;
    }

    public List<TypeOfRoad> getTypeOfRoad() {
        return typeOfRoad;
    }

    public Optional<LocalDate> getPointInTime() {
        return pointInTime;
    }

    public Optional<LocalDate> getStartPointInTime() {
        return startPointInTime;
    }

    public Optional<LocalDate> getEndPointInTime() {
        return endPointInTime;
    }

    public boolean usesReflinkPosition() {
        return startReflinkPosition != null;
    }

    public boolean usesGeometry() {
        return nonNull(geometry);
    }

    public static Builder builder() {
        return new Builder();
    }

    public boolean isKeepRoadUserGroup() {
        return keepRoadUserGroup;
    }

    public Projection getProjection() {
        return projection;
    }

    public static class Builder {
        private RefLinkPosition startReflinkPosition;
        private RefLinkPosition endReflinkPosition;
        private Coordinates startCoordinates;
        private Coordinates endCoordinates;
        private String geometry;
        private Projection projection = Projection.UTM33;
        private int distance = 10;
        private int envelope = 100;
        private boolean briefResponse = false;
        private boolean connectionLinks = true;
        private boolean detailedLinks = false;
        private Optional<String> roadRefFilter = Optional.empty();
        private Optional<RoadUserGroup> roadUserGroup = Optional.empty();
        private List<TypeOfRoad> typeOfRoad = emptyList();
        private Optional<LocalDate> pointInTime = Optional.empty();
        private Optional<LocalDate> startPointInTime = Optional.empty();
        private Optional<LocalDate> endPointInTime = Optional.empty();
        public boolean keepRoadUserGroup = false;

        public Builder between(RefLinkPosition startReflinkPosition, RefLinkPosition endReflinkPosition) {
            this.startReflinkPosition = startReflinkPosition;
            this.endReflinkPosition = endReflinkPosition;
            return this;
        }

        public Builder between(Coordinates startCoordinates, Coordinates endCoordinates) {
            this.startCoordinates = startCoordinates;
            this.endCoordinates = endCoordinates;
            return this;
        }

        public Builder fromGeometry(String geometry) {
            this.geometry = geometry;
            return this;
        }

        public Builder withProjection(Projection projection) {
            this.projection = projection;
            return this;
        }

        public Builder withDistance(int distance) {
            this.distance = distance;
            return this;
        }

        public Builder withEnvelope(int envelope) {
            this.envelope = envelope;
            return this;
        }

        public Builder withBriefResponse(boolean briefResponse) {
            this.briefResponse = briefResponse;
            return this;
        }

        public Builder withConnectionLinks(boolean connectionLinks) {
            this.connectionLinks = connectionLinks;
            return this;
        }

        public Builder withDetailedLinks(boolean detailedLinks) {
            this.detailedLinks = detailedLinks;
            return this;
        }

        public Builder withRoadRefFilter(String filter) {
            this.roadRefFilter = Optional.ofNullable(filter);
            return this;
        }

        public Builder withRoadUserGroup(RoadUserGroup roadUserGroup) {
            this.roadUserGroup = Optional.ofNullable(roadUserGroup);
            return this;
        }

        public Builder withTypeOfRoad(List<TypeOfRoad> typeOfRoad) {
            this.typeOfRoad = typeOfRoad;
            return this;
        }

        public Builder withPointInTime(LocalDate pointInTime) {
            this.pointInTime = Optional.ofNullable(pointInTime);
            return this;
        }

        public Builder withStartPointInTime(LocalDate startPointInTime) {
            this.startPointInTime = Optional.ofNullable(startPointInTime);
            return this;
        }

        public Builder withEndPointInTime(LocalDate endPointInTime) {
            this.endPointInTime = Optional.ofNullable(endPointInTime);
            return this;
        }

        public Builder withKeepRoadUserGroup(boolean keepRoadUserGroup){
            this.keepRoadUserGroup = keepRoadUserGroup;
            return this;
        }

        public RoadNetRouteRequest build() {
            return new RoadNetRouteRequest(this);
        }
    }
}
