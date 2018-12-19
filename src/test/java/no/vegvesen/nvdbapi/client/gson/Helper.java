package no.vegvesen.nvdbapi.client.gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

class Helper {
     static <T> List<T> parseList(String file, Function<JsonObject, T> mapper) {
         InputStream resource = Helper.class.getResourceAsStream("/jsonresponse/" + file);
         JsonElement response = new JsonParser().parse(new InputStreamReader(resource));
         JsonArray objekter = response.getAsJsonArray();
         return StreamSupport.stream(objekter.spliterator(), false)
             .map(JsonElement::getAsJsonObject)
             .map(mapper)
             .collect(Collectors.toList());
    }

    static <T> T parseObject(String file, Function<JsonObject, T> mapper) {
        InputStream resource = Helper.class.getResourceAsStream("/jsonresponse/" + file);
        JsonElement response = new JsonParser().parse(new InputStreamReader(resource));
        return mapper.apply(response.getAsJsonObject());
    }
}
