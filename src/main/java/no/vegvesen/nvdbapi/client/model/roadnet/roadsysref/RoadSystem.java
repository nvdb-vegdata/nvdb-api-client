package no.vegvesen.nvdbapi.client.model.roadnet.roadsysref;

import java.util.Objects;

public class RoadSystem {
    public final Long id;
    public final Integer version;
    public final Integer roadNumber;
    public final RoadCategory roadCategory;
    public final Phase phase;

    public RoadSystem(Long id,
                      Integer version,
                      Integer roadNumber,
                      RoadCategory roadCategory,
                      Phase phase) {
        this.id = id;
        this.version = version;
        this.roadNumber = roadNumber;
        this.roadCategory = roadCategory;
        this.phase = phase;
    }

    public String getCategoryPhaseNumberAsString(){
        return roadCategory.toString() + phase.toString() + (roadNumber == null ? "" : roadNumber);
    }

    @Override
    public String toString() {
        return "RoadSystem{" +
                "id=" + id +
                ", version=" + version +
                ", roadNumber=" + roadNumber +
                ", roadCategory='" + roadCategory + '\'' +
                ", phase='" + phase + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoadSystem that = (RoadSystem) o;
        return Objects.equals(roadNumber, that.roadNumber) &&
                Objects.equals(id, that.id) &&
                Objects.equals(version, that.version) &&
                Objects.equals(roadCategory, that.roadCategory) &&
                Objects.equals(phase, that.phase);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version, roadNumber, roadCategory, phase);
    }
}
