package no.vegvesen.nvdbapi.client.model.roadobjects.attribute;

public abstract class Attribute {
    private final int id;

    public Attribute(int id) {
        this.id = id;
    }

    public abstract AttributeType getAttributeType();

    public int getId() {
        return id;
    }
}
