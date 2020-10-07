package no.vegvesen.nvdbapi.client.gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import no.vegvesen.nvdbapi.client.model.roadnet.route.RouteOnRoadNet;
import no.vegvesen.nvdbapi.client.model.roadnet.route.RouteStatus;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RouteParser {
    static class RouteResponseField {
        static final String ROUTE_SEGMENTS = "vegnettsrutesegmenter";
        static final String METADATA = "metadata";
        static final String LENGTH = "lengde";
        static final String STATUS = "status";
        static final String STATUS_TEXT = "status_tekst";
    }

    public static RouteOnRoadNet parseDetailed(JsonObject result) {
        JsonObject metadata = result.getAsJsonObject(RouteResponseField.METADATA);
        JsonArray segmenter = result.getAsJsonArray(RouteResponseField.ROUTE_SEGMENTS);
        return new RouteOnRoadNet(
            StreamSupport.stream(
                    segmenter.spliterator(), false)
                    .map(JsonElement::getAsJsonObject)
                    .map(DetailedRouteSegmentParser::parse)
                    .collect(Collectors.toList()),
                metadata.get(RouteResponseField.LENGTH).getAsDouble(),
                RouteStatus.valueOfCode(metadata.get(RouteResponseField.STATUS).getAsInt()));
    }

    public static RouteOnRoadNet parseBrief(JsonObject result) {
        JsonObject metadata = result.getAsJsonObject(RouteResponseField.METADATA);
        JsonArray segmenter = result.getAsJsonArray(RouteResponseField.ROUTE_SEGMENTS);
        return new RouteOnRoadNet(
                StreamSupport.stream(
                        segmenter.spliterator(), false)
                        .map(JsonElement::getAsJsonObject)
                        .map(BriefRouteSegmentParser::parse)
                        .collect(Collectors.toList()),
                metadata.get(RouteResponseField.LENGTH).getAsDouble(),
                RouteStatus.valueOfCode(metadata.get(RouteResponseField.STATUS).getAsInt()));
    }
}
