package no.vegvesen.nvdbapi.client.model.roadobjects.attribute;

import java.util.Objects;

public class EnumAttribute extends Attribute {
    private final Integer enumId;

    public EnumAttribute(int id, Integer enumId) {
        super(id);
        this.enumId = enumId;
    }

    public Integer getEnumId() {
        return enumId;
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
