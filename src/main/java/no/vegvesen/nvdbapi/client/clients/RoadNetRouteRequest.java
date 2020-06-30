package no.vegvesen.nvdbapi.client.clients;

import no.vegvesen.nvdbapi.client.model.Coordinates;
import no.vegvesen.nvdbapi.client.model.Geometry;
import no.vegvesen.nvdbapi.client.model.RefLinkPosition;
import no.vegvesen.nvdbapi.client.model.roadnet.RoadUserGroup;
import no.vegvesen.nvdbapi.client.model.roadnet.TypeOfRoad;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Objects.nonNull;

public class RoadNetRouteRequest {

    private final RefLinkPosition startReflinkPosition;
    private final RefLinkPosition endReflinkPosition;
    private final Coordinates startCoordinates;
    private final Coordinates endCoordinates;
    private final boolean connectionLinks;
    private final boolean detailedLinks;
    private final Geometry geometry;
    private final int distanceThreshold;
    private final int circumferenceAroundPoints;
    private final Optional<String> roadRefFilter;
    private final Optional<RoadUserGroup> roadUserGroup;
    private final List<TypeOfRoad> typeOfRoad;
    private final boolean briefResponse;
    private final Optional<LocalDate> pointInTime;

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
        this.connectionLinks = b.connectionLinks;
        this.detailedLinks = b.detailedLinks;
        this.roadUserGroup = b.roadUserGroup;
        this.typeOfRoad = b.typeOfRoad;
        this.pointInTime = b.pointInTime;
    }

    public Optional<LocalDate> getPointInTime() {
        return pointInTime;
    }

    public List<TypeOfRoad> getTypeOfRoad() {
        return typeOfRoad;
    }

    public Optional<String> getRoadRefFilter() {
        return roadRefFilter;
    }

    public Optional<RoadUserGroup> getRoadUserGroup() {
        return roadUserGroup;
    }

    public boolean isConnectionLinks() {
        return connectionLinks;
    }

    public boolean isDetailedLinks() {
        return detailedLinks;
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

    public boolean usesGeometry() {
        return nonNull(geometry);
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public boolean isBriefResponse() {
        return briefResponse;
    }

    public Map<String, String> getJsonObject() {
        Map<String, String> jsonMap = new HashMap<>();

        if (startReflinkPosition != null) jsonMap.put("start", String.valueOf(startReflinkPosition));
        if (endReflinkPosition != null) jsonMap.put("slutt", String.valueOf(endReflinkPosition));
        if (geometry != null) jsonMap.put("geometri", geometry.toString(false));
        jsonMap.put("kortform", String.valueOf(briefResponse));
        jsonMap.put("konnekteringslenker", String.valueOf(connectionLinks));
        jsonMap.put("detaljerte_lenker", String.valueOf(detailedLinks));
        jsonMap.put("maks_avstand", String.valueOf(distanceThreshold));
        jsonMap.put("omkrets", String.valueOf(circumferenceAroundPoints));
        roadRefFilter.ifPresent(s -> jsonMap.put("vegsystemreferanse", s));
        roadUserGroup.ifPresent(userGroup -> jsonMap.put("trafikantgruppe", userGroup.getTextValue()));
        if (pointInTime.isPresent()) jsonMap.put("tidspunkt", String.valueOf(pointInTime));

        return jsonMap;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private RefLinkPosition startReflinkPosition;
        private RefLinkPosition endReflinkPosition;
        private Coordinates startCoordinates;
        private Coordinates endCoordinates;
        private int distanceThreshold = 10;
        private int circumferenceAroundPoints = 100;
        private Geometry geometry;
        private Optional<String> roadRefFilter = Optional.empty();
        private Optional<RoadUserGroup> roadUserGroup = Optional.empty();
        private boolean briefReponse = false;
        private boolean connectionLinks = false;
        private boolean detailedLinks = false;
        private List<TypeOfRoad> typeOfRoad = emptyList();
        private Optional<LocalDate> pointInTime = Optional.empty();

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

        public Builder withPointInTime(LocalDate pointInTime) {
            this.pointInTime = Optional.ofNullable(pointInTime);
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

        public Builder withRoadUserGroup(RoadUserGroup roadUserGroup) {
            this.roadUserGroup = Optional.ofNullable(roadUserGroup);
            return this;
        }

        public Builder withTypeOfRoad(List<TypeOfRoad> typeOfRoad) {
            this.typeOfRoad = typeOfRoad;
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

        public Builder withConnectionLinks(boolean connectionLinks) {
            this.connectionLinks = connectionLinks;
            return this;
        }

        public Builder withDetailedLinks(boolean detailedLinks) {
            this.detailedLinks = detailedLinks;
            return this;
        }

        public RoadNetRouteRequest build() {
            return new RoadNetRouteRequest(this);
        }
    }
}
