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

public class LinkSequence {

    private final long id;
    private final List<Port> ports;
    private final List<Link> links;
    private final double length;
    private final boolean fixedLength;

    public LinkSequence(long id,
                        List<Port> ports,
                        List<Link> links,
                        double length,
                        boolean fixedLength) {
        this.id = id;
        this.ports = ports;
        this.links = links;
        this.length = length;
        this.fixedLength = fixedLength;
    }

    public long getId() {
        return id;
    }

    public List<Port> getPorts() {
        return ports;
    }

    public List<Link> getLinks() {
        return links;
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
        LinkSequence linkSequence = (LinkSequence) o;
        return Double.compare(linkSequence.length, length) == 0 &&
                fixedLength == linkSequence.fixedLength &&
                Objects.equals(id, linkSequence.id) &&
                Objects.equals(ports, linkSequence.ports) &&
                Objects.equals(links, linkSequence.links);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ports, links, length, fixedLength);
    }

    @Override
    public String toString() {
        return "LinkSequence{" +
                "id=" + id +
                ", ports=" + ports +
                ", links=" + links +
                ", length=" + length +
                ", fixedLength=" + fixedLength +
                '}';
    }
}