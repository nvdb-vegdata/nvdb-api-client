package no.vegvesen.nvdbapi.client.clients;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import no.vegvesen.nvdbapi.client.gson.PlacementParser;
import no.vegvesen.nvdbapi.client.model.Position;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;
import java.time.LocalDate;
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

    /**
     * Get road system reference for an old reference in hp/meter
     * @param roadRef            Old road reference in hp/m
     * @param startDato          Start date
     * @param sluttDato          End date
     * @param tidspunkt          Tidspunkt
     * @param historisk          Historisk
     * @return   A position with geometry, reflink, municipality and a road system reference (section/part/meter)
     */
    public Position getRoadSysRef(Optional<String> roadRef,
                                  Optional<String> startDato,
                                  Optional<String> sluttDato,
                                  Optional <String> tidspunkt,
                                  Optional <String> historisk) {

        UriBuilder url = getRoadRefEndpoint();

        roadRef.ifPresent(v -> url.queryParam("vegreferanse", v));
        startDato.ifPresent(v -> url.queryParam("startDato", v));
        sluttDato.ifPresent(v -> url.queryParam("sluttDato", v));
        tidspunkt.ifPresent(v -> url.queryParam("tidspunkt", v));
        historisk.ifPresent(v -> url.queryParam("historisk", v));

        WebTarget target = getClient().target(url);
        JsonArray response = JerseyHelper.execute(target).getAsJsonArray();

        return new Position(collectResults(response));
    }


    /**
     * Get old road reference in hp/meter for a reflink position
     *
     * @param reflink            The reflink
     * @param reflinkPosition    The position
     * @return   A postion with geometry, reflink, municipality and a road system reference (section/part/meter)
     */

    public Position getRoadRef(int reflink, double reflinkPosition) {

        UriBuilder url = getRefLinkEndpoint();
        url.queryParam("veglenkesekvens", "" + reflinkPosition + "@" + reflink);
        WebTarget target = getClient().target(url);
        JsonArray response = JerseyHelper.execute(target).getAsJsonArray();

        return new Position(collectResults(response));
    }

    /**
     * Get old road reference in hp/meter for a reflink position
     *
     * @param reflink            The reflink
     * @param reflinkPosition    The position
     * @param date               Search for this date
     * @return   A postion with geometry, reflink, municipality and a road system reference (section/part/meter)
     */
    public Position getRoadRef(int reflink, double reflinkPosition, LocalDate date) {

        UriBuilder url = getRefLinkEndpoint();
        url.queryParam("veglenkesekvens", "" + reflinkPosition + "@" + reflink);
        url.queryParam("tidspunkt", date.toString());
        WebTarget target = getClient().target(url);
        JsonArray response = JerseyHelper.execute(target).getAsJsonArray();

        return new Position(collectResults(response));
    }

    /**
     * Get old road reference in hp/meter for a reflink position
     * @param reflink            The reflink
     * @param reflinkPosition    The position
     * @param history            Include history - return all segments from past and present
     * @return    A postion with geometry, reflink, municipality and a road system reference (section/part/meter)
     */
    public Position getRoadRef(int reflink, double reflinkPosition, boolean history) {

        UriBuilder url = getRefLinkEndpoint();
        url.queryParam("veglenkesekvens", "" + reflinkPosition + "@" + reflink);
        url.queryParam("historisk", history);
        WebTarget target = getClient().target(url);
        JsonArray response = JerseyHelper.execute(target).getAsJsonArray();

        return new Position(collectResults(response));
    }

    private List<Position.Result> collectResults(JsonArray results) {
        return StreamSupport.stream(results.spliterator(), false)
                .map(JsonElement::getAsJsonObject)
                .map(rt(PlacementParser::parsePosition))
                .collect(Collectors.toList());
    }


    private UriBuilder getRoadRefEndpoint() { return rootEndpoint().path("vegreferanseposisjon");}

    private UriBuilder getRefLinkEndpoint() { return rootEndpoint().path("vegreferanse");}

    private UriBuilder rootEndpoint() {
        return start();
    }
}
