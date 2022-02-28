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

import java.io.*;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.pool.ConnPoolControl;
import org.glassfish.jersey.apache.connector.ApacheClientProperties;
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.message.DeflateEncoder;
import org.glassfish.jersey.message.GZipEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import no.vegvesen.nvdbapi.client.ClientConfiguration;
import no.vegvesen.nvdbapi.client.ProxyConfig;
import no.vegvesen.nvdbapi.client.gson.GsonMessageBodyHandler;
import no.vegvesen.nvdbapi.client.model.datakatalog.Datakatalog;
import no.vegvesen.nvdbapi.client.model.datakatalog.Version;
import no.vegvesen.nvdbapi.client.util.LoggingFilter;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.util.Objects.isNull;

public final class ClientFactory implements AutoCloseable {
    private final String baseUrl;
    private final String userAgent;
    private final String xClientName;
    private final String xSession;

    static final String apiRevision = "application/vnd.vegvesen.nvdb-v3-rev2+json";
    private final ProxyConfig proxyConfig;
    private final ClientConfiguration clientConfig;

    private Datakatalog datakatalog;
    private Version datakatalogVersion;
    private final Map<Class<? extends AbstractJerseyClient>, AbstractJerseyClient> clients;
    private boolean isClosed;
    private final Logger debugLogger;
    private final PoolingHttpClientConnectionManager connectionManager;
    /*
     * Each of or Clients have their own {@code javax.ws.rs.client.Client}, that share a
     * {@code org.apache.http.impl.conn.PoolingHttpClientConnectionManager}.
     * If {@code close()} is called on our client, the connection manager is shutdown.
     */
    private final HttpClientConnectionManager notCloseableConnectionManager;

    private Login.AuthTokens authTokens;

    /**
     * @param baseUrl - what base url to use. For production: https://nvdbapiles-v3.atlas.vegvesen.no
     * @param xClientName - a name describing/name of your consumer application.
     */
    public ClientFactory(String baseUrl, String xClientName) {
        this(baseUrl, xClientName, (ProxyConfig) null);
    }

    /**
     * @param baseUrl - what base url to use. For production: https://nvdbapiles-v3.atlas.vegvesen.no
     * @param xClientName - a name describing/name of your consumer application.
     * @param clientConfiguration - a client configuration for setting timeouts
     */
    public ClientFactory(String baseUrl, String xClientName, ClientConfiguration clientConfiguration) {
        this(baseUrl, xClientName, null, null, clientConfiguration);
    }

    /**
     * @param baseUrl - what base url to use. For production: https://nvdbapiles-v3.atlas.vegvesen.no
     * @param xClientName - a name describing/name of your consumer application.
     * @param proxyConfig - Config if traffic have to go through proxy.
     */
    public ClientFactory(String baseUrl, String xClientName, ProxyConfig proxyConfig) {
        this(baseUrl, xClientName, null, proxyConfig);
    }

    /**
     * @param baseUrl - what base url to use. For production: https://nvdbapiles-v3.atlas.vegvesen.no
     * @param xClientName - a name describing/name of your consumer application.
     * @param proxyConfig - Config if traffic have to go through proxy.
     * @param clientConfiguration - a client configuration for setting timeouts
     */
    public ClientFactory(String baseUrl, String xClientName, ProxyConfig proxyConfig, ClientConfiguration clientConfiguration) {
        this(baseUrl, xClientName, null, proxyConfig, clientConfiguration);
    }

    /**
     * @param baseUrl - what base url to use. For production: https://nvdbapiles-v3.atlas.vegvesen.no
     * @param xClientName - a name describing/name of your consumer application.
     * @param xSession - something identifying this session. Used to tag a sequence of requests, such that
     *                 if there are several instances that have the same xClientName it is possible to tell
     *                 the requests of each instance apart.
     *                 Use a uuid or something similar. not something that can identify a user, like username or email.
     * @param proxyConfig - Config if traffic have to go through proxy.
     */
    public ClientFactory(String baseUrl, String xClientName, String xSession, ProxyConfig proxyConfig) {
        this(baseUrl, xClientName, xSession, proxyConfig, null);
    }

    /**
     * @param baseUrl - what base url to use. For production: https://nvdbapiles-v3.atlas.vegvesen.no
     * @param xClientName - a name describing/name of your consumer application.
     * @param xSession - something identifying this session. Used to tag a sequence of requests, such that
     *                 if there are several instances that have the same xClientName it is possible to tell
     *                 the requests of each instance apart.
     *                 Use a uuid or something similar. not something that can identify a user, like username or email.
     * @param proxyConfig - Config if traffic have to go through proxy.
     * @param clientConfig - a client configuration for setting timeouts
     */
    public ClientFactory(String baseUrl, String xClientName, String xSession, ProxyConfig proxyConfig, ClientConfiguration clientConfig) {
        this.baseUrl = baseUrl;
        this.xClientName = xClientName;
        this.xSession = Optional.ofNullable(xSession).orElseGet(this::getOrCreateSessionId);
        this.userAgent = getUserAgent();
        this.debugLogger = LoggerFactory.getLogger("no.vegvesen.nvdbapi.Client");
        this.clients = new HashMap<>();
        this.connectionManager = new PoolingHttpClientConnectionManager();
        this.notCloseableConnectionManager = getConnectionManager(connectionManager);
        this.proxyConfig = proxyConfig;
        this.clientConfig = clientConfig;
    }

    private String getUserAgent() {
        return "nvdb-api-client-" + getClientVersion();
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

    /**
     * Variant authentication for service accounts using username and password.
     * If successful the {@code AuthTokens} recieved is used in followinf calls.
     * @param username -
     * @param password -
     * @return {@code Login} containing either {@code AuthTokens} if successful or {@code Failure} if not
     */
    public Login serviceLogin(String username, String password) {
        try {
            AuthClient client = getAuthClient();
            Login login = client.login(username, password,"serviceaccount");
            if(login.isSuccessful()) {
                this.authTokens = login.authTokens;
            }
            return login;
        } catch (Exception e) {
            debugLogger.error("Login failed", e);
            return Login.failed(e.getMessage());
        }
    }

    /**
     * clear the ClientFactory's auth tokens.
     */
    public void logout() {
        this.authTokens = null;
    }

    private AuthClient getAuthClient() {
        return getOrCreateClient(AuthClient.class, AuthClient::new);
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
        if(isNull(this.authTokens) || isNull(authTokens.refreshToken)) {
            throw new IllegalStateException("Tried to refresh without existing refresh token");
        }

        return refresh(this.authTokens.refreshToken);
    }

    /**
     * Use an existing token to authenticate.
     * @param idToken - preexisting idtoken
     * NOTE: The client will not be able to reauthenticate without a refresh-token
     */
    public void setIdToken(String idToken) {
        this.authTokens = new Login.AuthTokens(idToken, null);
    }

    /**
     * @return a new instance of {@code RoadNetClient}, or existing if one exists
     */
    public RoadNetClient getRoadNetService() {
        return getOrCreateClient(RoadNetClient.class, RoadNetClient::new);
    }

    /**
     * @return a new instance of {@code SegmentedRoadNetClient}, or existing if one exists
     */
    public SegmentedRoadNetClient getSegmentedRoadNetService() {
        return getOrCreateClient(SegmentedRoadNetClient.class, SegmentedRoadNetClient::new);
    }

    /**
     * @return a new instance of {@code RoadNetRouteClient}, or existing if one exists
     */
    public RoadNetRouteClient getRoadNetRouteClient() {
        return getOrCreateClient(RoadNetRouteClient.class, RoadNetRouteClient::new);
    }

    private void assertIsOpen() {
        if (isClosed) {
            throw new IllegalStateException("Client factory is closed! Create new instance to continue.");
        }
    }

    /**
     * @return a new instance of {@code DatakatalogClient}, or existing if one exists
     */
    public DatakatalogClient getDatakatalogClient() {
        return getOrCreateClient(DatakatalogClient.class, DatakatalogClient::new);
    }

    public Datakatalog getDatakatalog() {
        if (datakatalog == null) {
            datakatalog = getDatakatalogClient().getDatakalog();
        }
        return datakatalog;
    }

    public Version getDatakatalogVersion() {
        if (datakatalogVersion == null) {
            datakatalogVersion = getDatakatalogClient().getVersion();
        }
        return datakatalogVersion;
    }

    /**
     * @return a new instance of {@code AreaClient}, or existing if one exists
     */
    public AreaClient getAreaClient() {
        return getOrCreateClient(AreaClient.class, AreaClient::new);
    }

    /**
     * @return a new instance of {@code RoadObjectClient}, or existing if one exists
     */
    public RoadObjectClient getRoadObjectClient() {
        return getRoadObjectClient(DatakatalogPolicy.defaultPolicy());
    }

    /**
     * @param datakatalogPolicy How to handle that the Datakatalog is updated.
     * @return a new instance of {@code RoadObjectClient}, or existing if one exists
     */
    public RoadObjectClient getRoadObjectClient(DatakatalogPolicy datakatalogPolicy) {
        String version = getDatakatalogVersion().getVersion();
        return getOrCreateClient(
            RoadObjectClient.class,
            RoadObjectClient::new,
            config -> config.register(datakatalogPolicy.getFilter(version))
        );
    }

    /**
     * @return a new instance of {@code PositionClient}, or existing if one exists
     */
    public PositionClient getPlacementClient() {
        return getOrCreateClient(PositionClient.class, PositionClient::new);
    }

    /**
     * @return a new instance of {@code RoadPlacementClient}, or existing if one exists
     */
    public RoadPlacementClient getRoadPlacementClient() {
        return getOrCreateClient(RoadPlacementClient.class, RoadPlacementClient::new);
    }

    /**
     * @return a new instance of {@code StatusClient}, or existing if one exists
     */
    public StatusClient getStatusClient() {
        return getOrCreateClient(StatusClient.class, StatusClient::new);
    }

    /**
     * @return a new instance of {@code TransactionsClient}, or existing if one exists
     */
    public TransactionsClient getTransactionsClient(){
        return getOrCreateClient(TransactionsClient.class, TransactionsClient::new);
    }

    private Client createClient() {
        return createClient(Function.identity());
    }

    private Client createClient(Function<ClientConfig, ClientConfig> clientConfigCustomizer) {
        ClientConfig config = new ClientConfig()
            .register(GZipEncoder.class)
            .register(DeflateEncoder.class)
            .connectorProvider(new ApacheConnectorProvider())
            .property(ApacheClientProperties.CONNECTION_MANAGER, notCloseableConnectionManager)
            .register(GsonMessageBodyHandler.class)
            .register(
                new RequestHeaderFilter(
                    userAgent,
                    xClientName,
                    xSession,
                    apiRevision,
                    () -> authTokens));
        if (debugLogger != null) {
            config.register(new LoggingFilter(debugLogger, true));
        }

        if (proxyConfig != null) {
            config.property(ClientProperties.PROXY_URI, proxyConfig.getUrl());
            if (proxyConfig.hasCredentials()) {
                config.property(ClientProperties.PROXY_USERNAME, proxyConfig.getUsername());
                config.property(ClientProperties.PROXY_PASSWORD, proxyConfig.getPassword());
            }
        }
        if (clientConfig != null) {
            config.property(ClientProperties.READ_TIMEOUT, clientConfig.getReadTimeout());
            config.property(ClientProperties.CONNECT_TIMEOUT, clientConfig.getConnectTimeout());
        }

        return ClientBuilder.newBuilder()
            .withConfig(
                clientConfigCustomizer.apply(config)
            )
            .build();
    }

    @Override
    public void close() throws Exception {
        for (AbstractJerseyClient client : new ArrayList<>(clients.values())) {
            if (!client.isClosed()) {
                client.close();
            }
        }
        connectionManager.close();
        isClosed = true;
    }

    private <T extends AbstractJerseyClient> T getOrCreateClient(
        Class<T> type,
        ClientConstructor<T> clientConstructor) {
        return getOrCreateClient(type, clientConstructor, Function.identity());
    }

    @SuppressWarnings("unchecked")
    private <T extends AbstractJerseyClient> T getOrCreateClient(
        Class<T> type,
        ClientConstructor<T> clientConstructor,
        Function<ClientConfig, ClientConfig> clientConfigCustomizer) {
        assertIsOpen();
        return (T) clients.computeIfAbsent(
            type,
            aClass -> clientConstructor.apply(
                baseUrl,
                createClient(clientConfigCustomizer),
                c -> clients.remove(type))
        );
    }

    @FunctionalInterface
    private interface ClientConstructor<C extends AbstractJerseyClient> {
        C apply(String url, Client client, Consumer<AbstractJerseyClient> onClose);
    }

    private String getClientVersion() {
        try {
            Enumeration<URL> resources = getClass().getClassLoader()
                .getResources("META-INF/MANIFEST.MF");
            while (resources.hasMoreElements()) {
                Manifest manifest = new Manifest(resources.nextElement().openStream());
                Attributes attributes = manifest.getMainAttributes();
                if("nvdb-api-client".equals(attributes.getValue("Implementation-Title"))) {
                    return attributes.getValue("Implementation-Version");
                }
            }
        } catch (IOException E) { /* ignore */ }
        return "unknown";
    }

    private String getOrCreateSessionId() {
        try {
            File dotFolder = getClientHome();
            File sessionIdFile = new File(dotFolder, "session");
            if(sessionIdFile.exists()) {
                return Files.readAllLines(sessionIdFile.toPath(), StandardCharsets.UTF_8).get(0);
            } else {
                String sessionId = UUID.randomUUID().toString();
                Files.write(sessionIdFile.toPath(), sessionId.getBytes(StandardCharsets.UTF_8), CREATE);
                return sessionId;
            }
        } catch (IOException e) {
            return UUID.randomUUID().toString();
        }
    }

    private static File getClientHome() {
        String userHome = System.getProperty("java.io.tmpdir");
        File dotFolder = new File(userHome, ".nvdb-api-read-v3");
        if(!dotFolder.exists()) {
            dotFolder.mkdir();
        }
        return dotFolder;
    }

    public static Optional<String> getEtag(String resource) {
        File etagFile = new File(getClientHome(), resource.replace('/', '_') + ".etag");
        if(etagFile.exists()) {
            try {
                return Optional.of(Files.readAllLines(etagFile.toPath(), StandardCharsets.UTF_8).get(0));
            } catch (IOException e) {
                LoggerFactory.getLogger(ClientFactory.class).error("Error getting etag for {}", resource, e);
            }
        }
        return Optional.empty();
    }

    public static Optional<JsonElement> getResponse(String resource) {
        File etagFile = new File(getClientHome(), resource.replace('/', '_') + ".json");
        if(etagFile.exists()) {
            try {
                return Optional.of(
                    JsonParser.parseReader(new JsonReader(new InputStreamReader(new FileInputStream(etagFile), StandardCharsets.UTF_8)))
                );
            } catch (IOException e) {
                LoggerFactory.getLogger(ClientFactory.class).error("Error getting body for {}", resource, e);
            }
        }
        return Optional.empty();
    }

    public static void setEtag(String resource, String etag, String body) {
        try {
            String r = resource.replace('/', '_');
            File etagFile = new File(getClientHome(), r + ".etag");
            Files.write(etagFile.toPath(), etag.getBytes(StandardCharsets.UTF_8), CREATE);
            File bodyFile = new File(getClientHome(), r + ".json");
            Files.write(bodyFile.toPath(), body.getBytes(StandardCharsets.UTF_8), CREATE);
        } catch (IOException e) {
            LoggerFactory.getLogger(ClientFactory.class).error("Error setting etag for {}", resource, e);
        }
    }

    private static HttpClientConnectionManager getConnectionManager(PoolingHttpClientConnectionManager connectionManager) {
        return (HttpClientConnectionManager) Proxy.newProxyInstance(
            PoolingHttpClientConnectionManager.class.getClassLoader(),
            new Class[]{HttpClientConnectionManager.class, ConnPoolControl.class, Closeable.class},
            (proxy, method, args) -> {
                if(method.getName().equals("shutdown")) {
                    return null;
                } else {
                    return method.invoke(connectionManager, args);
                }
            });
    }
}
