package no.vegvesen.nvdbapi.client.model.roadobjects.attribute;

import no.vegvesen.nvdbapi.client.model.roadnet.NetElementType;

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
}
