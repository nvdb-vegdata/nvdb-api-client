package no.vegvesen.nvdbapi.client.model.datakatalog;

import no.vegvesen.nvdbapi.client.model.LocationalType;

public class LocationalAttributeType extends AttributeType {

    private final LocationalType locationalType;
    private final Boolean overlapp;
    private final String laneRelevant;
    private final String sideposRelevant;
    private final Boolean heightRelevant;
    private final String ajourholdi;
    private final String ajourholdsplitt;
    private final boolean insideparent;


    public LocationalAttributeType(AttributeCommonProperties props,
                                   LocationalType locationalType, Boolean overlapp, String laneRelevant,
                                   String sideposRelevant, Boolean heightRelevant,
                                   String ajourholdi, String ajourholdsplitt,
                                   boolean insideparent) {
        super(props);
        this.locationalType = locationalType;
        this.overlapp = overlapp;
        this.laneRelevant = laneRelevant;
        this.sideposRelevant = sideposRelevant;
        this.heightRelevant = heightRelevant;
        this.ajourholdi = ajourholdi;
        this.ajourholdsplitt = ajourholdsplitt;
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

    public String getAjourholdi() {
        return ajourholdi;
    }

    public String getAjourholdsplitt() {
        return ajourholdsplitt;
    }

    public boolean isInsideparent() {
        return insideparent;
    }
}
