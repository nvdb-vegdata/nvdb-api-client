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

import java.util.Objects;

public class Port {

    private final Integer id;

    private final Double linkPosition;

    private final PortConnection portConnection;
    public Port(Integer id, Double linkPosition, PortConnection portConnection) {
        this.id = id;
        this.linkPosition = linkPosition;
        this.portConnection = portConnection;
    }

    public Integer getId() {
        return id;
    }

    public Double getLinkPosition() {
        return linkPosition;
    }

    public PortConnection getPortConnection() {
        return portConnection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Port port = (Port) o;
        return Objects.equals(getId(), port.getId()) &&
            Objects.equals(getLinkPosition(), port.getLinkPosition()) &&
            Objects.equals(getPortConnection(), port.getPortConnection());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getLinkPosition(), getPortConnection());
    }

    @Override
    public String toString() {
        return "Port{" +
            "id=" + id +
            ", linkPosition=" + linkPosition +
            ", portConnection=" + portConnection +
            '}';
    }
}
