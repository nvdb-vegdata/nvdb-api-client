package no.vegvesen.nvdbapi.client.model;

public enum HeightLevel {
    OVER("OVER"),
    ON("PÅ"),
    UNDER("UNDER");

    private final String value;

    HeightLevel(String value) {
        this.value = value;
    }
}
