package no.vegvesen.nvdbapi.client.model.roadnet;

public enum LinkType {
    HOVED(false, false),
    DETALJERT(false, true),
    KONNEKTERING(true, false),
    DETALJERT_KONNEKTERING(true, true);

    private final boolean isDetailed;
    private final boolean isConnection;

    LinkType(boolean isConnection, boolean isDetailed) {
        this.isConnection = isConnection;
        this.isDetailed = isDetailed;
    }

    public boolean isDetailed() {
        return isDetailed;
    }

    public boolean isConnection() {
        return isConnection;
    }
}
