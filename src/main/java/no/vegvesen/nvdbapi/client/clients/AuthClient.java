package no.vegvesen.nvdbapi.client.clients;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonMap;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;

class AuthClient extends AbstractJerseyClient {
    protected AuthClient(String baseUrl, Client client) {
        super(baseUrl, client);
    }

    public Login login(String username, String password) {
        UriBuilder path = start()
            .path("auth")
            .path("login");
        WebTarget target = getClient().target(path);
        try {
            Login.AuthTokens authTokens = target.request()
                .post(Entity.entity(credentialsJson(username, password), APPLICATION_JSON_TYPE), Login.AuthTokens.class);
            return Login.success(authTokens);
        } catch (Exception e) {
            return Login.failed(e.getMessage());
        }
    }


    public Login refresh(String refreshToken) {
        UriBuilder path = start()
            .path("auth")
            .path("refresh");
        WebTarget target = getClient().target(path);
        try {
            Login.AuthTokens authTokens = target.request()
                .post(Entity.entity(refreshJson(refreshToken), APPLICATION_JSON_TYPE), Login.AuthTokens.class);
            return Login.success(authTokens);
        } catch (Exception e) {
            return Login.failed(e.getMessage());
        }
    }

    private Map<String, String> refreshJson(String refreshToken) {
        return singletonMap("refreshToken", refreshToken);
    }


    private Map<String, String> credentialsJson(String username, String password) {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", username);
        credentials.put("password", password);
        return credentials;
    }
}
