package no.vegvesen.nvdbapi.client.model.roadobjects.attribute;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ListAttribute that = (ListAttribute) o;
        return Objects.equals(attributes, that.attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), attributes);
    }
}
