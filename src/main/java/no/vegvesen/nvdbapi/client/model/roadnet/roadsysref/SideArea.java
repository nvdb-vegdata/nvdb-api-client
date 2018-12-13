package no.vegvesen.nvdbapi.client.model.roadnet.roadsysref;

import java.util.Objects;

public class SideArea {
    public final int number;
    public final int part;

    public SideArea(int number, int part) {
        this.number = number;
        this.part = part;
    }

    @Override
    public String toString() {
        return "SideArea{" +
            "number=" + number +
            ", part=" + part +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SideArea sideArea = (SideArea) o;
        return number == sideArea.number &&
            Objects.equals(part, sideArea.part);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, part);
    }
}
