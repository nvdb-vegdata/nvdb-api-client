package no.vegvesen.nvdbapi.client.clients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.util.Objects;

class DatakatalogFilter implements ClientRequestFilter, ClientResponseFilter {
    private static final Logger log = LoggerFactory.getLogger("no.vegvesen.nvdbapi.Client.Datakatalog");
    private static final String DAKAT_VERSION = "X-Datakatalog-Versjon";
    private final boolean sendDatakatalogVersion;
    private final DatakatalogUpdateCallback callback;

    private String dakatVersion;

    DatakatalogFilter(String dakatVersion,
                             boolean sendDatakatalogVersion,
                             DatakatalogUpdateCallback callback) {
        this.dakatVersion = dakatVersion;
        this.sendDatakatalogVersion = sendDatakatalogVersion;
        this.callback = callback;
    }

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        if (sendDatakatalogVersion && dakatVersion != null){
            MultivaluedMap<String, Object> headers = requestContext.getHeaders();
            headers.putSingle(DAKAT_VERSION, dakatVersion);
        }
    }

    @Override
    public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {
        String dakatVersion = responseContext.getHeaderString(DAKAT_VERSION);
        if(!Objects.equals(dakatVersion, this.dakatVersion) && dakatVersion != null) {
            log.info("Datakatalog version changed from {} to {}", this.dakatVersion, dakatVersion);
            this.dakatVersion = dakatVersion;
            callback.onDatakatalogUpdate();
        }
    }
}
