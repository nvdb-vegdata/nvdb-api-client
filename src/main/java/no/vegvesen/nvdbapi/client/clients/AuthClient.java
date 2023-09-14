package no.vegvesen.nvdbapi.client.clients;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.UriBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static java.util.Collections.singletonMap;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;

class AuthClient extends AbstractJerseyClient {
    AuthClient(String baseUrl, Client client, Consumer<AbstractJerseyClient> onClose) {
        super(baseUrl, client, onClose);
    }

    public Login login(String username, String password) {
        return login(username, password, "employee");
    }

    public Login login(String username, String password, String userType) {
        UriBuilder path = start()
            .path("auth")
            .path("login");
        WebTarget target = getClient().target(path);
        try {
            Login.AuthTokens authTokens = target.request()
                    .post(Entity.entity(credentialsJson(username, password, userType), APPLICATION_JSON_TYPE), Login.AuthTokens.class);
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


    private Map<String, String> credentialsJson(String username, String password, String userType) {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", username);
        credentials.put("password", password);
        credentials.put("user_type", userType);
        return credentials;
    }
}
