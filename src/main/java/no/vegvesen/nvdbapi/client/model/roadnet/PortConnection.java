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

public class PortConnection {

    private final Integer portId;
    private final Long netelementId;
    private final NetElementType netElementType;

    public PortConnection(Integer portId,
                          Long netelementId,
                          NetElementType netElementType) {
        this.portId = portId;
        this.netelementId = netelementId;
        this.netElementType = netElementType;
    }

    public Integer getPortId() {
        return portId;
    }

    public Long getNetelementId() {
        return netelementId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PortConnection that = (PortConnection) o;
        return Objects.equals(getPortId(), that.getPortId()) &&
                Objects.equals(getNetelementId(), that.getNetelementId()) &&
                getNetElementType() == that.getNetElementType();
    }

    public NetElementType getNetElementType() {
        return netElementType;
    }

    @Override
    public int hashCode() {

        return Objects.hash(getPortId(), getNetelementId(), getNetElementType());
    }

    @Override
    public String toString() {
        return "PortConnection{" +
                "portId=" + portId +
                ", netElementId=" + netelementId +
                ", netElementType=" + netElementType +
                '}';
    }
}
