package no.vegvesen.nvdbapi.client.model.roadnet;

import static java.util.Objects.isNull;

public enum RoadUserGroup {
    KJORENDE("K"),
    GAENDE("G");

    private final String textValue;

    RoadUserGroup(String text) {
        this.textValue = text;
    }

    public static RoadUserGroup fromValue(String textValue) {
        if (isNull(textValue)) return null;
        switch (textValue.toUpperCase()) {
            case "G": return GAENDE;
            case "K": return KJORENDE;
            default: return null;
        }
    }

    public String getTextValue() {
        return textValue;
    }
}
