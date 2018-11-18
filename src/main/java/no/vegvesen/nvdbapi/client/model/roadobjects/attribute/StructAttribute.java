package no.vegvesen.nvdbapi.client.model.roadobjects.attribute;

import java.util.List;

public class StructAttribute extends Attribute {
    private final List<? extends Attribute> attributes;

    public StructAttribute(int id, List<? extends Attribute> attributes) {
        super(id);
        this.attributes = attributes;
    }

    @Override
    public AttributeType getAttributeType() {
        return AttributeType.STRUCT;
    }

    public List<? extends Attribute> getAttributes() {
        return attributes;
    }
}
