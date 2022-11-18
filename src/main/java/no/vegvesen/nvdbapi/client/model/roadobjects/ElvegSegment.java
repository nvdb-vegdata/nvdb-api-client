package no.vegvesen.nvdbapi.client.model.roadobjects;

import no.vegvesen.nvdbapi.client.model.Direction;
import no.vegvesen.nvdbapi.client.model.Geometry;
import no.vegvesen.nvdbapi.client.model.roadnet.DetailLevel;
import no.vegvesen.nvdbapi.client.model.roadnet.RefLinkPartType;
import no.vegvesen.nvdbapi.client.model.roadnet.TypeOfRoad;
import no.vegvesen.nvdbapi.client.model.roadnet.roadsysref.RoadSysRef;

import java.time.LocalDate;
import java.util.List;

public class ElvegSegment extends Segment {

    private List<String> lanes;

    public ElvegSegment(long netElementId, double startPosition, double endPosition, Direction direction, Geometry geometry, int municipality, int county, RoadSysRef roadSysRef, Double length, LocalDate startDate, LocalDate endDate, RefLinkPartType refLinkPartType, DetailLevel detailLevel, TypeOfRoad typeOfRoad, List<String> lanes) {
        super(netElementId, startPosition, endPosition, direction, geometry, municipality, county, roadSysRef, length, startDate, endDate, refLinkPartType, detailLevel, typeOfRoad);
        this.lanes = lanes;
    }

    public static ElvegSegment fromSegment(Segment segment, List<String> lanes){
        return new ElvegSegment(
                segment.getNetElementId(),
                segment.getStartPosition(),
                segment.getEndPosition(),
                segment.getDirection(),
                segment.getGeometry(),
                segment.getMunicipality(),
                segment.getCounty(),
                segment.getRoadSysRef(),
                segment.getLength(),
                segment.getStartDate(),
                segment.getEndDate(),
                segment.getRefLinkPartType(),
                segment.getDetailLevel(),
                segment.getTypeOfRoad(),
                lanes);
    }

    public List<String> getLanes() {
        return lanes;
    }
}
