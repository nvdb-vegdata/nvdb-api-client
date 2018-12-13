package no.vegvesen.nvdbapi.client.model.roadnet.roadsysref;

import java.util.Objects;

public class Section {


    public final Long id;
    public final Integer version;
    public final int sectionNumber;
    public final int sectionPartNumber;
    public final String arm;
    public final String sepratePassages;
    public final String trafficType;

    public Section(Long id, Integer version, int sectionNumber,
                   int sectionPartNumber,
                   String arm,
                   String sepratePassages,
                   String trafficType) {
        this.id = id;
        this.version = version;
        this.sectionNumber = sectionNumber;
        this.sectionPartNumber = sectionPartNumber;
        this.arm = arm;
        this.sepratePassages = sepratePassages;
        this.trafficType = trafficType;
    }

    @Override
    public String toString() {
        return "Section{" +
                "id=" + id +
                ", version=" + version +
                ", sectionNumber=" + sectionNumber +
                ", sectionPartNumber=" + sectionPartNumber +
                ", arm='" + arm + '\'' +
                ", sepratePassages='" + sepratePassages + '\'' +
                ", trafficType='" + trafficType + '\'' +
                '}';
    }

    public String getSectionAndPartAsString() {
        return "S" +
                sectionNumber +
                "DS" +
                sectionPartNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Section section = (Section) o;
        return sectionNumber == section.sectionNumber &&
                sectionPartNumber == section.sectionPartNumber &&
                Objects.equals(id, section.id) &&
                Objects.equals(version, section.version) &&
                Objects.equals(arm, section.arm) &&
                Objects.equals(sepratePassages, section.sepratePassages) &&
                Objects.equals(trafficType, section.trafficType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version, sectionNumber, sectionPartNumber, arm, sepratePassages, trafficType);
    }
}
