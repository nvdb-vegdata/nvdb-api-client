package no.vegvesen.nvdbapi.client.model.roadobjects.attribute;

import no.vegvesen.nvdbapi.client.model.roadnet.NetElementType;

import java.util.Objects;

public abstract class LocationalAttribute extends Attribute {

    private final long netelementId;
    private final NetElementType netelementType;

    public LocationalAttribute(int id, long netelementId, NetElementType netelementType) {
        super(id);
        this.netelementId = netelementId;
        this.netelementType = netelementType;
    }

    public long getNetelementId() {
        return netelementId;
    }

    public NetElementType getNetelementType() {
        return netelementType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationalAttribute that = (LocationalAttribute) o;
        return netelementId == that.netelementId &&
            netelementType == that.netelementType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(netelementId, netelementType);
    }
}
