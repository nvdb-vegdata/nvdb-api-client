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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class Datakatalog {
    private final List<FeatureType> featureTypes;
    private final Version version;
    private final List<Unit> units;
    private final Map<String, DataType> dataTypes;

    public Datakatalog(Version version,
                       List<FeatureType> featureTypes,
                       List<Unit> units,
                       Map<String, DataType> dataTypes) {
        this.version = version;
        this.featureTypes = featureTypes;
        this.units = units;
        this.dataTypes = dataTypes;
    }

    public Version getVersion() {
        return version;
    }

    public List<DataType> getDataTypes() {
        return new ArrayList<>(this.dataTypes.values());
    }

    public Map<String, DataType> getDataTypeMap() {
        return dataTypes;
    }

    public List<Unit> getUnits() {
        return units;
    }

    public List<FeatureType> getFeatureTypes() {
        return featureTypes;
    }

    public Stream<FeatureType> featureTypes() {
        return featureTypes.stream();
    }

    public Optional<FeatureType> getType(int id) {
        return featureTypes().filter(f -> f.getId() == id).findAny();
    }
}
