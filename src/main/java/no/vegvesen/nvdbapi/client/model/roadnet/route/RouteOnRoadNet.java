package no.vegvesen.nvdbapi.client.model.roadnet.route;

import java.util.List;
import java.util.Objects;

public class RouteOnRoadNet {
    private final List<RouteSegment> segments;

    public RouteOnRoadNet(List<RouteSegment> segments) {
        this.segments = segments;
    }

    public List<RouteSegment> getSegments() {
        return segments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RouteOnRoadNet that = (RouteOnRoadNet) o;
        return Objects.equals(segments, that.segments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(segments);
    }
}
