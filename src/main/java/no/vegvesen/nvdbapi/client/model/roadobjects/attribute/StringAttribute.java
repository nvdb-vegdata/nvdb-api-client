package no.vegvesen.nvdbapi.client.model.roadobjects.attribute;

import java.util.Objects;

public class StringAttribute extends Attribute {

    private final String value;

    public StringAttribute(int id, String value) {
        super(id);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public AttributeType getAttributeType() {
        return AttributeType.STRING;
    }

    @Override
    public String getValueAsString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        StringAttribute that = (StringAttribute) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }
}
