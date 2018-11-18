package no.vegvesen.nvdbapi.client.model.roadobjects.attribute;

import no.vegvesen.nvdbapi.client.model.roadnet.NetElementType;

public class TurnExtent extends LocationalAttribute {

    private final ReflinkExtentAttribute startPoint;
    private final ReflinkExtentAttribute endPoint;

    public TurnExtent(int id, int netelementId,
                      ReflinkExtentAttribute startPoint, ReflinkExtentAttribute endPoint) {
        super(id, netelementId, NetElementType.NODE);
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public ReflinkExtentAttribute getStartPoint() {
        return startPoint;
    }

    public ReflinkExtentAttribute getEndPoint() {
        return endPoint;
    }

    @Override
    public AttributeType getAttributeType() {
        return AttributeType.TURN_EXTENT;
    }
}
