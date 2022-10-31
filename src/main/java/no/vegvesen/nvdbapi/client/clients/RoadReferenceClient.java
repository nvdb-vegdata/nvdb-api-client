package no.vegvesen.nvdbapi.client.clients;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import no.vegvesen.nvdbapi.client.gson.PlacementParser;
import no.vegvesen.nvdbapi.client.model.Position;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static no.vegvesen.nvdbapi.client.gson.GsonUtil.rt;

public class RoadReferenceClient extends AbstractJerseyClient {
    RoadReferenceClient(String baseUrl, Client client, Consumer<AbstractJerseyClient> onClose) {
        super(baseUrl, client, onClose);
    }

    public Position getRoadSysRef(Optional<String> roadRef,
                                  Optional<String> startDato,
                                  Optional<String> sluttDato) {

        UriBuilder url = getRoadRefEndpoint();

        roadRef.ifPresent(v -> url.queryParam("vegreferanse", v));
        startDato.ifPresent(v -> url.queryParam("startDato", v));
        sluttDato.ifPresent(v -> url.queryParam("sluttDato", v));

        WebTarget target = getClient().target(url);

        JsonArray results = JerseyHelper.execute(target).getAsJsonArray();

        List<Position.Result> collect =
                StreamSupport.stream(results.spliterator(), false)
                        .map(JsonElement::getAsJsonObject)
                        .map(rt(PlacementParser::parsePosition))
                        .collect(Collectors.toList());
        return new Position(collect);

    }

    private UriBuilder getRoadRefEndpoint() { return rootEndpoint().path("vegreferanseposisjon");}

    private UriBuilder rootEndpoint() {
        return start();
    }
}
