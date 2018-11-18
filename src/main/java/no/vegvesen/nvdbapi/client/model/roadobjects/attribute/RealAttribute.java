package no.vegvesen.nvdbapi.client.model.roadobjects.attribute;

public class RealAttribute extends Attribute {

    private final Double value;

    public RealAttribute(int id, Double value) {
        super(id);
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    @Override
    public AttributeType getAttributeType() {
        return AttributeType.REAL;
    }
}
