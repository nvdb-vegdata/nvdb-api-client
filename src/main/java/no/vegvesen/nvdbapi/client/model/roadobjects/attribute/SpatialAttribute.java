package no.vegvesen.nvdbapi.client.model.roadobjects.attribute;

import no.vegvesen.nvdbapi.client.model.Geometry;

import java.util.Objects;

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

    public Geometry getGeometry() {
        return geometry;
    }

    @Override
    public String getValueAsString() {
        return geometry.getWkt();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SpatialAttribute that = (SpatialAttribute) o;
        return Objects.equals(geometry, that.geometry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), geometry);
    }
}
