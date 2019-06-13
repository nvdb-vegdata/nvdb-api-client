package no.vegvesen.nvdbapi.client.model.roadnet;

public enum RefLinkPartType {
    MAIN("Hoved"),
    CONNECTION("Konnektering"),
    DETAILED("Detaljert"),
    DETAILED_CONNECTION("Detaljert_konnekterting");


    private final String refLinkPartType;

    RefLinkPartType(String refLinkPartTYpe) {
        this.refLinkPartType = refLinkPartTYpe;
    }

    public static RefLinkPartType fromValue(String refLinkPartType) {
        for (RefLinkPartType rp : values()) {
            if (rp.refLinkPartType.equalsIgnoreCase(refLinkPartType)) return rp;
        }
        return null;
    }
    public String getRefLinkPartType() {
        return refLinkPartType;
    }
}
