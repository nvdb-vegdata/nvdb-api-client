package no.vegvesen.nvdbapi.client.model.roadobjects.attribute;

import no.vegvesen.nvdbapi.client.model.Direction;
import no.vegvesen.nvdbapi.client.model.SidePosition;
import no.vegvesen.nvdbapi.client.model.roadnet.NetElementType;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ReflinkExtentAttribute extends LocationalAttribute {

    private final Direction direction;
    private final SidePosition sidePosition;
    private final List<String> lanes;
    private final Double startLoc;
    private final Double endLoc;

    public ReflinkExtentAttribute(int id, long netelementId, Direction direction, SidePosition sidePosition,
                                  List<String> lanes, Double startLoc, Double endLoc) {
        super(id, netelementId, NetElementType.LENKE);
        this.direction = direction;
        this.sidePosition = sidePosition;
        this.lanes = lanes;
        this.startLoc = startLoc;
        this.endLoc = endLoc;
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

    @Override
    public String getValueAsString() {
        return String.format(Locale.ROOT, "%f-%f@%d", startLoc, endLoc, getNetelementId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ReflinkExtentAttribute that = (ReflinkExtentAttribute) o;
        return direction == that.direction &&
            sidePosition == that.sidePosition &&
            Objects.equals(lanes, that.lanes) &&
            Objects.equals(startLoc, that.startLoc) &&
            Objects.equals(endLoc, that.endLoc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), direction, sidePosition, lanes, startLoc, endLoc);
    }
}
