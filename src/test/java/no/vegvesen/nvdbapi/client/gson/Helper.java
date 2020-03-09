package no.vegvesen.nvdbapi.client.gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

class Helper {

    private final static String utf8 = StandardCharsets.UTF_8.name();

     static <T> List<T> parsePlainList(String file, Function<JsonObject, T> mapper) throws IOException {
         try(InputStream resource = Helper.class.getResourceAsStream("/jsonresponse/" + file)) {
             JsonElement response = JsonParser.parseReader(new InputStreamReader(resource, utf8));
             JsonArray objekter = response.getAsJsonArray();
             return parseList(mapper, objekter);
         }
     }

    static <T> List<T> parseObjekterList(String file, Function<JsonObject, T> mapper) throws IOException {
        try(InputStream resource = Helper.class.getResourceAsStream("/jsonresponse/" + file)) {
            JsonElement response = JsonParser.parseReader(new InputStreamReader(resource, utf8));
            JsonArray objekter = response.getAsJsonObject().get("objekter").getAsJsonArray();
            return parseList(mapper, objekter);
        }
    }


    private static <T> List<T> parseList(Function<JsonObject, T> mapper, JsonArray objekter) {
        return StreamSupport.stream(objekter.spliterator(), false)
            .map(JsonElement::getAsJsonObject)
            .map(mapper)
            .collect(Collectors.toList());
    }

    static <T> T parseObject(String file, Function<JsonObject, T> mapper) throws IOException {
        InputStream resource = Helper.class.getResourceAsStream("/jsonresponse/" + file);
        JsonElement response = JsonParser.parseReader(new InputStreamReader(resource, utf8));
        return mapper.apply(response.getAsJsonObject());
    }

    static <T> List<T> parseList(String file, Function<JsonObject, T> mapper) throws IOException {
        try(InputStream resource = Helper.class.getResourceAsStream("/jsonresponse/" + file)) {
            JsonElement response = JsonParser.parseReader(new InputStreamReader(resource, utf8));
            return StreamSupport.stream(response.getAsJsonArray().spliterator(), false)
                    .map(JsonElement::getAsJsonObject)
                    .map(mapper)
                    .collect(Collectors.toList());

        }
    }
}
