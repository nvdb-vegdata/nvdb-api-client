package no.vegvesen.nvdbapi.client.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import no.vegvesen.nvdbapi.client.model.RouteOnRoadNet;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Objects.nonNull;

public class RouteParser {
    public static RouteOnRoadNet parseRoute(JsonObject obj) {
        if(obj==null) return null;
        JsonObject start = obj.getAsJsonObject("start");
        JsonObject slutt = obj.getAsJsonObject("slutt");
        return new RouteOnRoadNet(
            nonNull(start) ? RoadPlacementParser.parseRoadPlacement(start) : null,
            nonNull(slutt) ? RoadPlacementParser.parseRoadPlacement(slutt) : null,
            StreamSupport.stream(
                obj.getAsJsonArray("elementer").spliterator(), false)
                .map(JsonElement::getAsJsonObject)
                .map(SegmentedLinkParser::parse)
                .collect(Collectors.toList())
        );
    }
}
