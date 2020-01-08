package no.vegvesen.nvdbapi.client.model.roadobjects.attribute;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class TimeAttribute extends Attribute {

    private final LocalTime value;

    public TimeAttribute(int id, LocalTime value) {
        super(id);
        this.value = value;
    }

    public LocalTime getValue() {
        return value;
    }

    @Override
    public AttributeType getAttributeType() {
        return AttributeType.TIME;
    }

    @Override
    public String getValueAsString() {
        return value.format(DateTimeFormatter.ISO_LOCAL_TIME);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TimeAttribute that = (TimeAttribute) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }
}
