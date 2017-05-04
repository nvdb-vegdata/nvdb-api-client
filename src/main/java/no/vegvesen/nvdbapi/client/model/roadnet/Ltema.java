package no.vegvesen.nvdbapi.client.model.roadnet;

import java.util.Arrays;

public enum Ltema {
    
    VEGSENTERLINJE(7001, "VegSenterlinje"),
    KJOREFELT(7010, "Kjørefelt"),
    KJOREBANE(7011, "Kjørebane"),
    VEGTRASE(7012, "Vegtrasé"),
    BILFERJE(7201, "Bilferjestrekning"),
    GANG_OG_SYKKELVEG(7042, "GangSykkelVegSenterlinje"),
    FORTAU(7046, "Fortau"),
    TRAPP(6304, "FrittståendeTrapp"),
    SVINGEKONNEKTERINGLENKE(7004,"Svingekonnekteringslenke"),
    SYKKELVEG_SENTERLINJE(7043, "SykkelvegSenterlinje");

    private final int code;
    private final String description;
    
    Ltema(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int code() {
        return this.code;
    }

    public String description() {
        return this.description;
    }

    public static Ltema from(int code) {
        return Arrays.stream(Ltema.values()).filter(v -> v.code() == code).findAny().orElse(null);
    }
}
