package no.vegvesen.nvdbapi.client.model.roadnet.roadsysref;

import no.vegvesen.nvdbapi.client.model.Direction;
import no.vegvesen.nvdbapi.client.model.roadnet.RoadUserGroup;

import java.util.Objects;

public class SideArea {
    public final Long id;
    public final Integer version;
    public final int number;
    public final int part;
    public final double startMeter;
    public final Double endMeter;
    public final Direction direction;
    public final RoadUserGroup trafficType;

    public SideArea(Long id, Integer version, int number, int part, double startMeter, Double endMeter, Direction direction, RoadUserGroup trafficType) {
        this.id = id;
        this.version = version;
        this.number = number;
        this.part = part;
        this.startMeter = startMeter;
        this.endMeter = endMeter;
        this.direction = direction;
        this.trafficType = trafficType;
    }

    @Override
    public String toString() {
        return "SideArea{" +
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
        SideArea sideArea = (SideArea) o;
        return number == sideArea.number &&
                part == sideArea.part &&
                Double.compare(sideArea.startMeter, startMeter) == 0 &&
                Objects.equals(id, sideArea.id) &&
                Objects.equals(version, sideArea.version) &&
                Objects.equals(direction, sideArea.direction) &&
                Objects.equals(endMeter, sideArea.endMeter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version, number, part, startMeter, endMeter, direction);
    }
}
