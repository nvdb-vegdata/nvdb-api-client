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

import no.vegvesen.nvdbapi.client.model.Geometry;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Node {

    private final Long id;
    private final Geometry geometry;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final List<Port> ports;

    public Node(Long id, Geometry geometry, LocalDate startDate, LocalDate endDate, List<Port> ports) {
        this.id = id;
        this.geometry = geometry;
        this.startDate = startDate;
        this.endDate = endDate;
        this.ports = ports;
    }

    public Long getId() {
        return id;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public List<Port> getPorts() {
        return ports;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(getId(), node.getId()) &&
            Objects.equals(getGeometry(), node.getGeometry()) &&
            Objects.equals(getStartDate(), node.getStartDate()) &&
            Objects.equals(getEndDate(), node.getEndDate()) &&
            Objects.equals(getPorts(), node.getPorts());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getGeometry(), getStartDate(), getEndDate(), getPorts());
    }

    @Override
    public String toString() {
        return "Node{" +
            "id=" + id +
            ", geometry=" + geometry +
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            ", ports=" + ports +
            '}';
    }
}
