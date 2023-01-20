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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import no.vegvesen.nvdbapi.client.gson.TransactionInfoParser;
import no.vegvesen.nvdbapi.client.model.transaction.TransactionInfo;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Objects.nonNull;
import static no.vegvesen.nvdbapi.client.gson.GsonUtil.rt;

public class TransactionStatusClient extends AbstractJerseyClient {

    TransactionStatusClient(String baseUrl, Client client, Consumer<AbstractJerseyClient> onClose) {
        super(baseUrl, client, onClose);
    }

    public List<TransactionInfo> getTransactions(TransactionStatusRequest request) {
        WebTarget target = setupGetTransactions(request);
        JsonElement jsonElement = JerseyHelper.execute(target);
        JsonArray jsonArray = jsonElement.getAsJsonArray();

        return StreamSupport.stream(jsonArray.spliterator(), false).map(JsonElement::getAsJsonObject)
                .map(rt(TransactionInfoParser::parseTransaction))
                .collect(Collectors.toList());
    }


    private WebTarget setupGetTransactions(TransactionStatusRequest request) {
        UriBuilder url = start().path("/transaksjoner/status");

        if(nonNull(request.getFrom())) url.queryParam("fra", request.getFrom().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        if(nonNull(request.getType())) url.queryParam("type", request.getType().getTextValue());

        return getClient().target(url);
    }

}
