package no.vegvesen.nvdbapi.client.model.roadnet.roadsysref;

import java.util.Objects;

public class RoadSystem {
    public final int roadNumber;
    public final String roadCategory;
    public final String phase;

    public RoadSystem(int roadNumber, String roadCategory, String phase) {
        this.roadNumber = roadNumber;
        this.roadCategory = roadCategory;
        this.phase = phase;
    }

    public String getCategoryPhaseNumberAsString(){
        return roadCategory + phase + roadNumber;
    }

    @Override
    public String toString() {
        return "RoadSystem{" +
            "roadNumber=" + roadNumber +
            ", roadCategory='" + roadCategory + '\'' +
            ", phase='" + phase + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoadSystem that = (RoadSystem) o;
        return roadNumber == that.roadNumber &&
            Objects.equals(roadCategory, that.roadCategory) &&
            Objects.equals(phase, that.phase);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roadNumber, roadCategory, phase);
    }
}
