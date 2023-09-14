/*
 * Copyright (c) 2015-2018, Statens vegvesen
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

import no.vegvesen.nvdbapi.client.gson.TransactionParser;
import no.vegvesen.nvdbapi.client.model.Page;
import no.vegvesen.nvdbapi.client.model.transaction.Transaction;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.UriBuilder;

import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

import static java.util.Objects.nonNull;
import static no.vegvesen.nvdbapi.client.clients.RoadNetClient.join;
import static no.vegvesen.nvdbapi.client.gson.GsonUtil.rt;

public class TransactionsClient extends AbstractJerseyClient {

    TransactionsClient(String baseUrl, Client client, Consumer<AbstractJerseyClient> onClose) {
        super(baseUrl, client, onClose);
    }

    public TransacionsResult getTransactions() {
        return getTransactions(TransactionsRequest.DEFAULT);
    }

    public TransacionsResult getTransactions(TransactionsRequest request) {
        WebTarget target = setupGetTransactions(request);
        return new TransacionsResult(target, request.getPage());
    }

    public AsyncTransacionsResult getTransactionsAsync(TransactionsRequest request) {
        WebTarget target = setupGetTransactions(request);
        return new AsyncTransacionsResult(target, request.getPage());
    }

    private WebTarget setupGetTransactions(TransactionsRequest request) {
        UriBuilder url = start().path("/transaksjoner");

        if(request.getIder().size() > 0) url.queryParam("ider", join(request.getIder()));
        if(nonNull(request.getFrom())) url.queryParam("fra", request.getFrom().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        if(nonNull(request.getTo())) url.queryParam("til", request.getTo().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        return getClient().target(url);
    }

    public static class TransacionsResult extends GenericResultSet<Transaction>{
        protected TransacionsResult(WebTarget baseTarget, Page currentPage) {
            super(baseTarget, currentPage, rt(TransactionParser::parseTransaction));
        }
    }
    public static class AsyncTransacionsResult extends AsyncResult<Transaction>{
        protected AsyncTransacionsResult(WebTarget baseTarget, Page currentPage) {
            super(baseTarget, currentPage, rt(TransactionParser::parseTransaction));
        }
    }

}
