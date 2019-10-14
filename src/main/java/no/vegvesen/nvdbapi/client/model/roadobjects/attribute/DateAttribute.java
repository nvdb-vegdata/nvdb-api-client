package no.vegvesen.nvdbapi.client.model.roadobjects.attribute;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class DateAttribute extends Attribute {

    private final LocalDate value;

    public DateAttribute(int id, LocalDate value) {
        super(id);
        this.value = value;
    }

    public LocalDate getValue() {
        return value;
    }

    @Override
    public AttributeType getAttributeType() {
        return AttributeType.DATE;
    }

    @Override
    public String getValueAsString() {
        return value.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DateAttribute that = (DateAttribute) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }
}
