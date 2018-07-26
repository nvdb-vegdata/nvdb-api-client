package no.vegvesen.nvdbapi.client.model.roadnet;

import java.util.Objects;

/**
 * A wrapper that contains either a {@code {@link Node} or @code {@link LinkSequence }}
 */
public class NetElementWrapper {
    private final Object netelement;

    public NetElementWrapper(Object netelement) {
        this.netelement = Objects.requireNonNull(netelement);
        boolean notLinkOrNode = !(LinkSequence.class.isInstance(netelement) || Node.class.isInstance(netelement));
        if(notLinkOrNode) {
            throw new IllegalArgumentException("Not link or node");
        }
    }

    public boolean isNode() {
        return Node.class.isInstance(netelement);
    }

    public boolean isLink() {
        return LinkSequence.class.isInstance(netelement);
    }

    public LinkSequence link() {
        return (LinkSequence) netelement;
    }

    public Node node() {
        return (Node) netelement;
    }
}
