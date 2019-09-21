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

import java.util.Set;

public class DoubleEnumAttributeType extends AbstractEnumAttributeType<DoubleEnumValue> {
    private final Double defaultvalue;
    private final Double minValue;
    private final Double maxValue;
    private final Double absMinValue;
    private final Double absMaxValue;
    private final Integer fieldLength;
    private final Integer decimalCount;
    private final Unit unit;
    private final Boolean complementarysign;
    private final boolean isdirectionsensitive;
    private final boolean isextentsensitive;

    public DoubleEnumAttributeType(AttributeCommonProperties props,
                                   Double defaultvalue,
                                   Double minValue,
                                   Double maxValue,
                                   Double absMinValue,
                                   Double absMaxValue,
                                   Integer fieldLength,
                                   Integer decimalCount,
                                   Unit unit,
                                   Set<DoubleEnumValue> values,
                                   Boolean complementarysign, boolean isdirectionsensitive, boolean isextentsensitive) {
        super(props, values);
        this.defaultvalue = defaultvalue;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.absMinValue = absMinValue;
        this.absMaxValue = absMaxValue;
        this.fieldLength = fieldLength;
        this.decimalCount = decimalCount;
        this.unit = unit;
        this.complementarysign = complementarysign;
        this.isdirectionsensitive = isdirectionsensitive;
        this.isextentsensitive = isextentsensitive;
    }

    public Double getDefaultvalue() {
        return defaultvalue;
    }

    public Double getMinValue() {
        return minValue;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public Double getAbsMinValue() {
        return absMinValue;
    }

    public Double getAbsMaxValue() {
        return absMaxValue;
    }

    public Integer getFieldLength() {
        return fieldLength;
    }

    public Integer getDecimalCount() {
        return decimalCount;
    }

    public Unit getUnit() {
        return unit;
    }

    public Boolean getComplementarysign() {
        return complementarysign;
    }

    public boolean isIsdirectionsensitive() {
        return isdirectionsensitive;
    }

    public boolean isIsextentsensitive() {
        return isextentsensitive;
    }
}
