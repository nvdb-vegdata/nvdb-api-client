/*
 * Copyright (c) 2015-2017, Statens vegvesen
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

import no.vegvesen.nvdbapi.client.ProxyConfig;
import no.vegvesen.nvdbapi.client.clients.filters.RequestHeaderFilter;
import no.vegvesen.nvdbapi.client.gson.GsonMessageBodyHandler;
import no.vegvesen.nvdbapi.client.model.datakatalog.Datakatalog;
import no.vegvesen.nvdbapi.client.util.LoggingFilter;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.glassfish.jersey.apache.connector.ApacheClientProperties;
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.message.DeflateEncoder;
import org.glassfish.jersey.message.GZipEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

public final class ClientFactory implements AutoCloseable {
    private final String baseUrl;
    private final String userAgent;
    private final String xClientName;

    private static final String apiRevision = "application/vnd.vegvesen.nvdb-v3-rev0+json";
    private final ProxyConfig proxyConfig;

    private Datakatalog datakatalog;
    private List<AbstractJerseyClient> clients;
    private boolean isClosed;
    private final Logger debugLogger;
    private PoolingHttpClientConnectionManager connectionManager;
    private Login.AuthTokens authTokens;

    public ClientFactory(String baseUrl, String userAgent, String xClientName) {
        this(baseUrl, userAgent, xClientName, null, null);
    }

    public ClientFactory(String baseUrl, String userAgent, String xClientName, ProxyConfig proxyConfig) {
        this(baseUrl, userAgent, xClientName, null, proxyConfig);
    }

    public ClientFactory(String baseUrl, String userAgent, String xClientName, String debugLogName, ProxyConfig proxyConfig) {
        this.baseUrl = baseUrl;
        this.userAgent = userAgent;
        this.xClientName = xClientName;
        this.debugLogger = Optional.ofNullable(debugLogName)
                .filter(s -> s.trim().length() > 0)
                .map(LoggerFactory::getLogger)
                .orElse(null);
        this.clients = new ArrayList<>();
        this.connectionManager = new PoolingHttpClientConnectionManager();
        this.proxyConfig = proxyConfig;
    }

    public ClientFactory(String baseUrl) {
        this(baseUrl, null, null, null, null);
    }

    public boolean isClosed() {
        return isClosed;
    }

    /**
     * Authenticate with username and password.
     * If successful the {@code AuthTokens} recieved is used in followinf calls.
     * @param username -
     * @param password -
     * @return {@code Login} containing either {@code AuthTokens} if successful or {@code Failure} if not
     */
    public Login login(String username, String password) {
        try {
            AuthClient client = getAuthClient();
            Login login = client.login(username, password);
            if(login.isSuccessful()) {
                this.authTokens = login.authTokens;
            }
            return login;
        } catch (Exception e) {
            debugLogger.error("Login failed", e);
            return Login.failed(e.getMessage());
        }
    }

    private AuthClient getAuthClient() {
        return clients.stream()
                .filter(c -> c.getClass().equals(AuthClient.class))
                .map(AuthClient.class::cast)
                .findFirst()
                .orElseGet(() -> {
                    AuthClient client = new AuthClient(baseUrl, createClient());
                    clients.add(client);
                    return client;
                });
    }

    /**
     * Use an existing refresh token to authenticate.
     * @param refreshToken from a previous session
     * @return {@code Login} containing either {@code AuthTokens} if successful or {@code Failure} if not
     */
    public Login refresh(String refreshToken) {
        try {
            AuthClient client = getAuthClient();
            Login refresh = client.refresh(refreshToken);
            if(refresh.isSuccessful()) {
                this.authTokens = refresh.authTokens;
            }
            return refresh;
        } catch (Exception e) {
            debugLogger.error("Login failed", e);
            return Login.failed(e.getMessage());
        }
    }

    /**
     * Refresh authentication using internal {@code AuthTokens}.
     * @return {@code Login} containing either {@code AuthTokens} if successful or {@code Failure} if not
     */
    public Login refresh() {
        if(isNull(this.authTokens)) {
            throw new IllegalStateException("Tried to refresh without existing AuthTokens");
        }

        return refresh(this.authTokens.refreshToken);
    }

    public RoadNetClient createRoadNetService() {
        assertIsOpen();
        RoadNetClient c = new RoadNetClient(baseUrl, createClient(getDatakatalog().getVersion().getVersion()));
        clients.add(c);
        return c;
    }
    public SegmentedRoadNetClient createSegmentedRoadNetService() {
        assertIsOpen();
        SegmentedRoadNetClient c = new SegmentedRoadNetClient(baseUrl, createClient(getDatakatalog().getVersion().getVersion()));
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
        DatakatalogClient c = new DatakatalogClient(baseUrl, createClient());
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
        Datakatalog datakatalog = getDatakatalog();
        RoadObjectClient c =
                new RoadObjectClient(
                        baseUrl,
                        createClient(datakatalog.getVersion().getVersion()),
                        datakatalog);
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
        StatusClient c = new StatusClient(baseUrl, createClient());
        clients.add(c);
        return c;
    }

    public TransactionsClient createTransactionsClient(){
        assertIsOpen();
        TransactionsClient c = new TransactionsClient(baseUrl, createClient());
        clients.add(c);
        return c;
    }

    private Client createClient() {
        return createClient(null, true);
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
        config.connectorProvider(new ApacheConnectorProvider());

        config.property(ApacheClientProperties.CONNECTION_MANAGER, connectionManager);
        config.register(GsonMessageBodyHandler.class);
        config.register(
                new RequestHeaderFilter(
                        userAgent,
                        xClientName,
                        datakatalogVersion,
                        enableCompression,
                        apiRevision,
                        authTokens));

        if (proxyConfig != null) {
            config.property(ClientProperties.PROXY_URI, proxyConfig.getUrl());
            if (proxyConfig.hasCredentials()) {
                config.property(ClientProperties.PROXY_USERNAME, proxyConfig.getUsername());
                config.property(ClientProperties.PROXY_PASSWORD, proxyConfig.getPassword());
            }
        }
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
        connectionManager.close();
        isClosed = true;
    }
}
