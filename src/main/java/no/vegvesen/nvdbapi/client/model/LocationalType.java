package no.vegvesen.nvdbapi.client.model;

import java.util.Arrays;

public enum LocationalType {

    POINT("Point"),
    LINE("Line"),
    UNKNOWN("Unknown");

    private final String name;

    LocationalType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static LocationalType from(String type) {
        return Arrays.stream(values())
            .filter(v -> v.name.equalsIgnoreCase(type))
            .findAny()
            .orElse(UNKNOWN);
    }
}