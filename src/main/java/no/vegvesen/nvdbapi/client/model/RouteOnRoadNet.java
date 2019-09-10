package no.vegvesen.nvdbapi.client.model;

import no.vegvesen.nvdbapi.client.model.roadnet.SegmentedLink;

import java.util.List;
import java.util.Objects;

public class RouteOnRoadNet {
    private final RoadPlacement start;
    private final RoadPlacement end;
    private final List<SegmentedLink> segments;

    public RouteOnRoadNet(RoadPlacement start,
                          RoadPlacement end,
                          List<SegmentedLink> segments) {
        this.start = start;
        this.end = end;
        this.segments = segments;
    }

    public RoadPlacement getStart() {
        return start;
    }

    public RoadPlacement getEnd() {
        return end;
    }

    public List<SegmentedLink> getSegments() {
        return segments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RouteOnRoadNet that = (RouteOnRoadNet) o;
        return Objects.equals(start, that.start) &&
            Objects.equals(end, that.end) &&
            Objects.equals(segments, that.segments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, segments);
    }
}
