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

import java.time.LocalTime;

public class TimeAttributeType extends AttributeType {

    private final LocalTime defaultvalue;
    private final LocalTime minValue;
    private final LocalTime maxValue;
    private final String format;
    private final boolean isdirectionsensitive;
    private final boolean isextentsensitive;

    public TimeAttributeType(AttributeCommonProperties props,
                             LocalTime defaultvalue, LocalTime minValue, LocalTime maxValue,
                             String format, boolean isdirectionsensitive, boolean isextentsensitive) {
        super(props);
        this.defaultvalue = defaultvalue;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.format = format;
        this.isdirectionsensitive = isdirectionsensitive;
        this.isextentsensitive = isextentsensitive;
    }

    public LocalTime getDefaultvalue() {
        return defaultvalue;
    }

    public LocalTime getMinValue() {
        return minValue;
    }

    public LocalTime getMaxValue() {
        return maxValue;
    }

    public String getFormat() {
        return format;
    }

    public boolean isIsdirectionsensitive() {
        return isdirectionsensitive;
    }

    public boolean isIsextentsensitive() {
        return isextentsensitive;
    }
}
