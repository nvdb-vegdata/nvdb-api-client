/*
 * Copyright (c) 2015-2018, Statens vegvesen
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

package no.vegvesen.nvdbapi.client.model.roadnet;

import java.util.List;
import java.util.Objects;

public class Link {

    private final long id;
    private final List<Port> ports;
    private final List<LinkPart> linkParts;
    private final double length;
    private final boolean fixedLength;

    public Link(long id,
                List<Port> ports,
                List<LinkPart> linkParts,
                double length,
                boolean fixedLength) {
        this.id = id;
        this.ports = ports;
        this.linkParts = linkParts;
        this.length = length;
        this.fixedLength = fixedLength;
    }

    public long getId() {
        return id;
    }

    public List<Port> getPorts() {
        return ports;
    }

    public List<LinkPart> getLinkParts() {
        return linkParts;
    }

    public double getLength() {
        return length;
    }

    public boolean getFixedLength() {
        return fixedLength;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return Double.compare(link.length, length) == 0 &&
                fixedLength == link.fixedLength &&
                Objects.equals(id, link.id) &&
                Objects.equals(ports, link.ports) &&
                Objects.equals(linkParts, link.linkParts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ports, linkParts, length, fixedLength);
    }

    @Override
    public String toString() {
        return "Link{" +
                "id=" + id +
                ", ports=" + ports +
                ", linkParts=" + linkParts +
                ", length=" + length +
                ", fixedLength=" + fixedLength +
                '}';
    }
}