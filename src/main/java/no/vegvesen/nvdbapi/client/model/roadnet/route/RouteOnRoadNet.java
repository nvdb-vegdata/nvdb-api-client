package no.vegvesen.nvdbapi.client.model.roadnet.route;

import java.util.List;
import java.util.Objects;


public class RouteOnRoadNet {
    private final List<RouteSegment> segments;
    public final double length;
    public final RouteStatus status;

    public RouteOnRoadNet(List<RouteSegment> segments, double length, RouteStatus status) {
        this.segments = segments;
        this.length = length;
        this.status = status;
    }

    public List<RouteSegment> getSegments() {
        return segments;
    }

    public double getLength() {
        return length;
    }

    public RouteStatus getStatus() {
        return status;
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
