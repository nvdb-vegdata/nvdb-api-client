package no.vegvesen.nvdbapi.client.model.roadnet.roadsysref;

import no.vegvesen.nvdbapi.client.model.Direction;
import no.vegvesen.nvdbapi.client.model.roadnet.SeparatePassages;

import java.util.Objects;

public class Section {

    public final Long id;
    public final Integer version;
    public final int sectionNumber;
    public final int sectionPartNumber;
    public final Boolean arm;
    public final SeparatePassages sepratePassages;
    public final String trafficType;
    public final double startMeter;
    public final Double endMeter;
    public final Direction direction;

    public Section(Long id,
                   Integer version,
                   int sectionNumber,
                   int sectionPartNumber,
                   Boolean arm,
                   SeparatePassages sepratePassages,
                   String trafficType,
                   double startMeter,
                   Double endMeter,
                   Direction direction) {
        this.id = id;
        this.version = version;
        this.sectionNumber = sectionNumber;
        this.sectionPartNumber = sectionPartNumber;
        this.arm = arm;
        this.sepratePassages = sepratePassages;
        this.trafficType = trafficType;
        this.startMeter = startMeter;
        this.endMeter = endMeter;
        this.direction = direction;
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
                ", startMeter='" + startMeter + '\'' +
                ", endMeter='" + endMeter + '\'' +
                ", direction'" + direction + '\'' +
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
                Double.compare(section.startMeter, startMeter) == 0 &&
                Objects.equals(id, section.id) &&
                Objects.equals(version, section.version) &&
                Objects.equals(arm, section.arm) &&
                Objects.equals(sepratePassages, section.sepratePassages) &&
                Objects.equals(trafficType, section.trafficType) &&
                Objects.equals(endMeter, section.endMeter) &&
                Objects.equals(direction, section.direction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version, sectionNumber, sectionPartNumber, arm, sepratePassages, trafficType,
                startMeter, endMeter, direction);
    }
}
