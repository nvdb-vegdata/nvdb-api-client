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

package no.vegvesen.nvdbapi.client.model.datakatalog;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class EnumValue<T> implements Serializable {
    private final Integer id;
    private final Integer sortNumber;
    private final T value;
    private final String shortName;
    private final String description;
    private final LocalDate objectListDate;
    private final boolean isDefault;
    private final boolean isShortValueUsable;
    private final Integer shortValueLength;
    private final Integer complimentaryId;

    public EnumValue(Integer id,
                     Integer sortNumber,
                     T value,
                     String shortName,
                     String description,
                     LocalDate objectListDate,
                     boolean isDefault,
                     boolean isShortValueUsable,
                     Integer shortValueLength,
                     Integer complimentaryId) {
        this.id = id;
        this.sortNumber = sortNumber;
        this.value = value;
        this.shortName = shortName;
        this.description = description;
        this.objectListDate = objectListDate;
        this.isDefault = isDefault;
        this.isShortValueUsable = isShortValueUsable;
        this.shortValueLength = shortValueLength;
        this.complimentaryId = complimentaryId;
    }

    public Integer getId() {
        return id;
    }

    public Integer getSortNumber() {
        return sortNumber;
    }

    public T getValue() {
        return value;
    }

    public String getShortName() {
        return shortName;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getObjectListDate() {
        return objectListDate;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public boolean isShortValueUsable() {
        return isShortValueUsable;
    }

    public Integer getShortValueLength() {
        return shortValueLength;
    }

    public Integer getComplimentaryId() {
        return complimentaryId;
    }
}
