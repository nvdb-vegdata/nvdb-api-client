package no.vegvesen.nvdbapi.client.model.roadobjects.attribute;

import java.util.Objects;

public class BooleanAttribute extends Attribute {
    private final Boolean value;

    public BooleanAttribute(int id, Boolean value) {
        super(id);
        this.value = value;
    }

    @Override
    public AttributeType getAttributeType() {
        return AttributeType.BOOLEAN;
    }

    public Boolean getValue() {
        return value;
    }

    @Override
    public String getValueAsString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BooleanAttribute that = (BooleanAttribute) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }
}
