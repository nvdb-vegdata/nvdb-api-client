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
import no.vegvesen.nvdbapi.client.model.datakatalog.DataType;
import no.vegvesen.nvdbapi.client.model.datakatalog.Unit;
import no.vegvesen.nvdbapi.client.model.roadnet.DetailLevel;
import no.vegvesen.nvdbapi.client.model.roadnet.RefLinkPartType;
import no.vegvesen.nvdbapi.client.model.roadnet.TypeOfRoad;
import no.vegvesen.nvdbapi.client.model.roadnet.roadsysref.RoadSysRef;
import no.vegvesen.nvdbapi.client.model.roadobjects.*;
import no.vegvesen.nvdbapi.client.model.roadobjects.attribute.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static no.vegvesen.nvdbapi.client.gson.AttributeTypeParser.parseUnit;
import static no.vegvesen.nvdbapi.client.gson.GsonUtil.*;

public final class RoadObjectParser {
    private RoadObjectParser() {}

    public static RoadObject parse(Map<String, DataType> dataTypes, JsonObject obj) {
        Integer id = parseIntMember(obj, "id");

        Integer typeId = parseIntMember(obj, "metadata.type.id");
        Integer version = parseIntMember(obj, "metadata.versjon");
        LocalDate startDate = parseDateMember(obj, "metadata.startdato"), endDate = parseDateMember(obj, "metadata.sluttdato");
        LocalDateTime lastModified = parseDateTimeMember(obj, "metadata.sist_modifisert");

        List<Attribute> attributes = parseAttributes(obj);

        List<Association> childrenList = parseChildren(dataTypes, obj);

        List<Association> parentList = parseParents(dataTypes, obj);

        Location location = Optional.ofNullable(obj.get("lokasjon"))
            .map(e -> parseLocation(e.getAsJsonObject())).orElse(null);

        Geometry geometry = null;
        if (obj.has("geometri")) {
            geometry = GeometryParser.parse(obj.getAsJsonObject("geometri"));
        }

        List<Segment> segments = parseSegments(obj);

        return new RoadObject(id, typeId, version, startDate, endDate, segments, location, geometry,
            lastModified, attributes, childrenList, parentList);
    }

    private static List<Segment> parseSegments(JsonObject obj) {
        List<Segment> segments = Collections.emptyList();
        JsonArray segmenter = obj.getAsJsonArray("vegsegmenter");
        if (segmenter != null) {
            return StreamSupport.stream(segmenter.spliterator(), false)
                .map(e -> parseSegment(e.getAsJsonObject()))
                .collect(toList());
        }
        return segments;
    }

    private static List<Association> parseParents(Map<String, DataType> dataTypes, JsonObject obj) {
        List<Association> parentList = Collections.emptyList();
        JsonArray parents = getArray(obj, "relasjoner.foreldre").orElse(null);
        return getAssociations(dataTypes, parentList, parents);
    }

    private static List<Association> parseChildren(Map<String, DataType> dataTypes, JsonObject obj) {
        List<Association> childrenList = Collections.emptyList();
        JsonArray children = getArray(obj, "relasjoner.barn").orElse(null);
        return getAssociations(dataTypes, childrenList, children);
    }

    private static List<Association> getAssociations(Map<String, DataType> dataTypes, List<Association> parentList, JsonArray parents) {
        if (parents != null) {
            parentList = StreamSupport.stream(parents.spliterator(), false)
                .map(e -> parseAssociation(dataTypes, e.getAsJsonObject()))
                .collect(toList());
        }
        return parentList;
    }

    static List<Attribute> parseAttributes(JsonObject obj) {
        JsonArray egenskaper = obj.getAsJsonArray("egenskaper");
        if (egenskaper != null) {
            return parseInnhold(egenskaper);
        } else {
            return Collections.emptyList();
        }
    }

    static Location parseLocation(JsonObject obj) {
        List<Integer> municipalities = parseIntListMember(obj, "kommuner");
        List<Integer> counties = parseIntListMember(obj, "fylker");
        List<ContractArea> contractAreas = parseContractAreas(obj);
        List<Route> nationalRoutes = parseRoutes(obj);

        Geometry geometry = null;
        if (obj.has("geometri")) {
            geometry = GeometryParser.parse(obj.get("geometri").getAsJsonObject());
        }

        JsonArray refs = obj.getAsJsonArray("vegsystemreferanser");
        List<RoadSysRef> roadRefs = Collections.emptyList();
        if (refs != null) {
            roadRefs = StreamSupport.stream(refs.spliterator(), false)
                .map(JsonElement::getAsJsonObject)
                .map(RoadSysRefParser::parse)
                .collect(toList());
        }

        List<Placement> placements = Collections.emptyList();
        JsonArray placementsArray = getArray(obj, "stedfestinger").orElse(null);
        if (placementsArray != null) {
            placements = StreamSupport.stream(placementsArray.spliterator(), false)
                .map(JsonElement::getAsJsonObject)
                .map(PlacementParser::parsePlacement)
                .collect(toList());
        }

        Double length = parseDoubleMember(obj, "lengde");

        return new Location(municipalities, counties, length, placements, roadRefs, contractAreas,
            nationalRoutes, geometry);
    }

    static List<Route> parseRoutes(JsonObject obj) {
        List<Route> nationalRoutes = Collections.emptyList();
        if (obj.has("riksvegruter")) {
            nationalRoutes = StreamSupport.stream(obj.getAsJsonArray("riksvegruter").spliterator(), false)
                .map(JsonElement::getAsJsonObject).map(AreaParser::parseRoute)
                .collect(Collectors.toList());
        }
        return nationalRoutes;
    }

    static List<ContractArea> parseContractAreas(JsonObject obj) {
        List<ContractArea> contractAreas = Collections.emptyList();
        if (obj.has("kontraktsområder")) {
            contractAreas = StreamSupport.stream(obj.getAsJsonArray("kontraktsområder").spliterator(), false)
                .map(JsonElement::getAsJsonObject)
                .map(AreaParser::parseContractArea)
                .collect(Collectors.toList());
        }
        return contractAreas;
    }

    private static Segment parseSegment(JsonObject obj) {

        Geometry geo = GeometryParser.parse(obj.getAsJsonObject("geometri"));

        Integer municipality = parseIntMember(obj, "kommune");
        Integer county = parseIntMember(obj, "fylke");

        RoadSysRef ref = null;
        if (obj.has("vegsystemreferanse")) {
            ref = RoadSysRefParser.parse(obj);
        }

        Integer length = parseIntMember(obj, "lengde");

        boolean isPoint = obj.has("relativPosisjon");
        double startPos = isPoint ? parseDoubleMember(obj, "relativPosisjon") : parseDoubleMember(obj, "startposisjon");
        double endPos   = isPoint ? startPos : parseDoubleMember(obj, "sluttposisjon");

        return new Segment(
            parseLongMember(obj, "veglenkesekvensid"),
            startPos,
            endPos,
            geo,
            municipality,
            county,
            ref,
            length,
            parseDateMember(obj, "startdato"),
            parseDateMember(obj, "sluttdato"),
            RefLinkPartType.fromValue(parseStringMember(obj, "veglenkeType")),
            DetailLevel.fromTextValue(parseStringMember(obj, "detaljnivå")),
            TypeOfRoad.fromTextValue(parseStringMember(obj, "typeVeg")));
    }

    public static Attribute parseAttribute(JsonObject obj) {
        Integer id = parseIntMember(obj, "id");
        String egenskapstype = parseStringMember(obj, "egenskapstype");

        switch (egenskapstype) {
            case "Assosiasjon":
                return new AssociationAttribute(id, parseLongMember(obj, "verdi"));
            case "Binær":
                return new BlobAttribute(
                    id,
                    parseIntMember(obj, "blob_id"),
                    parseStringMember(obj, "blob_format"),
                    parseStringMember(obj, "href"));
            case "Boolsk":
                return new BooleanAttribute(id, parseBooleanMember(obj, "verdi"));
            case "Dato":
                return new DateAttribute(id, parseDateMember(obj, "verdi"));
            case "Flyttall":
                return new RealAttribute(id, parseDoubleMember(obj, "verdi"), getUnit(obj));
            case "Flyttallenum":
                return new RealEnumAttribute(id, parseIntMember(obj, "enum_id"), parseDoubleMember(obj, "verdi"));
            case "Geometri":
                return new SpatialAttribute(id, GeometryParser.parseAttribute(obj));
            case "Heltall":
                return new IntegerAttribute(id, parseIntMember(obj, "verdi"), getUnit(obj));
            case "Heltallenum":
                return new IntegerEnumAttribute(id, parseIntMember(obj, "enum_id"), parseIntMember(obj, "verdi"));
            case "Kortdato":
                return new ShortDateAttribute(id, MonthDay.parse("--" + parseStringMember(obj, "verdi")));
            case "Liste":
                return new ListAttribute(
                    id,
                    parseInnhold(obj.getAsJsonArray("innhold")));
            case "Stedfesting":
                String stedfestingstype = parseStringMember(obj, "stedfestingstype");
                switch (stedfestingstype) {
                    case "Punkt":
                        return new ReflinkExtentAttribute(
                            id,
                            parseLongMember(obj, "veglenkesekvensid"),
                            Direction.from(parseStringMember(obj, "retning")),
                            SidePosition.from(parseStringMember(obj, "sideposisjon")),
                            parseStringListMember(obj, "kjørefelt"),
                            parseDoubleMember(obj, "relativPosisjon"),
                            parseDoubleMember(obj, "relativPosisjon")
                        );
                    case "Linje":
                        return new ReflinkExtentAttribute(
                            id,
                            parseLongMember(obj, "veglenkesekvensid"),
                            Direction.from(parseStringMember(obj, "retning")),
                            SidePosition.from(parseStringMember(obj, "sideposisjon")),
                            parseStringListMember(obj, "kjørefelt"),
                            parseDoubleMember(obj, "startposisjon"),
                            parseDoubleMember(obj, "sluttposisjon")
                        );
                    case "Sving": return new TurnExtent(
                        id,
                        parseLongMember(obj, "nodeid"),
                        PlacementParser.parseRefLinkExtentPlacement(obj.getAsJsonObject("startpunkt")),
                        PlacementParser.parseRefLinkExtentPlacement(obj.getAsJsonObject("sluttpunkt"))
                    );
                    default: throw new IllegalArgumentException("Unknown stedfestingstype " + stedfestingstype);
                }
            case "Struktur":
                return new StructAttribute(
                    id,
                    parseInnhold(obj.getAsJsonArray("innhold"))
                    );
            case "Tekst":
                return new StringAttribute(id, parseStringMember(obj, "verdi"));
            case "Tekstenum":
                return new StringEnumAttribute(id, parseIntMember(obj, "enum_id"), parseStringMember(obj, "verdi"));
            case "Tid":
                return new TimeAttribute(id, LocalTime.parse(parseStringMember(obj, "verdi")));
            default: throw new RuntimeException("Ukjent egenskapstype: " + egenskapstype);
        }
    }

    private static Unit getUnit(JsonObject obj) {
        return obj.has("enhet") ? parseUnit(obj.getAsJsonObject("enhet")) : null;
    }

    private static List<Attribute> parseInnhold(JsonArray innhold) {
        return StreamSupport.stream(innhold.spliterator(), false)
            .map(JsonElement::getAsJsonObject)
            .map(RoadObjectParser::parseAttribute)
            .collect(toList());
    }

    private static Association parseAssociation(Map<String, DataType> dataTypes, JsonObject obj) {
        Integer typeId = parseIntMember(obj, "type.id");
        JsonArray objects = obj.get("vegobjekter").getAsJsonArray();
        Set<RoadObject> roadObjects = StreamSupport.stream(objects.spliterator(), false)
            .map(e -> {
                RoadObject ro;
                if (e.isJsonPrimitive()) {
                    ro = new RoadObject(e.getAsLong(), typeId, null, null, null, null, null, null, null, null, null, null);
                } else {
                    ro = parse(dataTypes, e.getAsJsonObject());
                }
                return ro;
            }).collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(RoadObject::getId))));

        return new Association(typeId, roadObjects);
    }

    public static Statistics parseStatistics(JsonObject obj) {
        int numFound = parseIntMember(obj, "antall");
        long length = parseLongMember(obj, "lengde");

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
                parseStringMember(json, "sosinavn"),
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
                    parseIntMember(qualityElement, "synbarhet")))
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
