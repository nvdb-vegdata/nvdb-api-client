package no.vegvesen.nvdbapi.client;

import java.util.Objects;

public class ClientConfiguration {
    private final int readTimeout;
    private final int connectTimeout;

    private ClientConfiguration(int readTimeout, int connectTimeout) {
        this.readTimeout = readTimeout;
        this.connectTimeout = connectTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public static final class ClientConfigurationBuilder {
        private int readTimeout = 0;
        private int connectTimeout = 0;

        private ClientConfigurationBuilder() {
        }

        public static ClientConfigurationBuilder builder() {
            return new ClientConfigurationBuilder();
        }

        /**
         * @param readTimeout in millis. Set the read timeout for the jersey client.
         * @return builder
         */
        public ClientConfigurationBuilder withReadTimeout(int readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        /**
         * @param connectTimeout in millis. Set the connect timeout for the jersey client.
         * @return builder
         */
        public ClientConfigurationBuilder withConnectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        public ClientConfiguration build() {
            return new ClientConfiguration(readTimeout, connectTimeout);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientConfiguration that = (ClientConfiguration) o;
        return readTimeout == that.readTimeout &&
                connectTimeout == that.connectTimeout;
    }

    @Override
    public int hashCode() {
        return Objects.hash(readTimeout, connectTimeout);
    }
}
