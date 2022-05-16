package no.vegvesen.nvdbapi.client.model.roadnet.roadsysref;

import java.util.Objects;

import no.vegvesen.nvdbapi.client.model.Direction;
import no.vegvesen.nvdbapi.client.model.roadnet.RoadUserGroup;
import no.vegvesen.nvdbapi.client.model.roadnet.SeparatePassages;

public class Section {

    public final Long id;
    public final Integer version;
    public final Integer sectionNumber;
    public final Integer sectionPartNumber;
    public final Boolean arm;
    public final SeparatePassages separatePassages;
    public final String separatePassagesNumber;
    public final RoadUserGroup trafficType;
    public final double startMeter;
    public final Double endMeter;
    public final Direction direction;

    public Section(Long id,
                   Integer version,
                   Integer sectionNumber,
                   Integer sectionPartNumber,
                   Boolean arm,
                   SeparatePassages separatePassages,
                   String separatePassagesNumber,
                   RoadUserGroup trafficType,
                   double startMeter,
                   Double endMeter,
                   Direction direction) {
        this.id = id;
        this.version = version;
        this.sectionNumber = sectionNumber;
        this.sectionPartNumber = sectionPartNumber;
        this.arm = arm;
        this.separatePassages = separatePassages;
        this.separatePassagesNumber = separatePassagesNumber;
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
                ", separatePassages='" + separatePassages + '\'' +
                ", separatePassagesNumber='" + separatePassagesNumber + '\'' +
                ", trafficType='" + trafficType + '\'' +
                ", startMeter='" + startMeter + '\'' +
                ", endMeter='" + endMeter + '\'' +
                ", direction'" + direction + '\'' +
                '}';
    }

    public String getSectionAndPartAsString() {
        return "S" +
                sectionNumber +
                "D" +
                sectionPartNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Section section = (Section) o;
        return sectionNumber.equals(section.sectionNumber) &&
                sectionPartNumber.equals(section.sectionPartNumber) &&
                Double.compare(section.startMeter, startMeter) == 0 &&
                Objects.equals(id, section.id) &&
                Objects.equals(version, section.version) &&
                Objects.equals(arm, section.arm) &&
                Objects.equals(separatePassages, section.separatePassages) &&
                Objects.equals(trafficType, section.trafficType) &&
                Objects.equals(endMeter, section.endMeter) &&
                Objects.equals(direction, section.direction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version, sectionNumber, sectionPartNumber, arm, separatePassages, trafficType,
                startMeter, endMeter, direction);
    }
}
