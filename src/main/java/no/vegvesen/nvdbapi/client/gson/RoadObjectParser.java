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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import no.vegvesen.nvdbapi.client.model.Direction;
import no.vegvesen.nvdbapi.client.model.Geometry;
import no.vegvesen.nvdbapi.client.model.SidePosition;
import no.vegvesen.nvdbapi.client.model.areas.ContractArea;
import no.vegvesen.nvdbapi.client.model.areas.Route;
import no.vegvesen.nvdbapi.client.model.areas.Street;
import no.vegvesen.nvdbapi.client.model.datakatalog.Unit;
import no.vegvesen.nvdbapi.client.model.roadnet.DetailLevel;
import no.vegvesen.nvdbapi.client.model.roadnet.RefLinkPartType;
import no.vegvesen.nvdbapi.client.model.roadnet.TypeOfRoad;
import no.vegvesen.nvdbapi.client.model.roadnet.roadsysref.RoadSysRef;
import no.vegvesen.nvdbapi.client.model.roadobjects.*;
import no.vegvesen.nvdbapi.client.model.roadobjects.attribute.AssociationAttribute;
import no.vegvesen.nvdbapi.client.model.roadobjects.attribute.Attribute;
import no.vegvesen.nvdbapi.client.model.roadobjects.attribute.BlobAttribute;
import no.vegvesen.nvdbapi.client.model.roadobjects.attribute.BooleanAttribute;
import no.vegvesen.nvdbapi.client.model.roadobjects.attribute.DateAttribute;
import no.vegvesen.nvdbapi.client.model.roadobjects.attribute.IntegerAttribute;
import no.vegvesen.nvdbapi.client.model.roadobjects.attribute.IntegerEnumAttribute;
import no.vegvesen.nvdbapi.client.model.roadobjects.attribute.ListAttribute;
import no.vegvesen.nvdbapi.client.model.roadobjects.attribute.RealAttribute;
import no.vegvesen.nvdbapi.client.model.roadobjects.attribute.RealEnumAttribute;
import no.vegvesen.nvdbapi.client.model.roadobjects.attribute.ReflinkExtentAttribute;
import no.vegvesen.nvdbapi.client.model.roadobjects.attribute.ShortDateAttribute;
import no.vegvesen.nvdbapi.client.model.roadobjects.attribute.SpatialAttribute;
import no.vegvesen.nvdbapi.client.model.roadobjects.attribute.StringAttribute;
import no.vegvesen.nvdbapi.client.model.roadobjects.attribute.StringEnumAttribute;
import no.vegvesen.nvdbapi.client.model.roadobjects.attribute.StructAttribute;
import no.vegvesen.nvdbapi.client.model.roadobjects.attribute.TimeAttribute;
import no.vegvesen.nvdbapi.client.model.roadobjects.attribute.TurnExtent;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

import static no.vegvesen.nvdbapi.client.gson.AttributeTypeParser.parseUnit;
import static no.vegvesen.nvdbapi.client.gson.GsonUtil.getArray;
import static no.vegvesen.nvdbapi.client.gson.GsonUtil.parseBooleanMember;
import static no.vegvesen.nvdbapi.client.gson.GsonUtil.parseDateMember;
import static no.vegvesen.nvdbapi.client.gson.GsonUtil.parseDateTimeMember;
import static no.vegvesen.nvdbapi.client.gson.GsonUtil.parseDoubleMember;
import static no.vegvesen.nvdbapi.client.gson.GsonUtil.parseIntListMember;
import static no.vegvesen.nvdbapi.client.gson.GsonUtil.parseIntMember;
import static no.vegvesen.nvdbapi.client.gson.GsonUtil.parseLongMember;
import static no.vegvesen.nvdbapi.client.gson.GsonUtil.parseStringListMember;
import static no.vegvesen.nvdbapi.client.gson.GsonUtil.parseStringMember;

public final class RoadObjectParser {
    private RoadObjectParser() {}

    public static RoadObject parse(JsonObject obj) {
        Integer id = parseIntMember(obj, "id");

        Integer typeId = parseIntMember(obj, "metadata.type.id");
        Integer version = parseIntMember(obj, "metadata.versjon");
        LocalDate startDate = parseDateMember(obj, "metadata.startdato"), endDate = parseDateMember(obj, "metadata.sluttdato");
        LocalDateTime lastModified = parseDateTimeMember(obj, "metadata.sist_modifisert");

        List<Attribute> attributes = parseAttributes(obj);

        List<Association> childrenList = parseChildren(obj);

        List<Association> parentList = parseParents(obj);

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

    private static List<Association> parseParents(JsonObject obj) {
        JsonArray parents = getArray(obj, "relasjoner.foreldre").orElse(null);
        return getAssociations(parents);
    }

    private static List<Association> parseChildren(JsonObject obj) {
        JsonArray children = getArray(obj, "relasjoner.barn").orElse(null);
        return getAssociations(children);
    }

    private static List<Association> getAssociations(JsonArray parents) {
        if (parents != null) {
            return StreamSupport.stream(parents.spliterator(), false)
                .map(e -> parseAssociation(e.getAsJsonObject()))
                .collect(toList());
        } else {
            return Collections.emptyList();
        }
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
        List<Street> streets = parseStreets(obj);

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
            nationalRoutes, streets, geometry);
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

    static List<Street> parseStreets(JsonObject obj) {
        List<Street> streets = Collections.emptyList();
        if (obj.has("adresser")) {
            streets = StreamSupport.stream(obj.getAsJsonArray("adresser").spliterator(), false)
                .map(JsonElement::getAsJsonObject)
                .map(street -> new Street(
                        parseStringMember(street, "adressenavn"),
                        parseIntMember(street,"adressekode"),
                        parseBooleanMember(street, "sideveg")
                ))
                .collect(Collectors.toList());
        }
        return streets;
    }

    static Street parseStreet(JsonObject obj) {
        if(obj.has("adresse")) {
            JsonObject street = obj.getAsJsonObject("adresse");
            return new Street(
                    parseStringMember(street, "navn"),
                    parseIntMember(street,"adressekode"),
                    parseBooleanMember(street, "sideveg")
            );
        } else {
            return null;
        }
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
            ref = RoadSysRefParser.parse(obj.getAsJsonObject("vegsystemreferanse"));
        }

        Double length = parseDoubleMember(obj, "lengde");

        boolean isPoint = obj.has("relativPosisjon");
        double startPos = isPoint ? parseDoubleMember(obj, "relativPosisjon") : parseDoubleMember(obj, "startposisjon");
        double endPos   = isPoint ? startPos : parseDoubleMember(obj, "sluttposisjon");

        Segment segment = new Segment(
            parseLongMember(obj, "veglenkesekvensid"),
            startPos,
            endPos,
            Direction.from(parseStringMember(obj, "retning")),
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
        if (obj.has("feltoversikt")){
            return ElvegSegment.fromSegment(segment, parseStringListMember(obj, "feltoversikt"));
        } else return segment;
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

    private static Association parseAssociation(JsonObject obj) {
        Integer typeId = parseIntMember(obj, "type.id");
        JsonArray objects = obj.get("vegobjekter").getAsJsonArray();
        Set<RoadObject> roadObjects = StreamSupport.stream(objects.spliterator(), false)
            .map(e -> {
                RoadObject ro;
                if (e.isJsonPrimitive()) {
                    ro = new RoadObject(e.getAsLong(), typeId, null, null, null, null, null, null, null, null, null, null);
                } else {
                    ro = parse(e.getAsJsonObject());
                }
                return ro;
            }).collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(RoadObject::getId))));

        return new Association(typeId, roadObjects);
    }

    public static Statistics parseStatistics(JsonObject obj) {
        Integer numFound = parseIntMember(obj, "antall");
        Double length = parseDoubleMember(obj, "lengde");

        if (nonNull(numFound) && nonNull(length)) return new Statistics(numFound, length);

        if (nonNull(numFound)) {
            return new Statistics(numFound);
        } else {
            return new Statistics(length);
        }
    }

    public static RoadObjectType parseRoadObjectType(JsonObject obj){
        if(obj == null) return null;
        Integer id = parseIntMember(obj, "id");
        String name = parseStringMember(obj, "navn");

        return new RoadObjectType(id, name, parseStatistics(obj.getAsJsonObject("statistikk")));
    }

    public static RoadObjectTypeWithStats parseRoadObjectTypeWithStats(JsonObject o) {
        return new RoadObjectTypeWithStats(
            o.get("id").getAsInt(),
            o.get("navn").getAsString(),
            parseStatistics(o.getAsJsonObject("statistikk"))
        );
    }
}
