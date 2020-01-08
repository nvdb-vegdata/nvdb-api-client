package no.vegvesen.nvdbapi.client.model.roadnet.roadsysref;

/**
 * See definition of road object type 915, attribute 11278, «Fase»
 */
public enum Phase {
    P("Planlagt"),
    A("Under bygging"),
    V("Eksisterende"),
    F("Fiktiv");

    public final String name;

    Phase(String name) {
        this.name = name;
    }
}
