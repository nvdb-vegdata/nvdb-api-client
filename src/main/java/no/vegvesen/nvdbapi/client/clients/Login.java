package no.vegvesen.nvdbapi.client.clients;

import static java.util.Objects.nonNull;

public class Login {
    public final AuthTokens authTokens;
    public final Failure failure;

    private Login(AuthTokens authTokens, Failure failure) {
        this.authTokens = authTokens;
        this.failure = failure;
    }

    static Login failed(String message) {
        return new Login(null, new Failure(message));
    }

    static Login success(AuthTokens authTokens) {
        return new Login(authTokens, null);
    }

    public boolean isSuccessful() {
        return nonNull(authTokens);
    }

    public static class AuthTokens {
        public final String idToken;
        public final String refreshToken;

        AuthTokens(String idToken, String refreshToken) {
            this.idToken = idToken;
            this.refreshToken = refreshToken;
        }
    }

    public static class Failure {
        public final String message;

        Failure(String message) {
            this.message = message;
        }
    }
}
