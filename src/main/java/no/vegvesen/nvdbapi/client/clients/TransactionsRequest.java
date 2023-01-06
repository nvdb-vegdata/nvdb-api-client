/*
 * Copyright (c) 2015-2018, Statens vegvesen
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 *  Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package no.vegvesen.nvdbapi.client.clients;

import no.vegvesen.nvdbapi.client.model.Page;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

public class TransactionsRequest {

    public static final TransactionsRequest DEFAULT = new TransactionsRequest.Builder().build();

    private final Page page;
    private final List<Integer> ider;
    private final LocalDateTime from;
    private final LocalDateTime to;
    private final Type type;

    private TransactionsRequest(Builder b){
        this.page = b.page;
        this.ider = b.ider;
        this.from = b.from;
        this.to = b.to;
        this.type = b.type;

    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Page getPage() {
        return page;
    }

    public List<Integer> getIder() {
        return ider;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public Type getType() {
        return type;
    }

    public static class Builder{

        private Type type = Type.INDEXED_TRANSACTIONS;
        private Page page = Page.defaults();
        private List<Integer> ider = Collections.emptyList();
        private LocalDateTime from = null;
        private LocalDateTime to = null;

        public TransactionsRequest build() {
            return new TransactionsRequest(this);
        }

        public Builder withPage(Page page){
            this.page = page;
            return this;
        }

        public Builder withIder(List<Integer> ider){
            this.ider = ider;
            return this;
        }

        public Builder withFrom(LocalDateTime from){
            this.from = from;
            return this;
        }

        public Builder withTo(LocalDateTime to){
            this.to = to;
            return this;
        }

        public Builder withType(Type type) {
            this.type = type;
            return this;
        }
    }

    public enum Type {
        NOT_INDEXED_TRANSACTIONS("ikke_indeksert"),
        LAST_INDEXED_TRANSACTION("sist_indeksert"),
        INDEXED_TRANSACTIONS("indeksert");

        private final String type;
        private static Map<String, Type> mapping = Stream.of(Type.values()).collect(Collectors.toMap(k -> k.type.toLowerCase(), Function.identity()));

        Type(String type) {
            this.type = type;
        }

        public String getTextValue() {
            return type;
        }

        public static Type fromTextValue(String type) {
            if (isNull(type)) return Type.INDEXED_TRANSACTIONS;
            return mapping.getOrDefault(type.toLowerCase(), INDEXED_TRANSACTIONS);
        }
    }


}
