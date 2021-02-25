package no.vegvesen.nvdbapi.client.model.areas;

import java.io.Serializable;
import java.util.Objects;

public class Street implements Serializable {
    private final String name;
    private final int streetCode;
    private final Boolean sideStreet;

    public Street(String name,
                  int streetCode,
                  Boolean sideStreet) {
        this.name = name;
        this.streetCode = streetCode;
        this.sideStreet = sideStreet;
    }

    public String getName() {
        return name;
    }

    public int getStreetCode() {
        return streetCode;
    }

    public Boolean isSideStreet() {
        return sideStreet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Street that = (Street) o;
        return streetCode == that.streetCode
                && Objects.equals(name, that.name)
                && sideStreet == that.sideStreet;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, streetCode, sideStreet);
    }

    @Override
    public String toString() {
        return "Street{" +
            "name='" + name + '\'' +
            ", streetCode=" + streetCode +
            '}';
    }
}
