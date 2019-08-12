package no.vegvesen.nvdbapi.client.model.roadobjects.attribute;

public class BooleanAttribute extends Attribute {
    private final Boolean value;

    public BooleanAttribute(int id, Boolean value) {
        super(id);
        this.value = value;
    }

    @Override
    public AttributeType getAttributeType() {
        return AttributeType.BOOLEAN;
    }

    public Boolean getValue() {
        return value;
    }
}
