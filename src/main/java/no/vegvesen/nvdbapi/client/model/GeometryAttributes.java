package no.vegvesen.nvdbapi.client.model;

import java.time.LocalDate;

public class GeometryAttributes {

    private final LocalDate capturedDate;     //               indexed by: roadnet
    private final LocalDate verifiedDate;     //               indexed by: roadnet & roadobject
    private final LocalDate updatedDate;      //               indexed by: roadnet
    private final String processHistory;      //
    private final Integer municipality;       //               indexed by: roadnet
    private final String medium;              //               indexed by: roadnet
    private final Integer objectCode;         // (sosi name)
    private final Integer classCode;          // (ltema)       indexed by: roadnet
    private final Boolean referenceGeometry;  //
    private final Double length;              //               indexed by: roadnet & roadobject
    private final Integer heightRef;          //               indexed by: roadnet

    public GeometryAttributes(LocalDate capturedDate, LocalDate verifiedDate, LocalDate updatedDate, String processHistory, Integer municipality, String medium, Integer objectCode, Integer classCode, Boolean referenceGeometry, Double length, Integer heightRef) {
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

    public Integer getObjectCode() {
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

}
