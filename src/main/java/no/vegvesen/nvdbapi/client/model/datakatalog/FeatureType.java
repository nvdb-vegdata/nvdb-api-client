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
import java.util.*;
import java.util.stream.Stream;

public final class FeatureType implements Serializable {

    private final Integer id;
    private final String name;
    private final String shortname;
    private final String description;
    private final String instructions;
    private final String sosiName;
    private final String sosiNvdbName;
    private final Integer sortNumber;
    private final LocalDate objectListDate;
    private final PlacementType placementType;
    private final List<AttributeType> attributeTypes;
    private final List<AssociationType> parents;
    private final List<AssociationType> children;
    private final AttributeType locationalAttribute;

    private final String status;
    private final String mainCategory;
    private final boolean abstractType;
    private final boolean derived;
    private final boolean needParent;
    private final boolean measureSet;
    private final boolean connectingLinkOk;
    private final String additionalInformasion;
    private final boolean sensitive;
    private final boolean onlyOneVersion;

    public FeatureType(Integer id,
                       String name,
                       String shortname,
                       String description,
                       List<AttributeType> attributeTypes,
                       List<AssociationType> parents,
                       List<AssociationType> children,
                       String instructions, String sosiName,
                       String sosiNvdbName,
                       Integer sortNumber,
                       LocalDate objectListDate,
                       PlacementType placementType,
                       AttributeType locationalAttribute,
                       String status,
                       String mainCategory,
                       boolean onlyOneVersion,
                       boolean abstractType,
                       boolean derived,
                       boolean needParent,
                       boolean measureSet,
                       boolean connectingLinkOk,
                       String additionalInformasion,
                       boolean sensitive) {
        this.id = id;
        this.name = name;
        this.shortname = shortname;
        this.description = description;
        this.instructions = instructions;
        this.sosiName = sosiName;
        this.sosiNvdbName = sosiNvdbName;
        this.sortNumber = sortNumber;
        this.objectListDate = objectListDate;
        this.placementType = placementType;
        this.children = Optional.ofNullable(children).orElse(Collections.emptyList());
        this.parents = Optional.ofNullable(parents).orElse(Collections.emptyList());
        this.attributeTypes = Optional.ofNullable(attributeTypes).orElse(Collections.emptyList());
        this.locationalAttribute = locationalAttribute;
        this.status = status;
        this.mainCategory = mainCategory;
        this.onlyOneVersion = onlyOneVersion;
        this.abstractType = abstractType;
        this.derived = derived;
        this.needParent = needParent;
        this.measureSet = measureSet;
        this.connectingLinkOk = connectingLinkOk;
        this.additionalInformasion = additionalInformasion;
        this.sensitive = sensitive;
    }

    public Integer getId() {
        return id;
    }

    public PlacementType getPlacementType() {
        return placementType;
    }

    public String getSosiNvdbName() {
        return sosiNvdbName;
    }

    public Integer getSortNumber() {
        return sortNumber;
    }

    public String getName() {
        return name;
    }

    public LocalDate getObjectListDate() {
        return objectListDate;
    }

    public String getDescription() {
        return description;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getSosiName() {
        return sosiName;
    }

    public String getShortname() {
        return shortname;
    }

    public boolean isDerived() {
        return derived;
    }

    public boolean isNeedParent() {
        return needParent;
    }

    public boolean isMeasureSet() {
        return measureSet;
    }

    public boolean isConnectingLinkOk() {
        return connectingLinkOk;
    }

    public AttributeType getLocationalAttribute() {
        return locationalAttribute;
    }

    public AssociationType getAssociationType(Integer id) {
        return associationTypes().filter(at -> at.getId().equals(id)).findAny().orElse(null);
    }

    public List<AssociationType> getParents() {
        return parents;
    }

    public List<AssociationType> getChildren() {
        return children;
    }

    public Stream<AssociationType> associationTypes() {
        return Stream.concat(children.stream(), parents.stream());
    }

    public boolean isSensitive() {
        return sensitive;
    }

    /**
     * Convenience method for retriving a specific attribute type
     *
     * @param id attribute type id
     * @return the attribute type requested or null if not present
     */
    public AttributeType getAttributeType(Integer id) {
        return attributeTypes().filter(at -> at.getId().equals(id)).findAny().orElse(null);
    }

    /**
     * Convenience method for retriving a specific attribute type. This method casts to the specified
     *
     * @param id attribute type id
     * @param clazz type of {@code {@link AttributeType}}
     * @param <T> type of {@code {@link AttributeType}}
     * @return the attribute type requested or null if not present
     */
    public <T extends AttributeType> T getAttributeType(Integer id, Class<T> clazz) {
        return Optional.ofNullable(getAttributeType(id))
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .orElse(null);
    }

    public List<AttributeType> getAttributeTypes() {
        return attributeTypes;
    }

    public Stream<AttributeType> attributeTypes() {
        return attributeTypes.stream();
    }

    /**
     * The returned stream is sorted on the attribute <i>specified</i> order.
     *
     * @return {@code AttributeType}s sorted by sort number
     * @see AttributeType#getSortNumber()
     */
    public Stream<AttributeType> sortedAttributeTypes() {
        return attributeTypes().sorted(Comparator.comparing(AttributeType::getSortNumber));
    }

    public String getStatus() {
        return status;
    }

    public String getMainCategory() {
        return mainCategory;
    }

    public boolean isAbstractType() {
        return abstractType;
    }

    public String getAdditionalInformasion() {
        return additionalInformasion;
    }

    public boolean isOnlyOneVersion() {
        return onlyOneVersion;
    }

    public enum PlacementType {
        NONE("INGEN"),
        POINT("PUNKT"),
        LINE("LINJE"),
        TURN("SVING"),
        MULTI_POINT("MULTIPUNKT");

        private final String name;

        PlacementType(String name) {
            this.name = name;
        }

        public static PlacementType from(String text) {
            return Arrays.stream(values())
                         .filter(s -> s.name.equalsIgnoreCase(text))
                         .findAny()
                         .orElse(null);
        }
    }

    public enum Relevant {
        YES("JA"),
        NO("NEI"),
        MAYBE("KAN");

        private final String name;

        Relevant(String name) {
            this.name = name;
        }

        public static Relevant from(String text) {
            return Arrays.stream(values())
                    .filter(s -> s.name.equalsIgnoreCase(text))
                    .findAny()
                    .orElse(null);
        }
    }

    public enum Survivability {
        SURVIVES("OVERLEVER"),
        DOES_NOT_SURVIVE("OVERLEVER_IKKE"),
        UNKNOWN("IKKE_TATT_STILLING");

        private final String name;

        Survivability(String name) {
            this.name = name;
        }

        public static Survivability from(String text) {
            return Arrays.stream(values())
                    .filter(s -> s.name.equalsIgnoreCase(text))
                    .findAny()
                    .orElse(null);
        }
    }

    public enum Splitability {
        UNSPLITABLE("KAN_IKKE_SPLITTES"),
        SPLITABLE("KAN_SPLITTES"),
        UNKNOWN("IKKE_TATT_STILLING");

        private final String name;

        Splitability(String name) {
            this.name = name;
        }

        public static Splitability from(String text) {
            return Arrays.stream(values())
                    .filter(s -> s.name.equalsIgnoreCase(text))
                    .findAny()
                    .orElse(null);
        }
    }
}
