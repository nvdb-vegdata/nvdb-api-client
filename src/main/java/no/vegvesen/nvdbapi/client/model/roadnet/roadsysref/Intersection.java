package no.vegvesen.nvdbapi.client.model.roadnet.roadsysref;

import java.util.Objects;

public class Intersection {
    public final int number;
    public final int part;

    public Intersection(int number, int part) {
        this.number = number;
        this.part = part;
    }

    @Override
    public String toString() {
        return "Intersection{" +
            "number=" + number +
            ", systemPart=" + part +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Intersection that = (Intersection) o;
        return number == that.number &&
            part == that.part;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, part);
    }
}
