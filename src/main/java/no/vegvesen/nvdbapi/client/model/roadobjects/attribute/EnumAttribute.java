package no.vegvesen.nvdbapi.client.model.roadobjects.attribute;

import java.util.Objects;

public abstract class EnumAttribute<T> extends Attribute {
    private final Integer enumId;
    private final T value;

    public EnumAttribute(int id, Integer enumId, T value) {
        super(id);
        this.enumId = enumId;
        this.value = value;
    }

    public Integer getEnumId() {
        return enumId;
    }

    public T getValue() {
        return value;
    }

    @Override
    public String getValueAsString() {
        return enumId.toString();
    }

    @Override
    public AttributeType getAttributeType() {
        return AttributeType.ENUM;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        EnumAttribute that = (EnumAttribute) o;
        return Objects.equals(enumId, that.enumId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), enumId);
    }
}
