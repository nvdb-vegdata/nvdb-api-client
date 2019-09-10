package no.vegvesen.nvdbapi.client.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import no.vegvesen.nvdbapi.client.model.RouteOnRoadNet;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RouteParser {
    public static RouteOnRoadNet parseRoute(JsonObject obj) {
        if(obj==null) return null;
        return new RouteOnRoadNet(
            RoadPlacementParser.parseRoadPlacement(obj.getAsJsonObject("start")),
            RoadPlacementParser.parseRoadPlacement(obj.getAsJsonObject("slutt")),
            StreamSupport.stream(
                obj.getAsJsonArray("elementer").spliterator(), false)
                .map(JsonElement::getAsJsonObject)
                .map(SegmentedLinkParser::parse)
                .collect(Collectors.toList())
        );
    }
}
