/*
 * Copyright (c) 2015-2017, Statens vegvesen
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package no.vegvesen.nvdbapi.client.clients;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import no.vegvesen.nvdbapi.client.clients.util.JerseyHelper;
import no.vegvesen.nvdbapi.client.gson.AttributeTypeParser;
import no.vegvesen.nvdbapi.client.gson.FeatureTypeParser;
import no.vegvesen.nvdbapi.client.gson.DatakatalogVersionParser;
import no.vegvesen.nvdbapi.client.model.datakatalog.*;
import no.vegvesen.nvdbapi.client.util.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class DatakatalogClient extends AbstractJerseyClient {
    private static final Logger LOG = LoggerFactory.getLogger(DatakatalogClient.class);

    protected DatakatalogClient(String baseUrl, Client client) {
        super(baseUrl, client);
    }

    public Version getVersion() {
        WebTarget target = getClient().target(endpoint()).path("versjon");
        return JerseyHelper.executeOptional(target).map(JsonElement::getAsJsonObject).map(DatakatalogVersionParser::parseVersion).get();
    }

    public List<DataType> getDataTypes() {
        WebTarget target = getClient().target(endpoint()).path("datatyper");
        JsonElement types = JerseyHelper.execute(target);
        return StreamSupport.stream(types.getAsJsonArray().spliterator(), false)
                .map(JsonElement::getAsJsonObject)
                .map(AttributeTypeParser::parseDataType)
                .collect(Collectors.toList());
    }

    public Map<Integer, DataType> getDataTypeMap() {
        return getDataTypes().stream().collect(Collectors.toMap(DataType::getId, Function.identity()));
    }

    public List<Unit> getUnits() {
        WebTarget target = getClient().target(endpoint()).path("enheter");
        JsonElement units = JerseyHelper.execute(target);
        return StreamSupport.stream(units.getAsJsonArray().spliterator(), false)
                .map(JsonElement::getAsJsonObject)
                .map(AttributeTypeParser::parseUnit)
                .collect(Collectors.toList());
    }

    public Map<Integer, Unit> getUnitMap() {
        return getUnits().stream().collect(Collectors.toMap(Unit::getId, Function.identity()));
    }

    public Optional<AttributeType> getAttributeType(Map<Integer, DataType> dataTypes, int typeId) {
        WebTarget target = getClient().target(endpoint()).path("egenskapstyper").path(Integer.toString(typeId));
        return JerseyHelper.executeOptional(target).map(JsonElement::getAsJsonObject).map(o -> AttributeTypeParser.parse(dataTypes, o));
    }

    public Datakatalog getDatakalog() {
        Version v = getVersion();
        List<Unit> units = getUnits();
        Map<Integer, DataType> dataTypes = getDataTypeMap();

        List<FeatureType> featureTypes = getFeatureTypes(dataTypes, Include.ALL);
        return new Datakatalog(v, featureTypes, units, dataTypes);
    }

    public List<FeatureType> getFeatureTypes(Map<Integer, DataType> dataTypes, Include... informationToIncludes) {
        WebTarget target = getClient().target(endpoint());

        String includeArgument = getIncludeArgument(false, informationToIncludes);
        if (includeArgument != null) target = target.queryParam("inkluder", includeArgument);

        List<FeatureType> types = new ArrayList<>();
        Stopwatch sw = Stopwatch.createStarted();
        JsonArray array = JerseyHelper.executeOptional(target).map(JsonElement::getAsJsonArray).get();
        long requestTime = sw.stop().elapsedMillis();
        sw = Stopwatch.createStarted();
        array.forEach(e -> types.add(FeatureTypeParser.parse(dataTypes, e.getAsJsonObject())));
        long parsingTime = sw.stop().elapsedMillis();
        LOG.debug("Request execution took {} ms. Request parsing took {} ms. Total: {} ms.", requestTime, parsingTime, requestTime + parsingTime);
        return types;
    }

    public Optional<FeatureType> getFeatureType(Map<Integer, DataType> types, int typeId, Include... informationToInclude) {
        WebTarget target = getClient().target(endpoint().path(Integer.toString(typeId)));

        String includeArgument = getIncludeArgument(true, informationToInclude);
        if (includeArgument != null) target = target.queryParam("inkluder", includeArgument);

        return JerseyHelper.executeOptional(target).map(JsonElement::getAsJsonObject).map(o -> FeatureTypeParser.parse(types, o));
    }

    private static String getIncludeArgument(boolean singleRequest, Include... informationToInclude) {
        Set<Include> values = informationToInclude.length > 0 ? new HashSet<>(Arrays.asList(informationToInclude)) : Collections.emptySet();

        // Defaults
        if (values.isEmpty()) {
            return singleRequest ? "alle" : null;
        }

        // "All" trumps any other values
        if (values.contains(Include.ALL)) {
            return Include.ALL.value;
        }

        return values.stream().map(i -> i.value).collect(Collectors.joining(","));
    }

    private UriBuilder endpoint() {
        return start().path("vegobjekttyper");
    }

    public enum Include {
        ATTRIBUTES("egenskapstyper"),
        ASSOCIATIONS("relasjonstyper"),
        GUIDANCE_PARAMETERS("styringsparametere"),
        ALL("alle");

        private final String value;

        Include(String value) {
            this.value = value;
        }
    }
}
