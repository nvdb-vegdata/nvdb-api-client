package no.vegvesen.nvdbapi.client.model.roadobjects.attribute;

import java.util.List;

public class ListAttribute extends Attribute {
    private final List<? extends Attribute> attributes;

    public ListAttribute(int id, List<? extends Attribute> attributes) {
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
