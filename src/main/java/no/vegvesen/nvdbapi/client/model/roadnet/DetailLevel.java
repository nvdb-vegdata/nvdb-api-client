package no.vegvesen.nvdbapi.client.model.roadnet;

public enum DetailLevel {

    VEGTRASE("VT", "Vegtrase"),
    KJOREBANE("KB", "Kjørebane"),
    KJOREFELT("KF", "Kjørefelt"),
    VEGTRASE_OG_KJOREBANE("VTKB", "Vegtrase og kjørebane"),
    UKJENT("UKJENT", "Ukjent");

    private final String detailLevelShort;
    private final String detailLevelText;

    DetailLevel(String detailLevelShort, String detailLevelText) {
        this.detailLevelShort = detailLevelShort;
        this.detailLevelText = detailLevelText;
    }

    public static DetailLevel fromTextValue(String detailLevel) {
        for (DetailLevel l : values()) {
            if (l.detailLevelText.equalsIgnoreCase(detailLevel)) return l;
        }
        return UKJENT;

    }

    public String getSosi() {
        return detailLevelShort;
    }

}
