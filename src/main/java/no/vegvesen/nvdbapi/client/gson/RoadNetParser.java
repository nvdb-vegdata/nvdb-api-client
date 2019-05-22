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
import no.vegvesen.nvdbapi.client.model.Geometry;
import no.vegvesen.nvdbapi.client.model.roadnet.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;
import static no.vegvesen.nvdbapi.client.gson.GsonUtil.*;

public final class RoadNetParser {

    private RoadNetParser() { }

    public static LinkSequence parseLinkSequence(JsonObject obj){
        if(obj==null) return null;

        long id = parseLongMember(obj, "veglenkesekvensid");
        List<Port> ports = parsePorts(obj.getAsJsonArray("porter"));
        List<Link> links = parseLinkPorts(obj.getAsJsonArray("veglenker"));

        double length = parseDoubleMember(obj, "lengde");
        boolean fixedLength = parseBooleanMember(obj, "låst_lengde");
        return new LinkSequence(id, ports, links, length, fixedLength);
    }

    public static Node parseNode(JsonObject obj){
        if(obj==null) return null;

        Integer id = parseIntMember(obj, "id");
        Geometry geometry = GeometryParser.parse(obj.getAsJsonObject("geometri"));
        LocalDate startDate = parseDateMember(obj, "startdato");
        LocalDate endDate = parseDateMember(obj, "sluttdato");
        List<Port> ports = parsePorts(obj.getAsJsonArray("porter"));

        return new Node(id, geometry, startDate, endDate, ports);
    }

    private static List<Link> parseLinkPorts(JsonArray obj) {
        return StreamSupport
                .stream(obj.spliterator(), false)
                .map(JsonElement::getAsJsonObject)
                .map(o -> new Link(
                        parseIntMember(o, "veglenkenummer"),
                        parseBooleanMember(o, "konnekteringslenke"),
                        parseBooleanMember(o, "detaljert"),
                        TopologyLevel.fromValue(parseStringMember(o, "topologinivå")),
                        parseIntMember(o, "startport"),
                        parseIntMember(o, "sluttport"),
                        parseDoubleMember(o, "startposisjon"),
                        parseDoubleMember(o, "sluttposisjon"),
                        parseIntMember(o, "geometri.kommune"),
                        parseIntMember(o, "geometri.kommune"),
                        parseDoubleMember(o, "geometri.lengde"),
                        parseStringMember(o, "målemetode"),
                        parseDateMember(o, "måledato"),
                        SosiMedium.from(parseStringMember(o, "medium")),
                        Ltema.from(parseIntMember(o, "geometri.temakode")),
                        PlacementParser.parsePlacement(o.getAsJsonObject("superstedfesting")),
                        parseStringMember(o, "typeVeg"),
                        parseStringMember(o, "detaljnivå"),
                        GeometryParser.parse(o.getAsJsonObject("geometri")),
                        parseFields(o.getAsJsonArray("feltoversikt")),
                        parseDateMember(o, "startdato"),
                        parseDateMember(o, "sluttdato")
                ))
                .collect(toList());
    }

    private static List<String> parseFields(JsonArray obj) {
        List<String> fields = new ArrayList<>();
        if (obj != null) {
            obj.forEach(p -> fields.add(p.toString()));
        }
        return fields;
    }

    private static List<Port> parsePorts(JsonArray obj) {
        List<Port> ports = new ArrayList<>();
        if (obj != null) {
            obj.forEach(p ->
                    ports.add(new Port(
                            parseIntMember(p.getAsJsonObject(), "id"),
                            parseDoubleMember(p.getAsJsonObject(), "relativposisjon"),
                            getTilkobling(p))));
        }
        return ports;
    }

    private static PortConnection getTilkobling(JsonElement p) {
        return p.getAsJsonObject().getAsJsonObject("tilkobling") == null
                ? null
                : new PortConnection(parseIntMember(p.getAsJsonObject(), "tilkobling.portnummer"),
                parseLongMember(p.getAsJsonObject(), "tilkobling.nodeid"));
    }

    public static NetElementWrapper parseNetElement(JsonObject obj) {
        if(obj==null) return null;
        Integer netelementtype = parseIntMember(obj, "netelementtype");
        if (netelementtype == NetElementType.NODE.getValue()) {
            return new NetElementWrapper(parseNode(obj));
        } else if (netelementtype == NetElementType.LENKE.getValue()) {
            return new NetElementWrapper(parseLinkSequence(obj));
        }
        throw new RuntimeException("Netelement with type " + netelementtype + " not recognized");
    }
}
