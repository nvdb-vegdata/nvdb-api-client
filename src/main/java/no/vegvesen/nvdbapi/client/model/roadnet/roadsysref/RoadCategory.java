package no.vegvesen.nvdbapi.client.model.roadnet.roadsysref;

/**
 * See definition of road object type 915, attribute 11276, «Vegkategori»
 */
public enum RoadCategory {
    E( "Europaveg", "europaveg"),
    R( "Riksveg", "riksveg"),
    F( "Fylkesveg", "fylkesveg"),
    K( "Kommunal veg", "kommunalVeg"),
    P( "Privat veg", "privatVeg"),
    S("Skogsveg", "skogsVeg");

    public final String name;
    public final String codeName;

    RoadCategory(String name, String codeName) {
        this.name = name;
        this.codeName = codeName;
    }
}
