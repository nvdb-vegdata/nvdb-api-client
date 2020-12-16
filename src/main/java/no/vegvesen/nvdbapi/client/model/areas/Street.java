package no.vegvesen.nvdbapi.client.model.areas;

import java.io.Serializable;
import java.util.Objects;

public class Street implements Serializable {
    private final String name;
    private final int streetCode;

    public Street(String name, int streetCode) {
        this.name = name;
        this.streetCode = streetCode;
    }

    public String getName() {
        return name;
    }

    public int getStreetCode() {
        return streetCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Street that = (Street) o;
        return streetCode == that.streetCode && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, streetCode);
    }

    @Override
    public String toString() {
        return "Street{" +
            "name='" + name + '\'' +
            ", streetCode=" + streetCode +
            '}';
    }
}
