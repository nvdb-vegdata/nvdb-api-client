package no.vegvesen.nvdbapi.client.model;

import java.time.LocalDate;
import java.util.Objects;

public class GeometryAttributes {

    private final LocalDate capturedDate;     //               indexed by: roadnet
    private final LocalDate verifiedDate;     //               indexed by: roadnet & roadobject
    private final LocalDate updatedDate;      //               indexed by: roadnet
    private final String processHistory;      //
    private final Integer municipality;       //               indexed by: roadnet
    private final String medium;              //               indexed by: roadnet
    private final String objectCode;         // (sosi name)
    private final Integer classCode;          // (ltema)       indexed by: roadnet
    private final Boolean referenceGeometry;  //
    private final Double length;              //               indexed by: roadnet & roadobject
    private final Integer heightRef;          //               indexed by: roadnet
    private final Quality quality;

    public GeometryAttributes(LocalDate capturedDate,
                              LocalDate verifiedDate,
                              LocalDate updatedDate,
                              String processHistory,
                              Integer municipality,
                              String medium,
                              String objectCode,
                              Integer classCode,
                              Boolean referenceGeometry,
                              Double length,
                              Integer heightRef,
                              Quality quality) {
        this.capturedDate = capturedDate;
        this.verifiedDate = verifiedDate;
        this.updatedDate = updatedDate;
        this.processHistory = processHistory;
        this.municipality = municipality;
        this.medium = medium;
        this.objectCode = objectCode;
        this.classCode = classCode;
        this.referenceGeometry = referenceGeometry;
        this.length = length;
        this.heightRef = heightRef;
        this.quality = quality;
    }

    public LocalDate getCapturedDate() {
        return capturedDate;
    }

    public LocalDate getVerifiedDate() {
        return verifiedDate;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public String getProcessHistory() {
        return processHistory;
    }

    public Integer getMunicipality() {
        return municipality;
    }

    public String getMedium() {
        return medium;
    }

    public String getObjectCode() {
        return objectCode;
    }

    public Integer getClassCode() {
        return classCode;
    }

    public Boolean getReferenceGeometry() {
        return referenceGeometry;
    }

    public Double getLength() {
        return length;
    }

    public Integer getHeightRef() {
        return heightRef;
    }

    public Quality getQuality() {
        return quality;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeometryAttributes that = (GeometryAttributes) o;
        return Objects.equals(capturedDate, that.capturedDate) &&
            Objects.equals(verifiedDate, that.verifiedDate) &&
            Objects.equals(updatedDate, that.updatedDate) &&
            Objects.equals(processHistory, that.processHistory) &&
            Objects.equals(municipality, that.municipality) &&
            Objects.equals(medium, that.medium) &&
            Objects.equals(objectCode, that.objectCode) &&
            Objects.equals(classCode, that.classCode) &&
            Objects.equals(referenceGeometry, that.referenceGeometry) &&
            Objects.equals(length, that.length) &&
            Objects.equals(heightRef, that.heightRef) &&
            Objects.equals(quality, that.quality);
    }

    @Override
    public int hashCode() {
        return Objects.hash(capturedDate, verifiedDate, updatedDate, processHistory, municipality, medium,
            objectCode, classCode, referenceGeometry, length, heightRef, quality);
    }

    @Override
    public String toString() {
        return "GeometryAttributes{" +
            "capturedDate=" + capturedDate +
            ", verifiedDate=" + verifiedDate +
            ", updatedDate=" + updatedDate +
            ", processHistory='" + processHistory + '\'' +
            ", municipality=" + municipality +
            ", medium='" + medium + '\'' +
            ", objectCode='" + objectCode + '\'' +
            ", classCode=" + classCode +
            ", referenceGeometry=" + referenceGeometry +
            ", length=" + length +
            ", heightRef=" + heightRef +
            ", quality=" + quality +
            '}';
    }
}
