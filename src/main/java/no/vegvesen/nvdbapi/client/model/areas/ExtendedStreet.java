package no.vegvesen.nvdbapi.client.model.areas;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class ExtendedStreet extends Street implements Serializable {
    private final int municipality;
    private final List<RoadObjectId> objects;

    public ExtendedStreet(String name,
                          int streetCode,
                          Boolean sideStreet,
                          int municipality,
                          List<RoadObjectId> objects) {
        super(name, streetCode, sideStreet);
        this.municipality = municipality;
        this.objects = objects;
    }


    public int getMunicipality() {
        return municipality;
    }

    public List<RoadObjectId> getObjects() {
        return objects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ExtendedStreet street = (ExtendedStreet) o;
        return municipality == street.municipality && Objects.equals(objects, street.objects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), municipality, objects);
    }

    @Override
    public String toString() {
        return "Street{" +
            "name='" + getName() + '\'' +
            ", streetCode=" + getStreetCode() +
            ", municipality=" + municipality +
            ", objects=" + objects +
            "} " + super.toString();
    }
}
