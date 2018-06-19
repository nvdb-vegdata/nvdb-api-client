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

public class AssociationType implements Serializable {
    private final Integer id;
    private final Integer featureTypeId;
    private final InsideParent insideParent;
    private final Affiliation affiliation;
    private final LocalDate validFrom;

    // List associations
    private Integer listId;
    private Integer maxNumber;
    private Integer minNumber;

    public AssociationType(Integer id, Integer featureTypeId, InsideParent insideParent, Affiliation affiliation,
                           LocalDate validFrom, Integer listId, Integer maxNumber, Integer minNumber) {
        this.id = id;
        this.featureTypeId = featureTypeId;
        this.insideParent = insideParent;
        this.affiliation = affiliation;
        this.validFrom = validFrom;

        // List associations
        this.listId = listId;
        this.maxNumber = maxNumber;
        this.minNumber = minNumber;
    }

    public Integer getId() {
        return id;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public Integer getFeatureTypeId() {
        return featureTypeId;
    }

    public InsideParent getInsideParent() {
        return insideParent;
    }

    public Integer getListId() { return listId; }

    public Integer getMaxNumber() { return maxNumber; }

    public Integer getMinNumber() { return minNumber; }

    public Affiliation getAffiliation() {
        return affiliation;
    }

    public enum Affiliation {
        ASSOCIATION("ASSOSIASJON"),
        AGGREGATION("AGGREGERING"),
        COMPOSITION("KOMPOSISJON"),
        TOPOLOGY("TOPOLOGI");

        private final String name;

        Affiliation(String name) {
            this.name = name;
        }

        public static Affiliation from(String text) {
            return Arrays.stream(values()).filter(v -> v.name.equalsIgnoreCase(text)).findAny().orElse(null);
        }
    }

    public enum InsideParent {
        NO,
        YES,
        /**
         * Ja, men sidepos/feltkode/høydepos kan avvike
         */
        YES_WITH_DEVIATIONS;

        public static InsideParent from(String text) {
            switch (text.toLowerCase()) {
                case "ja":
                    return AssociationType.InsideParent.YES;
                case "nei":
                    return AssociationType.InsideParent.NO;
                case "med_avvik":
                    return AssociationType.InsideParent.YES_WITH_DEVIATIONS;
                default:
                    throw new UnsupportedOperationException("Unrecognized inside parent value: "+ text);
            }
        }

    }
}
