package no.vegvesen.nvdbapi.client.model.areas;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Street implements Serializable {
    private final String name;
    private final int streetCode;
    private final int municipality;
    private final List<RoadObjectId> objects;

    public Street(String name,
                  int streetCode,
                  int municipality,
                  List<RoadObjectId> objects) {
        this.name = name;
        this.streetCode = streetCode;
        this.municipality = municipality;
        this.objects = objects;
    }

    public String getName() {
        return name;
    }

    public int getStreetCode() {
        return streetCode;
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
        Street street = (Street) o;
        return streetCode == street.streetCode &&
            municipality == street.municipality &&
            Objects.equals(name, street.name) &&
            Objects.equals(objects, street.objects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, streetCode, municipality, objects);
    }

    @Override
    public String toString() {
        return "Street{" +
            "name='" + name + '\'' +
            ", streetCode=" + streetCode +
            ", municipality=" + municipality +
            ", objects=" + objects +
            '}';
    }
}
