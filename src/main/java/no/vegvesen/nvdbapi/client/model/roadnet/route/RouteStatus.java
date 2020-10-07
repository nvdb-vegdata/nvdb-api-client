package no.vegvesen.nvdbapi.client.model.roadnet.route;

public enum RouteStatus {
    KOMPLETT(2000),
    LENGSTE_SAMMENHENGENDE(2060),
    MED_GYLDIGHETSHULL(2061),
    IKKE_FUNNET_RUTE(4040),
    IKKE_FUNNET_STARTPUNKT(4041),
    IKKE_FUNNET_SLUTTPUNKT(4042),
    IKKE_FUNNET_SEGMENTER_I_UTSNITT(4043);

    private final int statusCode;

    RouteStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    public static RouteStatus valueOfCode(int code) {
        for (RouteStatus status: values()) {
            if (status.statusCode == code) {
                return status;
            }
        }
        return null;
    }

    public int getStatusCode() {
        return this.statusCode;
    }
}
