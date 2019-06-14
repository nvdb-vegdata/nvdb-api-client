package no.vegvesen.nvdbapi.client.model.roadnet;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toMap;

public enum RefLinkPartType {
    MAIN("Hoved"),
    CONNECTION("Konnektering"),
    DETAILED("Detaljert"),
    DETAILED_CONNECTION("Detaljert_konnektering"),
    UNKNOWN("Ukjent");


    private final String refLinkPartType;

    RefLinkPartType(String refLinkPartTYpe) {
        this.refLinkPartType = refLinkPartTYpe;
    }

    private static final Map<String, RefLinkPartType> mapping =
            Stream.of(values()).collect(toMap(k -> k.refLinkPartType.toLowerCase(), Function.identity()));

    public static RefLinkPartType fromValue(String refLinkPartType) {
        if (isNull(refLinkPartType)) return null;
        return mapping.getOrDefault(refLinkPartType.toLowerCase(), UNKNOWN);
    }

    public String getRefLinkPartType() {
        return refLinkPartType;
    }
}
