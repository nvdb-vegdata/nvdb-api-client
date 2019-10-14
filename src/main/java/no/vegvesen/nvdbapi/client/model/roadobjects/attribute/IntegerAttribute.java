package no.vegvesen.nvdbapi.client.model.roadobjects.attribute;

import no.vegvesen.nvdbapi.client.model.datakatalog.Unit;

import java.util.Objects;

public class IntegerAttribute extends Attribute {
    private final Integer value;
    private final Unit unit;

    public IntegerAttribute(int id, Integer value) {
        this(id, value, null);
    }

    public IntegerAttribute(int id, Integer value, Unit unit) {
        super(id);
        this.value = value;
        this.unit = unit;
    }

    public Integer getValue() {
        return value;
    }

    public Unit getUnit() {
        return unit;
    }

    @Override
    public String getValueAsString() {
        return String.valueOf(value);
    }

    @Override
    public AttributeType getAttributeType() {
        return AttributeType.INTEGER;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        IntegerAttribute that = (IntegerAttribute) o;
        return Objects.equals(value, that.value) &&
            Objects.equals(unit, that.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value, unit);
    }
}
