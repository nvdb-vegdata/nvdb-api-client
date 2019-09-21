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

public class IntegerAttributeType extends AttributeType {
    private final Integer defaultvalue;
    private final Integer minValue;
    private final Integer maxValue;
    private final Integer absMinValue;
    private final Integer absMaxValue;
    private final Integer fieldLength;
    private final Unit unit;
    private final Boolean complementarysign;
    private final boolean isdirectionsensitive;
    private final boolean isextentsensitive;

    public IntegerAttributeType(AttributeCommonProperties props,
                                Integer defaultvalue,
                                Integer minValue,
                                Integer maxValue,
                                Integer absMinValue,
                                Integer absMaxValue,
                                Integer fieldLength,
                                Unit unit,
                                Boolean complementarysign,
                                boolean isdirectionsensitive,
                                boolean isextentsensitive) {
        super(props);
        this.defaultvalue = defaultvalue;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.absMinValue = absMinValue;
        this.absMaxValue = absMaxValue;
        this.fieldLength = fieldLength;
        this.unit = unit;
        this.complementarysign = complementarysign;
        this.isdirectionsensitive = isdirectionsensitive;
        this.isextentsensitive = isextentsensitive;
    }

    public Integer getDefaultvalue() {
        return defaultvalue;
    }

    public Integer getMinValue() {
        return minValue;
    }

    public Integer getMaxValue() {
        return maxValue;
    }

    public Integer getAbsMinValue() {
        return absMinValue;
    }

    public Integer getAbsMaxValue() {
        return absMaxValue;
    }

    public Integer getFieldLength() {
        return fieldLength;
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
