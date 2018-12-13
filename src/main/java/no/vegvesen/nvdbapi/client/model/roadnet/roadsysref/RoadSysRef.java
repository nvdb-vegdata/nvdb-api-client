package no.vegvesen.nvdbapi.client.model.roadnet.roadsysref;

import java.util.Objects;

public class RoadSysRef {

    public final Section section;
    public final SideArea sideArea;
    public final Intersection intersection;
    public final RoadSystem roadSystem;

    /**
     * start meter for segment.
     * If roadsysref has intersection or sidearea, the meters are *on* those.
     */
    public final double startMeter;
    /**
     * end meter for segment.
     * If roadsysref has intersection or sidearea, the meters are *on* those.
     */
    public final double endMeter;
    /**
     * If roadsysref has intersection or sidearea this is the meter value
     * *on* the section where the intersction or sidearea starts.
     */
    public final Double sectionMeter;

    /**
     *  Shortform of the roadsysref
     */
    public final String shortForm;

    public RoadSysRef(RoadSystem roadSystem,
                      Section section,
                      Intersection interSection,
                      SideArea sideArea,
                      double startMeter,
                      double endMeter,
                      Double sectionMeter,
                      String shortForm) {
        this.roadSystem = roadSystem;
        this.section = section;
        this.intersection = interSection;
        this.sideArea = sideArea;
        this.startMeter = startMeter;
        this.endMeter = endMeter;
        this.sectionMeter = sectionMeter;
        this.shortForm = shortForm;
    }

    public Section getSection() {
        return section;
    }
    public Intersection getInterSection() {
        return intersection;
    }
    public SideArea getSideArea() {
        return sideArea;
    }
    public RoadSystem getRoadSystem() {
        return roadSystem;
    }

    @Override
    public String toString() {
        return "RoadSysRef{" +
                "section=" + section +
                ", sideArea=" + sideArea +
                ", intersection=" + intersection +
                ", roadSystem=" + roadSystem +
                ", startMeter=" + startMeter +
                ", endMeter=" + endMeter +
                ", sectionMeter=" + sectionMeter +
                ", shortForm='" + shortForm + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoadSysRef that = (RoadSysRef) o;
        return Double.compare(that.startMeter, startMeter) == 0 &&
                Double.compare(that.endMeter, endMeter) == 0 &&
                Objects.equals(section, that.section) &&
                Objects.equals(sideArea, that.sideArea) &&
                Objects.equals(intersection, that.intersection) &&
                Objects.equals(roadSystem, that.roadSystem) &&
                Objects.equals(sectionMeter, that.sectionMeter) &&
                Objects.equals(shortForm, that.shortForm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(section, sideArea, intersection, roadSystem, startMeter, endMeter, sectionMeter, shortForm);
    }
}
