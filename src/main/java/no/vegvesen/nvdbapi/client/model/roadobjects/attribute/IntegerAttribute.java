package no.vegvesen.nvdbapi.client.model.roadobjects.attribute;

public class IntegerAttribute extends Attribute {
    private final Integer value;

    public IntegerAttribute(int id, Integer value) {
        super(id);
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public AttributeType getAttributeType() {
        return AttributeType.INTEGER;
    }
}
