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

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class AttributeCommonProperties {
    private final Integer id;
    private final Integer category;
    private final String name;
    private final String shortname;
    private final String description;
    private final DataType type;
    private final Integer sortNumber;
    private final String requirementComment;
    private final AttributeType.Importance importance;
    private final String sosiName;
    private final String sosiNvdbName;
    private final Integer sensitiveLevel;
    private final LocalDate objectListDate;
    private final LocalDate validTo;
    private final Boolean readOnly;
    private final String caption;
    private final Integer complementaryattrtypeid;
    private final String planviewreftext;
    private final String heightreftext;
    private final String referencesosi;
    private final Boolean referencegeometry;
    private final Integer reqheightref;
    private final Double reqaccuracyplan;
    private final Double reqaccuracyheight;
    private final List<String> conditionalRequirements;
    private final Boolean derived;
    private final Boolean required;

    public AttributeCommonProperties(Integer id,
                                     Integer category,
                                     String name,
                                     String shortname,
                                     String description,
                                     DataType type,
                                     Integer sortNumber,
                                     String requirementComment,
                                     AttributeType.Importance importance,
                                     String sosiName,
                                     String sosiNvdbName,
                                     Integer sensitiveLevel,
                                     LocalDate objectListDate,
                                     LocalDate validTo,
                                     boolean readOnly,
                                     String caption,
                                     Integer complementaryattrtypeid,
                                     String planviewreftext,
                                     String heightreftext,
                                     String referencesosi,
                                     boolean referencegeometry,
                                     Integer reqheightref,
                                     Double reqaccuracyplan,
                                     Double reqaccuracyheight,
                                     List<String> conditionalRequirements,
                                     Boolean derived,
                                     Boolean required) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.shortname = shortname;
        this.description = description;
        this.type = type;
        this.sortNumber = sortNumber;
        this.requirementComment = requirementComment;
        this.importance = importance;
        this.sosiName = sosiName;
        this.sosiNvdbName = sosiNvdbName;
        this.sensitiveLevel = sensitiveLevel;
        this.objectListDate = objectListDate;
        this.validTo = validTo;
        this.readOnly = readOnly;
        this.caption = caption;
        this.complementaryattrtypeid = complementaryattrtypeid;
        this.planviewreftext = planviewreftext;
        this.heightreftext = heightreftext;
        this.referencesosi = referencesosi;
        this.referencegeometry = referencegeometry;
        this.reqheightref = reqheightref;
        this.reqaccuracyplan = reqaccuracyplan;
        this.reqaccuracyheight = reqaccuracyheight;
        this.conditionalRequirements = conditionalRequirements;
        this.derived = derived;
        this.required = required;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public DataType getType() {
        return type;
    }

    public Integer getSortNumber() {
        return sortNumber;
    }

    public String getRequirementComment() {
        return requirementComment;
    }

    public AttributeType.Importance getImportance() {
        return importance;
    }

    public String getSosiName() {
        return sosiName;
    }

    public String getSosiNvdbName() {
        return sosiNvdbName;
    }

    public Integer getSensitiveLevel() {
        return sensitiveLevel;
    }

    public LocalDate getObjectListDate() {
        return objectListDate;
    }

    public Integer getCategory() {
        return category;
    }

    public String getShortname() {
        return shortname;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public String getCaption() {
        return caption;
    }

    public Integer getComplementaryattrtypeid() {
        return complementaryattrtypeid;
    }

    public String getPlanviewreftext() {
        return planviewreftext;
    }

    public String getHeightreftext() {
        return heightreftext;
    }

    public String getReferencesosi() {
        return referencesosi;
    }

    public boolean isReferencegeometry() {
        return referencegeometry;
    }

    public int getReqheightref() {
        return reqheightref;
    }

    public double getReqaccuracyplan() {
        return reqaccuracyplan;
    }

    public double getReqaccuracyheight() {
        return reqaccuracyheight;
    }

    public List<String> getConditionalRequirements() {
        return conditionalRequirements;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public Boolean getDerived() {
        return derived;
    }

    public Boolean getRequired() {
        return required;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttributeCommonProperties that = (AttributeCommonProperties) o;
        return readOnly == that.readOnly &&
            referencegeometry == that.referencegeometry &&
            reqheightref.equals(that.reqheightref) &&
            Double.compare(that.reqaccuracyplan, reqaccuracyplan) == 0 &&
            Double.compare(that.reqaccuracyheight, reqaccuracyheight) == 0 &&
            Objects.equals(id, that.id) &&
            Objects.equals(category, that.category) &&
            Objects.equals(name, that.name) &&
            Objects.equals(shortname, that.shortname) &&
            Objects.equals(description, that.description) &&
            Objects.equals(type, that.type) &&
            Objects.equals(sortNumber, that.sortNumber) &&
            Objects.equals(requirementComment, that.requirementComment) &&
            importance == that.importance &&
            Objects.equals(sosiName, that.sosiName) &&
            Objects.equals(sosiNvdbName, that.sosiNvdbName) &&
            Objects.equals(sensitiveLevel, that.sensitiveLevel) &&
            Objects.equals(objectListDate, that.objectListDate) &&
            Objects.equals(validTo, that.validTo) &&
            Objects.equals(caption, that.caption) &&
            Objects.equals(complementaryattrtypeid, that.complementaryattrtypeid) &&
            Objects.equals(planviewreftext, that.planviewreftext) &&
            Objects.equals(heightreftext, that.heightreftext) &&
            Objects.equals(referencesosi, that.referencesosi) &&
            Objects.equals(conditionalRequirements, that.conditionalRequirements) &&
            Objects.equals(derived, that.derived) &&
            Objects.equals(required, that.required);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category, name, shortname, description, type, sortNumber, requirementComment,
            importance, sosiName, sosiNvdbName, sensitiveLevel, objectListDate, validTo, readOnly, caption,
            complementaryattrtypeid, planviewreftext, heightreftext, referencesosi, referencegeometry, reqheightref,
            reqaccuracyplan, reqaccuracyheight, conditionalRequirements, derived, required);
    }

    @Override
    public String toString() {
        return "AttributeCommonProperties{" +
            "id=" + id +
            ", category=" + category +
            ", name='" + name + '\'' +
            ", shortname='" + shortname + '\'' +
            ", description='" + description + '\'' +
            ", type=" + type +
            ", sortNumber=" + sortNumber +
            ", requirementComment='" + requirementComment + '\'' +
            ", importance=" + importance +
            ", sosiName='" + sosiName + '\'' +
            ", sosiNvdbName='" + sosiNvdbName + '\'' +
            ", sensitiveLevel=" + sensitiveLevel +
            ", objectListDate=" + objectListDate +
            ", validTo=" + validTo +
            ", readOnly=" + readOnly +
            ", caption='" + caption + '\'' +
            ", complementaryattrtypeid=" + complementaryattrtypeid +
            ", planviewreftext='" + planviewreftext + '\'' +
            ", heightreftext='" + heightreftext + '\'' +
            ", referencesosi='" + referencesosi + '\'' +
            ", referencegeometry=" + referencegeometry +
            ", reqheightref=" + reqheightref +
            ", reqaccuracyplan=" + reqaccuracyplan +
            ", reqaccuracyheight=" + reqaccuracyheight +
            ", conditionalRequirements=" + conditionalRequirements +
            ", derived=" + derived +
            ", required=" + required +
            '}';
    }
}
