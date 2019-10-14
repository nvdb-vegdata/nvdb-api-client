package no.vegvesen.nvdbapi.client.model.roadobjects.attribute;

import no.vegvesen.nvdbapi.client.model.roadnet.NetElementType;
import no.vegvesen.nvdbapi.client.model.roadobjects.RefLinkExtentPlacement;

import java.util.Objects;

public class TurnExtent extends LocationalAttribute {

    private final RefLinkExtentPlacement startPoint;
    private final RefLinkExtentPlacement endPoint;

    public TurnExtent(int id, long netelementId,
                      RefLinkExtentPlacement startPoint, RefLinkExtentPlacement endPoint) {
        super(id, netelementId, NetElementType.NODE);
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public RefLinkExtentPlacement getStartPoint() {
        return startPoint;
    }

    public RefLinkExtentPlacement getEndPoint() {
        return endPoint;
    }

    @Override
    public AttributeType getAttributeType() {
        return AttributeType.TURN_EXTENT;
    }

    @Override
    public String getValueAsString() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TurnExtent that = (TurnExtent) o;
        return Objects.equals(startPoint, that.startPoint) &&
            Objects.equals(endPoint, that.endPoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), startPoint, endPoint);
    }
}
