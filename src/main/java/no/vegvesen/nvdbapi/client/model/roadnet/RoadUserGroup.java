package no.vegvesen.nvdbapi.client.model.roadnet;

public enum RoadUserGroup {
    KJORENDE("K"),
    GAENDE("G");

    private final String textValue;

    RoadUserGroup(String text) {
        this.textValue = text;
    }

    RoadUserGroup fromValue(String textValue) {
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
