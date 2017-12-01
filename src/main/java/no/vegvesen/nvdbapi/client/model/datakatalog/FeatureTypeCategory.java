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
import java.util.Optional;

public class FeatureTypeCategory implements Serializable {
    private final int id;
    private final String name;
    private final String shortName;
    private final String description;
    private final int sortNumber;
    private final LocalDate startDate;

    public FeatureTypeCategory(int id, String name, String shortName, String description, int sortNumber, LocalDate startDate) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.description = description;
        this.sortNumber = sortNumber;
        this.startDate = startDate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public String getDescription() {
        return description;
    }

    public int getSortNumber() {
        return sortNumber;
    }

    public Optional<LocalDate> getStartDate() {
        return Optional.ofNullable(startDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FeatureTypeCategory that = (FeatureTypeCategory) o;

        if (id != that.id) return false;
        if (sortNumber != that.sortNumber) return false;
        if (!name.equals(that.name)) return false;
        if (!shortName.equals(that.shortName)) return false;
        if (!description.equals(that.description)) return false;
        return startDate != null ? startDate.equals(that.startDate) : that.startDate == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + shortName.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + sortNumber;
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FeatureTypeCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", description='" + description + '\'' +
                ", sortNumber=" + sortNumber +
                ", startDate=" + startDate +
                '}';
    }
}
