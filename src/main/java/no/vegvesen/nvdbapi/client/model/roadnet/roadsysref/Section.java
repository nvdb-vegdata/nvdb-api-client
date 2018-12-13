package no.vegvesen.nvdbapi.client.model.roadnet.roadsysref;

import java.util.Objects;

public class Section {

    public final int sectionNumber;
    public final int sectionPartNumber;
    public final String arm;
    public final String sepratePassages;
    public final String trafficType;

    public Section(int sectionNumber,
                   int sectionPartNumber,
                   String arm,
                   String sepratePassages,
                   String trafficType) {
        this.sectionNumber = sectionNumber;
        this.sectionPartNumber = sectionPartNumber;
        this.arm = arm;
        this.sepratePassages = sepratePassages;
        this.trafficType = trafficType;
    }

    public String getSectionAndPartAsString() {
        return "S" +
                sectionNumber +
                "DS" +
                sectionPartNumber;
    }

    @Override
    public String toString() {
        return "Section{" +
            "sectionNumber=" + sectionNumber +
            ", sectionPartNumber=" + sectionPartNumber +
            ", arm='" + arm + '\'' +
            ", sepratePassages='" + sepratePassages + '\'' +
            ", trafficType='" + trafficType + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Section section = (Section) o;
        return sectionNumber == section.sectionNumber &&
            sectionPartNumber == section.sectionPartNumber &&
            Objects.equals(arm, section.arm) &&
            Objects.equals(sepratePassages, section.sepratePassages) &&
            Objects.equals(trafficType, section.trafficType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectionNumber, sectionPartNumber, arm, sepratePassages, trafficType);
    }
}
