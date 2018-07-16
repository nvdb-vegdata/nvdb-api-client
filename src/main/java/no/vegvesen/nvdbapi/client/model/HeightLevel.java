package no.vegvesen.nvdbapi.client.model;

import java.util.Arrays;

public enum  HeightLevel {
    OVER("Over"),
    ON("På"),
    UNDER("Under");

    private final String text;

    HeightLevel(String text) {
        this.text = text;
    }

    public static HeightLevel from(String text) {
        return Arrays.stream(values())
            .filter(d -> d.text.equalsIgnoreCase(text))
            .findAny()
            .orElse(null);
    }
}
