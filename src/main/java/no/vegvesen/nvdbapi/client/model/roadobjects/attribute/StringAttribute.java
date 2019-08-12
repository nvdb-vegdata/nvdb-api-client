package no.vegvesen.nvdbapi.client.model.roadobjects.attribute;

public class StringAttribute extends Attribute {

    private final String value;

    public StringAttribute(int id, String value) {
        super(id);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public AttributeType getAttributeType() {
        return AttributeType.STRING;
    }
}
