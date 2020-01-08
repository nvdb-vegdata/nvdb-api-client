package no.vegvesen.nvdbapi.client.model.roadnet.roadsysref;

/**
 * See definition of road object type 915, attribute 11276, «Vegkategori»
 */
public enum RoadCategory {
    E( "Europaveg"),
    R( "Riksveg"),
    F( "Fylkesveg"),
    K( "Kommunal veg"),
    P( "Privat veg"),
    S("Skogsveg");

    public final String name;

    RoadCategory(String name) {
        this.name = name;
    }
}
