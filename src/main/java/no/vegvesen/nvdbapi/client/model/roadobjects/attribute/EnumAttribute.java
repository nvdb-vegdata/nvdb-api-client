package no.vegvesen.nvdbapi.client.model.roadobjects.attribute;

public class EnumAttribute extends Attribute {
    private final Integer enumId;

    public EnumAttribute(int id, Integer enumId) {
        super(id);
        this.enumId = enumId;
    }

    public Integer getEnumId() {
        return enumId;
    }

    @Override
    public AttributeType getAttributeType() {
        return AttributeType.ENUM;
    }
}
