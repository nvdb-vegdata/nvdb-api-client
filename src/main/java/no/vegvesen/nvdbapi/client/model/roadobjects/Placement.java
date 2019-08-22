package no.vegvesen.nvdbapi.client.model.roadobjects;

public interface Placement {

    default boolean isPointOrLine(){
        return this instanceof RefLinkExtentPlacement;
    }

    default boolean isTurn(){
        return this instanceof TurnExtentPlacement;
    }
}
