package no.vegvesen.nvdbapi.client.model.roadobjects.attribute;

import java.time.MonthDay;

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
}
