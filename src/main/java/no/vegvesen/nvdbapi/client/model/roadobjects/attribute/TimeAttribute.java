package no.vegvesen.nvdbapi.client.model.roadobjects.attribute;

import java.time.LocalTime;

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
}
