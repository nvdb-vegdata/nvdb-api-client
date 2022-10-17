package no.vegvesen.nvdbapi.client.model.datakatalog;

public class AttributeTypeWithOwner {
    private final AttributeType type;
    private final Owner owner;

    public AttributeTypeWithOwner(AttributeType type, Owner owner) {
        this.type = type;
        this.owner = owner;
    }

    public AttributeType getType() {
        return type;
    }

    public Owner getOwner() {
        return owner;
    }
}
