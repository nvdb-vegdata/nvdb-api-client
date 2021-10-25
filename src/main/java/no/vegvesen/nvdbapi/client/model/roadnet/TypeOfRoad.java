package no.vegvesen.nvdbapi.client.model.roadnet;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toMap;

public enum TypeOfRoad {

    KANALISERT_VEG("kanalisertVeg", "Kanalisert veg"),
    ENKEL_BILVEG("enkelBilveg", "Enkel bilveg"),
    RAMPE("rampe", "Rampe"),
    RUNDKJORING("rundkjøring", "Rundkjøring"),
    BILFERJE("bilferje", "Bilferje"),
    PASSASJERFERJE("passasjerferje", "Passasjerferje"),
    GANG_OG_SYKKELVEG("gangOgSykkelveg", "Gang- og sykkelveg"),
    SYKKELVEG("sykkelveg", "Sykkelveg"),
    GANGVEG("gangveg", "Gangveg"),
    GAGATE("gågate", "Gågate"),
    FORTAU("fortau", "Fortau"),
    TRAPP("trapp", "Trapp"),
    GANGFELT("gangfelt", "Gangfelt"),
    GATETUN("gatetun", "Gatetun"),
    TRAKTORVEG("traktorveg", "Traktorveg"),
    STI("sti", "Sti"),
    ANNET("annet", "Annet"),
    UKJENT("ukjent", "Ukjent");

    private final String typeOfRoadText;
    private final String typeOfRoadSosi;

    TypeOfRoad(String typeOfRoadSosi, String typeOfRoadText) {
        this.typeOfRoadText = typeOfRoadText;
        this.typeOfRoadSosi = typeOfRoadSosi;
    }

    private static final Map<String,TypeOfRoad> mapping =
            Stream.of(values()).collect(toMap(k -> k.typeOfRoadText.toLowerCase(), Function.identity()));

    public static TypeOfRoad fromTextValue(String typeOfRoad) {
        if (isNull(typeOfRoad)) return UKJENT;
        return mapping.getOrDefault(typeOfRoad.toLowerCase(), UKJENT);
    }

    public String getTypeOfRoadSosi() {
        return typeOfRoadSosi;
    }
}
