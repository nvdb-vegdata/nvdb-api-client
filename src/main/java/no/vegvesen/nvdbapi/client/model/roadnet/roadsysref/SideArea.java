package no.vegvesen.nvdbapi.client.model.roadnet.roadsysref;

import java.util.Objects;

public class SideArea {
    public final Long id;
    public final Integer version;
    public final int number;
    public final int part;

    public SideArea(Long id, Integer version, int number, int part) {
        this.id = id;
        this.version = version;
        this.number = number;
        this.part = part;
    }

    @Override
    public String toString() {
        return "SideArea{" +
                "id=" + id +
                ", version=" + version +
                ", number=" + number +
                ", part=" + part +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SideArea sideArea = (SideArea) o;
        return number == sideArea.number &&
                part == sideArea.part &&
                Objects.equals(id, sideArea.id) &&
                Objects.equals(version, sideArea.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version, number, part);
    }
}
