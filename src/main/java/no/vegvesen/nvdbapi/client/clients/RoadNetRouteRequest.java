package no.vegvesen.nvdbapi.client.clients;

import no.vegvesen.nvdbapi.client.model.Coordinates;
import no.vegvesen.nvdbapi.client.model.Geometry;
import no.vegvesen.nvdbapi.client.model.RefLinkPosition;
import no.vegvesen.nvdbapi.client.model.roadnet.RoadUserGroup;
import no.vegvesen.nvdbapi.client.model.roadnet.TypeOfRoad;
import no.vegvesen.nvdbapi.client.model.roadnet.route.RouteField;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Objects.nonNull;

public class RoadNetRouteRequest {

    private final RefLinkPosition startReflinkPosition;
    private final RefLinkPosition endReflinkPosition;
    private final Coordinates startCoordinates;
    private final Coordinates endCoordinates;
    private final Geometry geometry;
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

    private RoadNetRouteRequest(Builder b) {
        this.startReflinkPosition = b.startReflinkPosition;
        this.endReflinkPosition = b.endReflinkPosition;
        this.startCoordinates = b.startCoordinates;
        this.endCoordinates = b.endCoordinates;
        this.geometry = b.geometry;
        this.distance = b.distance;
        this.envelope = b.envelope;
        this.briefResponse = b.briefResponse;
        this.connectionLinks = b.connectionLinks;
        this.detailedLinks = b.detailedLinks;
        this.roadRefFilter = b.roadSysRefs;
        this.roadUserGroup = b.roadUserGroup;
        this.typeOfRoad = b.typeOfRoad;
        this.pointInTime = b.pointInTime;
        this.startPointInTime = b.startPointInTime;
        this.endPointInTime = b.endPointInTime;
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

    public int getDistance() {
        return distance;
    }

    public int getEnvelope() {
        return envelope;
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

    public Optional<LocalDate> getPointInTime() {
        return pointInTime;
    }

    public Optional<LocalDate> getStartPointInTime() {
        return startPointInTime;
    }

    public Optional<LocalDate> getEndPointInTime() {
        return endPointInTime;
    }

    public Map<String, String> getJsonObject() {
        Map<String, String> jsonMap = new HashMap<>();

        if (startReflinkPosition != null) jsonMap.put(RouteField.START.getName(), String.valueOf(startReflinkPosition));
        if (endReflinkPosition != null) jsonMap.put(RouteField.END.getName(), String.valueOf(endReflinkPosition));
        if (geometry != null) jsonMap.put(RouteField.GEOMETRY.getName(), geometry.toString(false));
        jsonMap.put(RouteField.DISTANCE.getName(), String.valueOf(distance));
        jsonMap.put(RouteField.ENVELOPE.getName(), String.valueOf(envelope));
        jsonMap.put(RouteField.BRIEF_RESPONSE.getName(), String.valueOf(briefResponse));
        jsonMap.put(RouteField.CONNECTION_LINKS.getName(), String.valueOf(connectionLinks));
        jsonMap.put(RouteField.DETAILED_LINKS.getName(), String.valueOf(detailedLinks));
        roadRefFilter.ifPresent(s -> jsonMap.put(RouteField.ROAD_SYS_REFS.getName(), s));
        roadUserGroup.ifPresent(userGroup -> jsonMap.put(RouteField.ROAD_USER_GROUP.getName(), userGroup.getTextValue()));
        if (!typeOfRoad.isEmpty()) jsonMap.put(RouteField.TYPE_OF_ROAD.getName(), typeOfRoad.stream().map(TypeOfRoad::getTypeOfRoadSosi).collect(Collectors.joining(",")));
        if (pointInTime.isPresent()) jsonMap.put(RouteField.POINT_IN_TIME.getName(), String.valueOf(pointInTime));
        if (startPointInTime.isPresent()) jsonMap.put(RouteField.START_POINT_IN_TIME.getName(), String.valueOf(startPointInTime));
        if (endPointInTime.isPresent()) jsonMap.put(RouteField.END_POINT_IN_TIME.getName(), String.valueOf(endPointInTime));

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
        private Geometry geometry;
        private int distance = 10;
        private int envelope = 100;
        private boolean briefResponse = false;
        private boolean connectionLinks = true;
        private boolean detailedLinks = false;
        private Optional<String> roadSysRefs = Optional.empty();
        private Optional<RoadUserGroup> roadUserGroup = Optional.empty();
        private List<TypeOfRoad> typeOfRoad = emptyList();
        private Optional<LocalDate> pointInTime = Optional.empty();
        private Optional<LocalDate> startPointInTime = Optional.empty();
        private Optional<LocalDate> endPointInTime = Optional.empty();

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

        public Builder fromGeometry(Geometry geometry) {
            this.geometry = geometry;
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
            this.roadSysRefs = Optional.ofNullable(filter);
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

        public RoadNetRouteRequest build() {
            return new RoadNetRouteRequest(this);
        }
    }
}
