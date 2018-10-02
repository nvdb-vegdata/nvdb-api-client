package no.vegvesen.nvdbapi.client.model.datakatalog;

import java.time.LocalDate;

public class AssociationRoleType extends AttributeType {

    private final int affiliation;
    private final int featureTypeId;
    private final int insideParent;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final int associationRequirement;
    private final String associationRequirementComment;

    public AssociationRoleType(AttributeCommonProperties props, int affiliation,
                               int featureTypeId, int insideParent, LocalDate startDate, LocalDate endDate,
                               int associationRequirement, String associationRequirementComment) {
        super(props);
        this.affiliation = affiliation;
        this.featureTypeId = featureTypeId;
        this.insideParent = insideParent;
        this.startDate = startDate;
        this.endDate = endDate;
        this.associationRequirement = associationRequirement;
        this.associationRequirementComment = associationRequirementComment;
    }

    public int getAffiliation() {
        return affiliation;
    }

    public int getFeatureTypeId() {
        return featureTypeId;
    }

    public int getInsideParent() {
        return insideParent;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getAssociationRequirement() {
        return associationRequirement;
    }

    public String getAssociationRequirementComment() {
        return associationRequirementComment;
    }
}
