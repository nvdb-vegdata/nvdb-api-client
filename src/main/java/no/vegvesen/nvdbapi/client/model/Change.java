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

package no.vegvesen.nvdbapi.client.model;

import no.vegvesen.nvdbapi.client.model.roadobjects.RoadObject;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class Change {
    private final int typeId;
    private final long featureId;
    private final Optional<RoadObject> roadObject;
    private final Type type;

    public Change(int typeId, long featureId, Type type, RoadObject roadObject) {
        this.typeId = typeId;
        this.featureId = featureId;
        this.type = type;
        this.roadObject = Optional.ofNullable(roadObject);
    }

    public int getTypeId() {
        return typeId;
    }

    public long getFeatureId() {
        return featureId;
    }

    public Optional<RoadObject> getRoadObject() {
        return roadObject;
    }

    @Override
    public String toString() {
        return "Change{" +
                ", typeId=" + typeId +
                ", featureId=" + featureId +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Change change = (Change) o;
        return typeId == change.typeId &&
                featureId == change.featureId &&
                Objects.equals(roadObject, change.roadObject) &&
                type == change.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeId, featureId, roadObject, type);
    }

    public enum Type {
        /**
         * Not yet supported
         */
        NEW("NY", "ny"),
        CHANGED("ENDRET", "endret"),
        DELETED("SLETTET", "slettet"),
        /**
         * Not yet supported
         */
        ARCHIVED("SATT_HISTORISK", "historisk");

        private final String textValue;
        private final String argValue;

        Type(String textValue, String argValue) {
            this.textValue = textValue;
            this.argValue = argValue;
        }

        public String stringValue() {
            return textValue;
        }

        public String getArgValue() {
            return argValue;
        }

        public static Type from(String apiValue) {
            return Arrays.stream(values())
                    .filter(s -> s.stringValue().equalsIgnoreCase(apiValue))
                    .findAny()
                    .orElse(null);
        }
    }
}
