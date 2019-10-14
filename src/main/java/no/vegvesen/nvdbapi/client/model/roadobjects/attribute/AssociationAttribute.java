package no.vegvesen.nvdbapi.client.model.roadobjects.attribute;

public class AssociationAttribute extends Attribute {

    private final long featureId;

    public AssociationAttribute(int id, long featureId) {
        super(id);
        this.featureId = featureId;
    }

    public long getFeatureId() {
        return featureId;
    }

    @Override
    public String getValueAsString() {
        return String.valueOf(featureId);
    }

    @Override
    public AttributeType getAttributeType() {
        return AttributeType.ASSOCIATION;
    }
}
