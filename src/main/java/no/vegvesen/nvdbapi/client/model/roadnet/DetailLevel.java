package no.vegvesen.nvdbapi.client.model.roadnet;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

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

    private static Map<String, DetailLevel> mapping =
            Stream.of(values()).collect(Collectors.toMap(k -> k.detailLevelText.toLowerCase(), Function.identity()));

    public static DetailLevel fromTextValue(String detailLevel) {
        if (isNull(detailLevel)) return UKJENT;
        return mapping.getOrDefault(detailLevel.toLowerCase(), UKJENT);
    }

    public String getSosi() {
        return detailLevelShort;
    }

    public String getDetailLevelText() {
        return detailLevelText;
    }
}
