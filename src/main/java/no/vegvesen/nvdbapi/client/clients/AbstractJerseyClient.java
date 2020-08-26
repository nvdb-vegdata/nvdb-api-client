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


import javax.ws.rs.client.Client;
import javax.ws.rs.core.UriBuilder;
import java.io.Serializable;
import java.util.function.Consumer;

abstract class AbstractJerseyClient implements AutoCloseable, Serializable {
    private final String baseUrl;
    private final Client client;
    private final Consumer<AbstractJerseyClient> onClose;
    private boolean isClosed;

    AbstractJerseyClient(String baseUrl,
                         Client client,
                         Consumer<AbstractJerseyClient> onClose) {
        this.baseUrl = baseUrl;
        this.client = client;
        this.onClose = onClose;
    }

    Client getClient() {
        return client;
    }

    UriBuilder start() {
        return UriBuilder.fromUri(baseUrl);
    }

    public boolean isClosed() {
        return isClosed;
    }

    @Override
    public void close() {
        if (isClosed) {
            return;
        }
        client.close();
        onClose.accept(this);
        isClosed = true;
    }
}
