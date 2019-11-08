package no.vegvesen.nvdbapi.client.model.datakatalog;

import no.vegvesen.nvdbapi.client.model.LocationalType;

public class LocationalAttributeType extends AttributeType {

    private final LocationalType locationalType;
    private final Boolean overlapp;
    private final String laneRelevant;
    private final String sideposRelevant;
    private final String ajourholdi;
    private final String ajourholdsplitt;
    private final String overlappsautomatikk;
    private final boolean insideparent;
    private final Boolean directionRelevant;


    public LocationalAttributeType(AttributeCommonProperties props,
                                   LocationalType locationalType, Boolean overlapp, String laneRelevant,
                                   String sideposRelevant,
                                   String ajourholdi, String ajourholdsplitt,
                                   boolean insideparent, String overlappsautomatikk,
                                   Boolean directionRelevant) {
        super(props);
        this.locationalType = locationalType;
        this.overlapp = overlapp;
        this.laneRelevant = laneRelevant;
        this.sideposRelevant = sideposRelevant;
        this.ajourholdi = ajourholdi;
        this.ajourholdsplitt = ajourholdsplitt;
        this.insideparent = insideparent;
        this.overlappsautomatikk = overlappsautomatikk;
        this.directionRelevant = directionRelevant;
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

    public String getAjourholdi() {
        return ajourholdi;
    }

    public String getAjourholdsplitt() {
        return ajourholdsplitt;
    }

    public boolean isInsideparent() {
        return insideparent;
    }

    public String getOverlappsautomatikk() {
        return overlappsautomatikk;
    }

    public Boolean isDirectionRelevant() {
        return directionRelevant;
    }
}
