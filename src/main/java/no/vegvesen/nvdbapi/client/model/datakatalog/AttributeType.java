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
import java.util.Arrays;

public abstract class AttributeType implements Serializable {

    private final AttributeCommonProperties props;

    protected AttributeType(AttributeCommonProperties props) {
        this.props = props;
    }

    public Integer getId() {
        return props.getId();
    }

    public Integer getCategoryId() {
        return props.getCategory();
    }

    public String getName() {
        return props.getName();
    }

    public String getShortname() {
        return props.getShortname();
    }

    public String getDescription() {
        return props.getDescription();
    }

    public Integer getSensitiveLevel() {
        return props.getSensitiveLevel();
    }

    public boolean isSensitive() {
        return props.getSensitiveLevel() > 0;
    }

    public DataType getType() {
        return props.getType();
    }

    public LocalDate getObjectListDate() {
        return props.getObjectListDate();
    }

    public Integer getSortNumber() {
        return props.getSortNumber();
    }

    public String getRequirementComment() {
        return props.getRequirementComment();
    }

    public Importance getImportance() {
        return props.getImportance();
    }

    public String getSosiName() {
        return props.getSosiName();
    }

    public String getSosiNvdbName() {
        return props.getSosiNvdbName();
    }

    public AttributeTypeWithOwner withOwner(Owner owner) {
        return new AttributeTypeWithOwner(this, owner);
    }

    public enum Importance {
        NOT_SET(0, "IKKE_SATT"),
        MANDATORY(1, "PÅKREVD_ABSOLUTT"),
        RECOMMENDED(2, "PÅKREVD_IKKE_ABSOLUTT"),
        CONDITIONAL(3, "BETINGET"),
        OPTIONAL(4, "OPSJONELL"),
        MINOR(7, "MINDRE_VIKTIG"),
        HISTORIC(9, "HISTORISK");

        private final int value;
        private final String name;

        Importance(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public static Importance from(String text) {
            return Arrays.stream(values()).filter(v -> v.name.equalsIgnoreCase(text)).findAny().orElse(null);
        }

        public static Importance from(int value) {
            return Arrays.stream(values()).filter(v -> v.value == value).findAny().orElse(null);
        }
    }
}
