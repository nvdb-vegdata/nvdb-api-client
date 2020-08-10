package no.vegvesen.nvdbapi.client.model.roadobjects.attribute;

import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class ShortDateAttribute extends Attribute {
    private final MonthDay value;

    public ShortDateAttribute(int id, MonthDay value) {
        super(id);
        this.value = value;
    }

    public MonthDay getValue() {
        return value;
    }

    @Override
    public AttributeType getAttributeType() {
        return AttributeType.SHORTDATE;
    }

    @Override
    public String getValueAsString() {
        return value.format(DateTimeFormatter.ofPattern("--MM-dd"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ShortDateAttribute that = (ShortDateAttribute) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }
}
