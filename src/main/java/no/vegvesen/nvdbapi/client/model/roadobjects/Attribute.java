/*
 * Copyright (c) 2015-2016, Statens vegvesen
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

package no.vegvesen.nvdbapi.client.model.roadobjects;

import no.vegvesen.nvdbapi.client.model.datakatalog.DataType;

import java.util.Optional;

public class Attribute {
    private final int typeId;
    private final String typeName;
    private final DataType datatype;
    private final Object value;
    private final Optional<Integer> enumId;

    public Attribute(int typeId, String typeName, DataType datatype, Object value, Optional<Integer> enumId) {
        this.typeId = typeId;
        this.typeName = typeName;
        this.datatype = datatype;
        this.value = value;
        this.enumId = enumId;
    }

    public int getTypeId() {
        return typeId;
    }

    public DataType getDatatype() {
        return datatype;
    }

    public String getTypeName() {
        return typeName;
    }

    public Object getValue() {
        return value;
    }

    public int intValue() {
        if (!(value instanceof Number)) {
            throw new IllegalStateException("Not a number!");
        }
        return ((Number) value).intValue();
    }

    public double doubleValue() {
        if (!(value instanceof Number)) {
            throw new IllegalStateException("Not a number!");
        }
        return ((Number) value).doubleValue();
    }

    public String stringValue() {
        return (String) value;
    }

    public boolean booleanValue() {
        return (boolean) value;
    }

    public Optional<Integer> getEnumId() {
        return enumId;
    }

    public boolean isEnum() {
        return enumId.isPresent();
    }
}
