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

package no.vegvesen.nvdbapi.client.gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import no.vegvesen.nvdbapi.client.model.*;
import no.vegvesen.nvdbapi.client.model.areas.ContractArea;
import no.vegvesen.nvdbapi.client.model.areas.Route;
import no.vegvesen.nvdbapi.client.model.roadobjects.*;
import no.vegvesen.nvdbapi.client.model.datakatalog.DataType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static no.vegvesen.nvdbapi.client.gson.GsonUtil.*;

public final class RoadObjectParser {
    private RoadObjectParser() {}

    public static RoadObject parse(Map<Integer, DataType> dataTypes, JsonObject obj) {
        Integer id = parseIntMember(obj, "id");

        Integer typeId = parseIntMember(obj, "metadata.type.id");
        Integer version = parseIntMember(obj, "metadata.versjon");
        LocalDate startDate = parseDateMember(obj, "metadata.startdato"), endDate = parseDateMember(obj, "metadata.sluttdato");
        LocalDateTime lastModified = parseDateTimeMember(obj, "metadata.sist_modifisert");

        List<Attribute> attributes = Collections.emptyList();
        JsonArray egenskaper = obj.getAsJsonArray("egenskaper");
        if (egenskaper != null) {
            attributes = StreamSupport.stream(egenskaper.spliterator(), false)
                    .map(e -> parseAttribute(dataTypes, e.getAsJsonObject()))
                    .collect(Collectors.toList());
        }

        List<Association> childrenList = Collections.emptyList();
        JsonArray children = getArray(obj, "relasjoner.barn").orElse(null);
        if (children != null) {
            childrenList = StreamSupport.stream(children.spliterator(), false)
                    .map(e -> parseAssociation(dataTypes, e.getAsJsonObject()))
                    .collect(Collectors.toList());
        }

        List<Association> parentList = Collections.emptyList();
        JsonArray parents = getArray(obj, "relasjoner.foreldre").orElse(null);
        if (parents != null) {
            parentList = StreamSupport.stream(parents.spliterator(), false)
                    .map(e -> parseAssociation(dataTypes, e.getAsJsonObject()))
                    .collect(Collectors.toList());
        }

        JsonElement loc = obj.get("lokasjon");
        Location location = Optional.ofNullable(loc).map(e -> parseLocation(e.getAsJsonObject())).orElse(null);

        Geometry geometry = null;
        if (obj.has("geometri")) {
            geometry = GeometryParser.parse(obj.getAsJsonObject("geometri"));
        }

        SegmentationFilter segFilter = null;
        JsonElement segmentation = obj.get("segmentering");
        if (segmentation != null) {
            segFilter = parseSegmentFilter(segmentation.getAsJsonObject());
        }

        List<Segment> segments = Collections.emptyList();
        JsonArray segmenter = obj.getAsJsonArray("vegsegmenter");
        if (segmenter != null) {
            segments = StreamSupport.stream(segmenter.spliterator(), false)
                    .map(e -> parseSegment(e.getAsJsonObject()))
                    .collect(Collectors.toList());
        }

        return new RoadObject(id, typeId, version, startDate, endDate, segFilter, segments, location, geometry,
                lastModified, attributes, childrenList, parentList);
    }

    private static Location parseLocation(JsonObject obj) {

        List<Integer> municipalities = parseIntListMember(obj, "kommuner");
        List<Integer> counties = parseIntListMember(obj, "fylker");
        List<Integer> regions  = parseIntListMember(obj, "regioner");
        List<Integer> departments = parseIntListMember(obj, "vegavdelinger");
        List<ContractArea> contractAreas = Collections.emptyList();
        if (obj.has("kontraktsområder")) {
            contractAreas = StreamSupport.stream(obj.getAsJsonArray("kontraktsområder").spliterator(), false)
                    .map(JsonElement::getAsJsonObject)
                    .map(AreaParser::parseContractArea)
                    .collect(Collectors.toList());
        }
        List<Route> nationalRoutes = Collections.emptyList();
        if (obj.has("riksvegruter")) {
            nationalRoutes = StreamSupport.stream(obj.getAsJsonArray("riksvegruter").spliterator(), false)
                    .map(JsonElement::getAsJsonObject).map(AreaParser::parseRoute)
                    .collect(Collectors.toList());
        }

        Geometry geometry = null;
        if (obj.has("geometri")) {
            geometry = GeometryParser.parse(obj.get("geometri").getAsJsonObject());
        }

        JsonArray refs = obj.getAsJsonArray("vegreferanser");
        List<RoadRef> roadRefs = Collections.emptyList();
        if (refs != null) {
            roadRefs = StreamSupport.stream(refs.spliterator(), false)
                    .map(e -> new RoadRef(-123))
                    .collect(Collectors.toList());
        }

        List<Placement> placements = Collections.emptyList();
        JsonArray placementsArray = getArray(obj, "stedfestinger").orElse(null);
        if (placementsArray != null) {
            placements = StreamSupport.stream(placementsArray.spliterator(), false)
                    .map(JsonElement::getAsJsonObject)
                    .map(RoadObjectParser::parsePlacement)
                    .collect(Collectors.toList());
        }

        Double length = parseDoubleMember(obj, "strekningslengde");

        return new Location(municipalities, counties, regions,
                departments, length, placements, roadRefs,
                contractAreas, nationalRoutes, geometry);
    }

    private static SegmentationFilter parseSegmentFilter(JsonObject object) {
        List<Integer> municipalities = Optional.ofNullable(parseIntListMember(object, "kommuner")).orElse(Collections.emptyList());
        List<Integer> counties = Optional.ofNullable(parseIntListMember(object, "fylker")).orElse(Collections.emptyList());
        List<Integer> regions = Optional.ofNullable(parseIntListMember(object, "regioner")).orElse(Collections.emptyList());
        List<Integer> roadDepartments = Optional.ofNullable(parseIntListMember(object, "vegavdelinger")).orElse(Collections.emptyList());
        List<RoadRefFilter> roadRefFilters = Optional.ofNullable(parseRoadRefFilter(object)).orElse(Collections.emptyList());

        return new SegmentationFilter(municipalities, counties,
                regions, roadDepartments, roadRefFilters);
    }

    private static Segment parseSegment(JsonObject obj) {
        Placement placement = parsePlacement(obj.getAsJsonObject("stedfesting"));

        Geometry geo = GeometryParser.parse(obj.getAsJsonObject("geometri"));

        Integer municipality = parseIntMember(obj, "kommune");
        Integer county = parseIntMember(obj, "fylke");
        Integer region  = parseIntMember(obj, "region");
        Integer department = parseIntMember(obj, "vegavdeling");

        RoadRef ref = null;
        if (obj.has("vegreferanse")) {
            ref = new RoadRef(-123);
        }

        Integer length = parseIntMember(obj, "strekningslengde");

        return new Segment(geo, municipality, county, region, department, placement, ref, length);
    }

    private static Placement parsePlacement(JsonObject obj) {
        int netElementId = parseIntMember(obj, "veglenkeid");

        double startPos, endPos;
        if (obj.has("posisjon")) {
            startPos = endPos = parseDoubleMember(obj, "posisjon");
        } else {
            startPos = parseDoubleMember(obj, "fra_posisjon");
            endPos = parseDoubleMember(obj, "til_posisjon");
        }

        Direction dir = Optional.of(parseStringMember(obj, "retning"))
                .map(Direction::from)
                .orElse(null);
        SidePosition sidePos = Optional.ofNullable(parseStringMember(obj, "sideposisjon")).map(SidePosition::from).orElse(null);
        List<String> lane = parseStringListMember(obj, "felt");

        HeightLevel heightLevel = Optional.ofNullable(parseStringMember(obj, "høyde"))
                .map(HeightLevel::from)
                .orElse(null);

        return new Placement(netElementId, startPos, endPos, dir, sidePos, heightLevel, lane);
    }

    private static List<RoadRefFilter> parseRoadRefFilter(JsonObject object) {
        if (!object.has("vegreferanser")) {
            return Collections.emptyList();
        }

        JsonArray a = object.getAsJsonArray("vegreferanser");

        return StreamSupport.stream(a.spliterator(), false).map(JsonElement::getAsJsonObject)
                .map(o -> {
                    Integer c = parseIntMember(o, "fylke");
                    Integer m = parseIntMember(o, "kommune");
                    String cat = parseStringMember(o, "kategori");
                    String stat = parseStringMember(o, "status");
                    Integer n = parseIntMember(o, "nummer");
                    Integer fromHp = null, toHp = null;
                    if (o.has("hp")) {
                        fromHp = parseIntMember(o, "hp");
                    } else {
                        fromHp = parseIntMember(o, "fra_hp");
                        toHp = parseIntMember(o, "til_hp");
                    }

                    return new RoadRefFilter(c, m, cat, stat, n, fromHp, toHp);
                })
                .collect(Collectors.toList());
    }

    public static Attribute parseAttribute(Map<Integer, DataType> dataTypes, JsonObject obj) {
        Integer id = parseIntMember(obj, "id");
        String name = parseStringMember(obj, "navn");
        Integer enumId = parseIntMember(obj, "enum_id");
        int dataTypeId = parseIntMember(obj, "datatype");
        DataType dataType = dataTypes.get(dataTypeId);
        Object value = parseAttributeValue(obj, "verdi", dataType.getJavaType());
        String href = parseStringMember(obj, "href");
        String blobFormat = parseStringMember(obj, "blob_format");
        Integer blobId = parseIntMember(obj, "blob_id");

        GeometryAttributes geometryAttributes = getGeometryAttributes(obj);
        Quality quality = getQuality(obj);

        Geometry attribGeometry = null;
        if (Objects.nonNull(quality)) {
            // Quality must be set to add geometry to an attribute
            attribGeometry = new Geometry((String) value, Projection.UTM33, quality, false, true, geometryAttributes);
        }

        return new Attribute(id, name, dataType, value, Optional.ofNullable(enumId), Optional.ofNullable(href),
                Optional.ofNullable(blobId), Optional.ofNullable(blobFormat), Optional.ofNullable(attribGeometry));
    }

    private static Association parseAssociation(Map<Integer, DataType> dataTypes, JsonObject obj) {
        Integer typeId = parseIntMember(obj, "type.id");
        JsonArray objects = obj.get("vegobjekter").getAsJsonArray();
        Set<RoadObject> roadObjects = StreamSupport.stream(objects.spliterator(), false).map(e -> {
            RoadObject ro;
            if (e.isJsonPrimitive()) {
                ro = new RoadObject(e.getAsLong(), typeId, null, null, null, null, null, null, null, null, null, null, null);
            } else {
                ro = parse(dataTypes, e.getAsJsonObject());
            }
            return ro;
        }).collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(RoadObject::getId))));

        return new Association(typeId, roadObjects);
    }

    public static Statistics parseStatistics(JsonObject obj) {
        int numFound = parseIntMember(obj, "antall");
        long length = parseLongMember(obj, "strekningslengde");

        return new Statistics(numFound, length);
    }

    public static RoadObjectType parseRoadObjectType(JsonObject obj){
        if(obj == null) return null;
        Integer id = parseIntMember(obj, "id");
        String name = parseStringMember(obj, "navn");

        return new RoadObjectType(id, name, parseStatistics(obj.getAsJsonObject("statistikk")));
    }

    private static GeometryAttributes getGeometryAttributes(JsonObject obj) {
        Optional<JsonElement> geometryElement = getNode(obj, "geometriattributt");
        if (geometryElement.isPresent()) {
            JsonObject json = (JsonObject) geometryElement.get();
            return new GeometryAttributes(
                    parseDateMember(json, "datafangstdato"),
                    parseDateMember(json, "verifiseringsdato"),
                    parseDateMember(json, "oppdateringsdato"),
                    parseStringMember(json, "prosesshistorikk"),
                    parseIntMember(json, "kommune"),
                    parseStringMember(json, "medium"),
                    parseIntMember(json, "sosinavn"),
                    parseIntMember(json, "temakode"),
                    parseBooleanMember(json, "referansegeometri"),
                    parseDoubleMember(json, "lengde"),
                    parseIntMember(json, "høydereferanse"));
        } else {
            return null;
        }
    }

    private static Quality getQuality(JsonObject obj) {
        return getNode(obj, "kvalitet")
                .map(JsonElement::getAsJsonObject)
                .map(qualityElement ->
                        new Quality(
                                parseIntMember(qualityElement, "målemetode"),
                                parseIntMember(qualityElement, "nøyaktighet"),
                                parseIntMember(qualityElement, "målemetodeHøyde"),
                                parseIntMember(qualityElement, "nøyaktighetHøyde"),
                                parseIntMember(qualityElement, "maksimaltAvvik"),
                                parseIntMember(qualityElement, "synbarhet"),
                                parseDateMember(qualityElement, "verifiseringsdato")))
                .orElse(null);
    }

    public static RoadObjectTypeWithStats parseRoadObjectTypeWithStats(JsonObject o) {
        return new RoadObjectTypeWithStats(
                o.get("id").getAsInt(),
                o.get("navn").getAsString(),
                parseStatistics(o.getAsJsonObject("statistikk"))
        );
    }
}
