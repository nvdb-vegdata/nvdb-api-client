package no.vegvesen.nvdbapi.client;

import java.util.Objects;

public class ClientConfiguration {
    private final int readTimeout;
    private final int connectTimeout;

    /**
     * @param readTimeout in millis. Set the read timeout for the jersey client.
     * @param connectTimeout in millis. Set the connect timeout for the jersey client.
     */
    public ClientConfiguration(int readTimeout, int connectTimeout) {
        this.readTimeout = readTimeout;
        this.connectTimeout = connectTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public int getConnectTimeout() {
        return connectTimeout;
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
