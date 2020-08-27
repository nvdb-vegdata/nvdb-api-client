package no.vegvesen.nvdbapi.client.gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import no.vegvesen.nvdbapi.client.model.roadnet.route.RouteOnRoadNet;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RouteParser {
    public static RouteOnRoadNet parseDetailed(JsonObject result) {
        JsonArray segmenter = result.getAsJsonArray("vegnettsrutesegmenter");
        return new RouteOnRoadNet(
            StreamSupport.stream(
                    segmenter.spliterator(), false)
                    .map(JsonElement::getAsJsonObject)
                    .map(DetailedRouteSegmentParser::parse)
                    .collect(Collectors.toList()));
    }

    public static RouteOnRoadNet parseBrief(JsonObject result) {
        JsonArray segmenter = result.getAsJsonArray("vegnettsrutesegmenter");
        return new RouteOnRoadNet(
                StreamSupport.stream(
                        segmenter.spliterator(), false)
                        .map(JsonElement::getAsJsonObject)
                        .map(BriefRouteSegmentParser::parse)
                        .collect(Collectors.toList()));
    }
}
