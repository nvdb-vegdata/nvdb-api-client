package no.vegvesen.nvdbapi.client.model.roadobjects.attribute;

import no.vegvesen.nvdbapi.client.model.Direction;
import no.vegvesen.nvdbapi.client.model.HeightLevel;
import no.vegvesen.nvdbapi.client.model.SidePosition;
import no.vegvesen.nvdbapi.client.model.roadnet.NetElementType;

import java.util.List;

public class ReflinkExtentAttribute extends LocationalAttribute {

    private final Direction direction;
    private final SidePosition sidePosition;
    private final HeightLevel heightLevel;
    private final List<String> lanes;
    private final Double startLoc;
    private final Double endLoc;

    public ReflinkExtentAttribute(int id, long netelementId, Direction direction, SidePosition sidePosition,
                                  HeightLevel heightLevel, List<String> lanes, Double startLoc, Double endLoc) {
        super(id, netelementId, NetElementType.LENKE);
        this.direction = direction;
        this.sidePosition = sidePosition;
        this.heightLevel = heightLevel;
        this.lanes = lanes;
        this.startLoc = startLoc;
        this.endLoc = endLoc;
    }

    public HeightLevel getHeightLevel() {
        return heightLevel;
    }


    public Double getStartLoc() {
        return startLoc;
    }

    public Double getEndLoc() {
        return endLoc;
    }

    public Direction getDirection() {
        return direction;
    }

    public SidePosition getSidePosition() {
        return sidePosition;
    }

    public List<String> getLanes() {
        return lanes;
    }

    @Override
    public AttributeType getAttributeType() {
        return AttributeType.REFLINK_EXTENT;
    }
}
