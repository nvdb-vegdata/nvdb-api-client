package no.vegvesen.nvdbapi.client.model.roadobjects;

public class TurnExtentPlacement implements Placement{

    private final long nodeId;
    private final RefLinkExtentPlacement startPosition;
    private final RefLinkExtentPlacement endPosition;

    public TurnExtentPlacement(long nodeId, RefLinkExtentPlacement startPosition, RefLinkExtentPlacement endPosition) {
        this.nodeId = nodeId;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    public long getNodeId() {
        return nodeId;
    }

    public RefLinkExtentPlacement getStartPosition() {
        return startPosition;
    }

    public RefLinkExtentPlacement getEndPosition() {
        return endPosition;
    }
}
