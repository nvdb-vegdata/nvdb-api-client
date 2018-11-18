package no.vegvesen.nvdbapi.client.model.roadobjects.attribute;

import java.time.LocalDate;

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
}
