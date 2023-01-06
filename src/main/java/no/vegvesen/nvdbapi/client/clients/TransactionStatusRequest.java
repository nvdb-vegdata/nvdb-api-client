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

import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TransactionStatusRequest {

    public static final TransactionStatusRequest DEFAULT = new TransactionStatusRequest.Builder().build();

    private final LocalDateTime from;
    private final Type type;

    private TransactionStatusRequest(Builder b){
        this.from = b.from;
        this.type = b.type;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public Type getType() {
        return type;
    }

    public static class Builder{

        private Type type = Type.LAST_INDEXED_TRANSACTION;
        private LocalDateTime from = null;

        public TransactionStatusRequest build() {
            return new TransactionStatusRequest(this);
        }

        public Builder withFrom(LocalDateTime from){
            this.from = from;
            return this;
        }

        public Builder withType(Type type) {
            this.type = type;
            return this;
        }
    }

    public enum Type {
        NOT_INDEXED_TRANSACTIONS("ikke_indeksert"),
        LAST_INDEXED_TRANSACTION("sist_indeksert");

        private final String type;
        private static Map<String, Type> mapping = Stream.of(Type.values()).collect(Collectors.toMap(k -> k.type.toLowerCase(), Function.identity()));

        Type(String type) {
            this.type = type;
        }

        public String getTextValue() {
            return type;
        }

        public static Type fromTextValue(String type) {
            return mapping.getOrDefault(type.toLowerCase(), LAST_INDEXED_TRANSACTION);
        }
    }
}
