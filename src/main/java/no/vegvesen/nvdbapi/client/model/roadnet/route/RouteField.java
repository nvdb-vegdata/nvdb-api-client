package no.vegvesen.nvdbapi.client.model.roadnet.route;

public enum RouteField {
    ROUTE_SEGMENTS("vegnettsrutesegmenter"),
    START("start"),
    END("slutt"),
    GEOMETRY("geometri"),
    SRID("srid"),
    BRIEF_RESPONSE("kortform"),
    CONNECTION_LINKS("konnekteringslenker"),
    DETAILED_LINKS("detaljerte_lenker"),
    DISTANCE("maks_avstand"),
    ENVELOPE("omkrets"),
    ROAD_SYS_REFS("vegsystemreferanse"),
    TYPE_OF_ROAD("typeveg"),
    ROAD_USER_GROUP("trafikantgruppe"),
    POINT_IN_TIME("tidspunkt"),
    START_POINT_IN_TIME("tidspunkt_start"),
    END_POINT_IN_TIME("tidspunkt_slutt"),
    METADATA("metadata"),
    LENGTH("lengde"),
    STATUS("status"),
    STATUS_TEXT("status_tekst");

    private final String fieldName;

    RouteField(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getName() {
        return this.fieldName;
    }
}
