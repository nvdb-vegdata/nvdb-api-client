package no.vegvesen.nvdbapi.client.model.roadnet;

public enum SeparatePassages {
    MED("Med"),
    NEI("Nei"),
    MOT("Mot");

    private final String textValue;

    SeparatePassages(String text) {
        this.textValue = text;
    }

    public static SeparatePassages fromValue(String textValue) {
        switch(textValue.toUpperCase()) {
            case "MED" : return MED;
            case "NEI" : return NEI;
            case "MOT" : return MOT;
            default: return null;
        }
    }

    public String getTextValue() {
        return textValue;
    }
}
