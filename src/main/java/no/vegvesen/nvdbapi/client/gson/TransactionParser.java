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

package no.vegvesen.nvdbapi.client.gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import no.vegvesen.nvdbapi.client.model.transaction.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static no.vegvesen.nvdbapi.client.gson.GsonUtil.*;

public class TransactionParser {

    public static Transactions parseTransactions(JsonObject obj) {
        Metadata metadata = parseMetadata(obj.getAsJsonObject("metadata"));
        List<Transaction> transactions = parseTransaction(obj.getAsJsonArray("objekter"));

        return new Transactions(transactions, metadata);
    }

    private static Metadata parseMetadata(JsonObject obj) {
        int numReturned = parseIntMember(obj, "returnert");
        NextPage nextPage = parseNextPage(obj.getAsJsonObject("neste"));

        return new Metadata(numReturned, nextPage);
    }

    private static NextPage parseNextPage(JsonObject obj) {
        String start = GsonUtil.parseStringMember(obj, "start");
        String href = GsonUtil.parseStringMember(obj, "href");

        return new NextPage(start, href);
    }

    private static List<Transaction> parseTransaction(JsonArray obj) {
        List<Transaction> transactions = new ArrayList<>();
        obj.forEach(e -> transactions.add(new Transaction(
            parseIntMember(e.getAsJsonObject(), "id"),
            parseDateTimeMember(e.getAsJsonObject(), "dato"),
            parseStringMember(e.getAsJsonObject(), "brukerid"),
            parseRoadObjects(e.getAsJsonObject().getAsJsonArray("objekter")))));

        return transactions;
    }

    private static List<RoadObject> parseRoadObjects(JsonArray obj) {
        List<RoadObject> roadObjects = new ArrayList<>();
        obj.forEach(e -> roadObjects.add(new RoadObject(
            parseIntMember(e.getAsJsonObject(), "id"),
            parseStringMember(e.getAsJsonObject(), "href"),
            parseRoadObjectMetadata(e.getAsJsonObject().getAsJsonObject("metadata")),
            Type.from(parseStringMember(e.getAsJsonObject(), "transaksjonstype_tekst")))));

        return roadObjects;
    }

    private static RoadObjectMetadata parseRoadObjectMetadata(JsonObject obj) {
        int version = parseIntMember(obj, "versjon");
        LocalDate startDate = parseDateMember(obj, "startdato");
        LocalDate endDate = parseDateMember(obj, "sluttdato");
        LocalDateTime lastModified = parseDateTimeMember(obj, "sist_modifisert");

        return new RoadObjectMetadata(parseRoadObjectType(obj.getAsJsonObject("type")), version, startDate, endDate, lastModified);
    }

    private static RoadObjectType parseRoadObjectType(JsonObject obj) {
        int id = parseIntMember(obj, "id");
        String name = parseStringMember(obj, "navn");

        return new RoadObjectType(id, name);
    }
}
