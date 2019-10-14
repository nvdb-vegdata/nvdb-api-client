package no.vegvesen.nvdbapi.client.model.roadobjects.attribute;

import no.vegvesen.nvdbapi.client.model.datakatalog.Unit;

import java.util.Objects;

public class RealAttribute extends Attribute {

    private final Double value;
    private final Unit unit;

    public RealAttribute(int id, Double value) {
        this(id, value, null);
    }

    public RealAttribute(int id, Double value, Unit unit) {
        super(id);
        this.value = value;
        this.unit = unit;
    }

    public Double getValue() {
        return value;
    }

    @Override
    public AttributeType getAttributeType() {
        return AttributeType.REAL;
    }

    @Override
    public String getValueAsString() {
        return String.valueOf(value);
    }

    public Unit getUnit() {
        return unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RealAttribute that = (RealAttribute) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }
}
