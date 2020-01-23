package no.vegvesen.nvdbapi.client.gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import no.vegvesen.nvdbapi.client.model.RouteOnRoadNet;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RouteParser {
    public static RouteOnRoadNet parseDetailed(JsonArray obj) {
        if(obj==null) return null;
        return new RouteOnRoadNet(
            StreamSupport.stream(
                    obj.spliterator(), false)
                    .map(JsonElement::getAsJsonObject)
                    .map(DetailedRouteSegmentParser::parse)
                    .collect(Collectors.toList()));
    }
    public static RouteOnRoadNet parseBrief(JsonArray obj) {
        if(obj==null) return null;
        return new RouteOnRoadNet(
                StreamSupport.stream(
                        obj.spliterator(), false)
                        .map(JsonElement::getAsJsonObject)
                        .map(BriefRouteSegmentParser::parse)
                        .collect(Collectors.toList()));
    }
}
