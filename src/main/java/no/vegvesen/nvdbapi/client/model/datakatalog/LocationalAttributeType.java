package no.vegvesen.nvdbapi.client.model.datakatalog;

import no.vegvesen.nvdbapi.client.model.LocationalType;

public class LocationalAttributeType extends AttributeType {

    private final LocationalType locationalType;

    public LocationalAttributeType(AttributeCommonProperties props, AttributeTypeParameters parameters, LocationalType locationalType) {
        super(props, parameters);
        this.locationalType = locationalType;
    }

    public LocationalType getLocationalType() {
        return locationalType;
    }
}
