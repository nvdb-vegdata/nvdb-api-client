package no.vegvesen.nvdbapi.client.model.roadobjects.attribute;

import no.vegvesen.nvdbapi.client.model.Geometry;

public class SpatialAttribute extends Attribute {
    private final Geometry geometry;

    public SpatialAttribute(int id, Geometry geometry) {
        super(id);
        this.geometry = geometry;
    }

    @Override
    public AttributeType getAttributeType() {
        return AttributeType.SPATIAL;
    }
}
