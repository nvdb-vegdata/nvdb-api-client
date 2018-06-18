package no.vegvesen.nvdbapi.client.model.roadnet;

import java.util.Objects;

/**
 * A wrapper that contains either a {@code {@link Node} or @code {@link Link}}
 */
public class NetElementWrapper {
    private final Object netelement;

    public NetElementWrapper(Object netelement) {
        this.netelement = Objects.requireNonNull(netelement);
        boolean notLinkOrNode = !(Link.class.isInstance(netelement) || Node.class.isInstance(netelement));
        if(notLinkOrNode) {
            throw new IllegalArgumentException("Not link or node");
        }
    }

    public boolean isNode() {
        return Node.class.isInstance(netelement);
    }

    public boolean isLink() {
        return Link.class.isInstance(netelement);
    }

    public Link link() {
        return (Link) netelement;
    }

    public Node node() {
        return (Node) netelement;
    }
}
