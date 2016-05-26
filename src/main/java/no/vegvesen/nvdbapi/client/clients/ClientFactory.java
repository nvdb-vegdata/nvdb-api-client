/*
 * Copyright (c) 2015-2016, Statens vegvesen
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package no.vegvesen.nvdbapi.client.clients;

import no.vegvesen.nvdbapi.client.clients.filters.RequestHeaderFilter;
import no.vegvesen.nvdbapi.client.gson.GsonMessageBodyHandler;
import no.vegvesen.nvdbapi.client.model.datakatalog.Datakatalog;
import no.vegvesen.nvdbapi.client.util.LoggingFilter;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.glassfish.jersey.apache.connector.ApacheClientProperties;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.message.DeflateEncoder;
import org.glassfish.jersey.message.GZipEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class ClientFactory implements AutoCloseable {
    private final String baseUrl;
    private final String userAgent;
    private final String xClientName;
    private Datakatalog datakatalog;
    private List<AbstractJerseyClient> clients;
    private boolean isClosed;
    private final Logger debugLogger;

    public ClientFactory(String baseUrl, String userAgent, String xClientName) {
        this(baseUrl, userAgent, xClientName, null);
    }

    public ClientFactory(String baseUrl, String userAgent, String xClientName, String debugLogName) {
        this.baseUrl = baseUrl;
        this.userAgent = userAgent;
        this.xClientName = xClientName;
        this.debugLogger = Optional.ofNullable(debugLogName).filter(s -> s.trim().length() > 0).map(LoggerFactory::getLogger).orElse(null);
        this.clients = new ArrayList<>();
    }

    public ClientFactory(String baseUrl) {
        this(baseUrl, null, null, null);
    }

    public boolean isClosed() {
        return isClosed;
    }

    public RoadNetClient createRoadNetService() {
        assertIsOpen();
        RoadNetClient c = new RoadNetClient(baseUrl, createClient(getDatakatalog().getVersion().getVersion()));
        clients.add(c);
        return c;
    }

    private void assertIsOpen() {
        if (isClosed) {
            throw new IllegalStateException("Client factory is closed! Create new instance to continue.");
        }
    }

    public DatakatalogClient createDatakatalogClient() {
        assertIsOpen();
        DatakatalogClient c = new DatakatalogClient(baseUrl, createClient(null));
        clients.add(c);
        return c;
    }

    public Datakatalog getDatakatalog() {
        if (datakatalog == null) {
            datakatalog = createDatakatalogClient().getDatakalog();
        }
        return datakatalog;
    }

    public AreaClient createAreaClient() {
        assertIsOpen();
        AreaClient c = new AreaClient(baseUrl, createClient(getDatakatalog().getVersion().getVersion()));
        clients.add(c);
        return c;
    }

    public RoadObjectClient createRoadObjectClient() {
        assertIsOpen();
        RoadObjectClient c = new RoadObjectClient(baseUrl, createClient(getDatakatalog().getVersion().getVersion()), getDatakatalog());
        clients.add(c);
        return c;
    }

    public PositionClient createPlacementClient() {
        assertIsOpen();
        PositionClient c = new PositionClient(baseUrl, createClient(getDatakatalog().getVersion().getVersion()));
        clients.add(c);
        return c;
    }

    public RoadPlacementClient createRoadPlacementClient() {
        assertIsOpen();
        RoadPlacementClient c = new RoadPlacementClient(baseUrl, createClient(getDatakatalog().getVersion().getVersion()));
        clients.add(c);
        return c;
    }

    public StatusClient createStatusClient() {
        assertIsOpen();
        StatusClient c = new StatusClient(baseUrl, createClient(null));
        clients.add(c);
        return c;
    }

    private Client createClient(String datakatalogVersion) {
        return createClient(datakatalogVersion, true);
    }

    private Client createClient(String datakatalogVersion, boolean enableCompression) {
        ClientConfig config = new ClientConfig();
        if (enableCompression) {
            config.register(GZipEncoder.class);
            config.register(DeflateEncoder.class);
        }
        if (debugLogger != null) {
            config.register(new LoggingFilter(debugLogger, true));
        }
        config.property(ApacheClientProperties.CONNECTION_MANAGER, new PoolingHttpClientConnectionManager());
        config.register(GsonMessageBodyHandler.class);
        config.register(new RequestHeaderFilter(userAgent, xClientName, datakatalogVersion, enableCompression));

        return ClientBuilder.newBuilder().withConfig(config).build();
    }

    @Override
    public void close() throws Exception {
        if (!clients.isEmpty()) {
            for (AbstractJerseyClient client : clients) {
                if (!client.isClosed()) {
                    client.close();
                }
            }
        }
        isClosed = true;
    }
}
