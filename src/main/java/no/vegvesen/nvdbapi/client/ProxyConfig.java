package no.vegvesen.nvdbapi.client;

import no.vegvesen.nvdbapi.client.util.Strings;

public class ProxyConfig {
    private final String url;
    private final String username;
    private final String password;

    public ProxyConfig(String url) {
        this(url, null, null);
    }

    public ProxyConfig(String url, String username, String password) {
        if (Strings.isNullOrEmpty(url)) {
            throw new IllegalArgumentException("Missing url argument!");
        }
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public boolean hasCredentials() {
        return username != null && password != null;
    }

    public String getPassword() {
        return password;
    }
}

