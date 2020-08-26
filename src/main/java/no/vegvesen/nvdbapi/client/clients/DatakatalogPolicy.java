package no.vegvesen.nvdbapi.client.clients;

public class DatakatalogPolicy {
    private final boolean sendDakatHeader;
    private final DatakatalogUpdateCallback callback;

    private DatakatalogPolicy(boolean sendDakatHeader, DatakatalogUpdateCallback callback) {
        this.sendDakatHeader = sendDakatHeader;
        this.callback = callback;
    }

    public static DatakatalogPolicy defaultPolicy() {
        return DatakatalogPolicy.builder().build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public DatakatalogFilter getFilter(String datakatVersion) {
        return new DatakatalogFilter(datakatVersion, sendDakatHeader, callback);
    }

    public static class Builder {
        private boolean sendDakatHeader = false;
        private DatakatalogUpdateCallback callback = () -> {};

        private Builder() {}

        /**
         * NVDB API LES has a mechanism that checks header «X-Datakatalog-Versjon», and if it differs from the
         * current version of Datakatalogen, returns HTTP 422: Nåværende datakatalogversjon i APIet er: {new version}
         * @param sendDakatHeader - true if header «X-Datakatalog-Versjon» should be sent.
         * @return this
         */
        public Builder sendDakatHeader(boolean sendDakatHeader) {
            this.sendDakatHeader = sendDakatHeader;
            return this;
        }

        /**
         * The given callback is called when the version of Datakatalogen has changed.
         * @param callback when Datakatalog version has changed.
         * @return this
         */
        public Builder onDatakatalogUpdateCallback(DatakatalogUpdateCallback callback) {
            this.callback = callback;
            return this;
        }

        public DatakatalogPolicy build() {
            return new DatakatalogPolicy(sendDakatHeader, callback);
        }
    }
}
