package no.vegvesen.nvdbapi.client.clients;

public class StreetRequest {
    private final int pageSize;
    private final boolean includeObjectLink;

    public StreetRequest(int pageSize, boolean includeObjectLink) {
        this.pageSize = pageSize;
        this.includeObjectLink = includeObjectLink;
    }

    public int getPageSize() {
        return pageSize;
    }

    public boolean includeObjectLink() {
        return includeObjectLink;
    }
}
