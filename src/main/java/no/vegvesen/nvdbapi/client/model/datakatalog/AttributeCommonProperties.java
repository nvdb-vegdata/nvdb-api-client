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
                                     LocalDate objectListDate) {
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
}
