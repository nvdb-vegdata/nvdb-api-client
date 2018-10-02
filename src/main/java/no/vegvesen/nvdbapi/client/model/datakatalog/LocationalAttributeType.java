package no.vegvesen.nvdbapi.client.model.datakatalog;

import no.vegvesen.nvdbapi.client.model.LocationalType;

public class LocationalAttributeType extends AttributeType {

    private final LocationalType locationalType;
    private final Boolean overlapp;
    private final String laneRelevant;
    private final String sideposRelevant;
    private final Boolean heightRelevant;
    private final Boolean dirRelevant;
    private final Boolean movable;
    private final String ajourholdi;
    private final String ajourholdsplitt;
    private final Double supplyLength;
    private final String dekningsgrad;
    private final boolean insideparent;


    public LocationalAttributeType(AttributeCommonProperties props,
                                   LocationalType locationalType, Boolean overlapp, String laneRelevant,
                                   String sideposRelevant, Boolean heightRelevant, Boolean dirRelevant,
                                   Boolean movable, String ajourholdi, String ajourholdsplitt,
                                   Double supplyLength, String dekningsgrad, boolean insideparent) {
        super(props);
        this.locationalType = locationalType;
        this.overlapp = overlapp;
        this.laneRelevant = laneRelevant;
        this.sideposRelevant = sideposRelevant;
        this.heightRelevant = heightRelevant;
        this.dirRelevant = dirRelevant;
        this.movable = movable;
        this.ajourholdi = ajourholdi;
        this.ajourholdsplitt = ajourholdsplitt;
        this.supplyLength = supplyLength;
        this.dekningsgrad = dekningsgrad;
        this.insideparent = insideparent;
    }

    public LocationalType getLocationalType() {
        return locationalType;
    }

    public Boolean getOverlapp() {
        return overlapp;
    }

    public String getLaneRelevant() {
        return laneRelevant;
    }

    public String getSideposRelevant() {
        return sideposRelevant;
    }

    public Boolean getHeightRelevant() {
        return heightRelevant;
    }

    public Boolean getDirRelevant() {
        return dirRelevant;
    }

    public Boolean getMovable() {
        return movable;
    }

    public String getAjourholdi() {
        return ajourholdi;
    }

    public String getAjourholdsplitt() {
        return ajourholdsplitt;
    }

    public Double getSupplyLength() {
        return supplyLength;
    }

    public String getDekningsgrad() {
        return dekningsgrad;
    }

    public boolean isInsideparent() {
        return insideparent;
    }
}
