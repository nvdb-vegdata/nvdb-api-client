package no.vegvesen.nvdbapi.client.model.roadnet.roadsysref;

import java.util.Objects;

public class Intersection {
    public final Long id;
    public final Integer version;
    public final int number;
    public final int part;
    public final double startMeter;
    public final Double endMeter;


    public Intersection(Long id, Integer version, int number, int part, double startMeter, Double endMeter) {
        this.number = number;
        this.part = part;
        this.id = id;
        this.version = version;
        this.startMeter = startMeter;
        this.endMeter = endMeter;
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
                Objects.equals(version, that.version) &&
                Objects.equals(endMeter, that.endMeter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version, number, part, startMeter, endMeter);
    }
}
