package no.vegvesen.nvdbapi.client.model.roadnet;

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
    UKJENT("ukjent", "Ukjent");

    private final String typeOfRoadText;
    private final String typeOfRoadSosi;

    TypeOfRoad(String typeOfRoadSosi, String typeOfRoadText) {
        this.typeOfRoadText = typeOfRoadText;
        this.typeOfRoadSosi = typeOfRoadSosi;
    }

    public static TypeOfRoad fromTextValue(String typeOfRoad) {
        for (TypeOfRoad r : values()) {
            if (r.typeOfRoadText.equalsIgnoreCase(typeOfRoad)) return r;
        }
        return UKJENT;
    }

    public String getTypeOfRoadSosi() {
        return typeOfRoadSosi;
    }
}
