package no.vegvesen.nvdbapi.client.model.roadobjects.attribute;

import java.util.Objects;

public abstract class Attribute {
    private final int id;

    public Attribute(int id) {
        this.id = id;
    }

    public abstract AttributeType getAttributeType();

    public int getId() {
        return id;
    }

    public abstract String getValueAsString();

    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attribute attribute = (Attribute) o;
        return id == attribute.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
