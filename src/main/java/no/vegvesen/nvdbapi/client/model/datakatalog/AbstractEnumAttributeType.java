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

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractEnumAttributeType extends AttributeType implements EnumAttributeType {

    private final Set<EnumValue> values;

    protected AbstractEnumAttributeType(AttributeCommonProperties props,
                                        AttributeTypeParameters parameters, Set<EnumValue> values) {
        super(props, parameters);
        this.values = Optional.ofNullable(values).orElse(Collections.emptySet());
    }

    @Override
     public boolean isEnum() {
        return !values.isEmpty();
    }

    @Override
    public Set<EnumValue> getValues() {
        return values;
    }

    @Override
    public EnumValue getValue(Integer id) {
        return values().filter(v -> v.getId().equals(id)).findAny().orElse(null);
    }

    @Override
    public List<EnumValue> getValueList() {
        return new ArrayList<>(values);
    }

    @Override
    public Stream<EnumValue> values() {
        return values.stream();
    }

    @Override
    public List<EnumValue> getSortedValuesList() {
        return values.stream().sorted(Comparator.comparing(EnumValue::getSortNumber)).collect(Collectors.toList());
    }

    @Override
    public int size() {
        return values.size();
    }
}
