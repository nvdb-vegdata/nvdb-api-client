package no.vegvesen.nvdbapi.client.model.roadnet.roadsysref;

import no.vegvesen.nvdbapi.client.model.Direction;
import no.vegvesen.nvdbapi.client.model.roadnet.RoadUserGroup;

import java.util.Objects;

public class Intersection {
    public final Long id;
    public final Integer version;
    public final int number;
    public final int part;
    public final double startMeter;
    public final Double endMeter;
    public final Direction direction;
    public final RoadUserGroup trafficType;

    public Intersection(Long id,
                        Integer version,
                        int number,
                        int part,
                        double startMeter,
                        Double endMeter,
                        Direction direction, RoadUserGroup trafficType) {
        this.number = number;
        this.part = part;
        this.id = id;
        this.version = version;
        this.startMeter = startMeter;
        this.endMeter = endMeter;
        this.direction = direction;
        this.trafficType = trafficType;
    }

    @Override
    public String toString() {
        return "Intersection{" +
                "id=" + id +
                ", version=" + version +
                ", number=" + number +
                ", part=" + part +
                ", startMeter='" + startMeter + '\'' +
                ", endMeter='" + endMeter + '\'' +
                ", direction='" + direction + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Intersection that = (Intersection) o;
        return number == that.number &&
                part == that.part &&
                Double.compare(that.startMeter, startMeter) == 0 &&
                Objects.equals(id, that.id) &&
                Objects.equals(direction, that.direction) &&
                Objects.equals(version, that.version) &&
                Objects.equals(endMeter, that.endMeter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version, number, part, startMeter, endMeter, direction);
    }
}
