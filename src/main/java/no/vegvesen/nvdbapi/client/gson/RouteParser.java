package no.vegvesen.nvdbapi.client.gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import no.vegvesen.nvdbapi.client.model.roadnet.route.RouteField;
import no.vegvesen.nvdbapi.client.model.roadnet.route.RouteOnRoadNet;
import no.vegvesen.nvdbapi.client.model.roadnet.route.RouteStatus;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RouteParser {
    public static RouteOnRoadNet parseDetailed(JsonObject result) {
        JsonObject metadata = result.getAsJsonObject(RouteField.METADATA.getName());
        JsonArray segmenter = result.getAsJsonArray(RouteField.ROUTE_SEGMENTS.getName());
        return new RouteOnRoadNet(
            StreamSupport.stream(
                    segmenter.spliterator(), false)
                    .map(JsonElement::getAsJsonObject)
                    .map(DetailedRouteSegmentParser::parse)
                    .collect(Collectors.toList()),
                metadata.get(RouteField.LENGTH.getName()).getAsDouble(),
                RouteStatus.valueOfCode(metadata.get(RouteField.STATUS.getName()).getAsInt()));
    }

    public static RouteOnRoadNet parseBrief(JsonObject result) {
        JsonObject metadata = result.getAsJsonObject(RouteField.METADATA.getName());
        JsonArray segmenter = result.getAsJsonArray(RouteField.ROUTE_SEGMENTS.getName());
        return new RouteOnRoadNet(
                StreamSupport.stream(
                        segmenter.spliterator(), false)
                        .map(JsonElement::getAsJsonObject)
                        .map(BriefRouteSegmentParser::parse)
                        .collect(Collectors.toList()),
                metadata.get(RouteField.LENGTH.getName()).getAsDouble(),
                RouteStatus.valueOfCode(metadata.get(RouteField.STATUS.getName()).getAsInt()));
    }
}
