package no.vegvesen.nvdbapi.client.model.roadobjects.attribute;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ListAttribute extends Attribute {
    private final List<Attribute> attributes;

    public ListAttribute(int id, List<Attribute> attributes) {
        super(id);
        this.attributes = attributes;
    }

    @Override
    public AttributeType getAttributeType() {
        return AttributeType.LIST;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    @Override
    public String getValueAsString() {
        return attributes.stream()
            .map(Attribute::getValueAsString)
            .collect(Collectors.joining());
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
