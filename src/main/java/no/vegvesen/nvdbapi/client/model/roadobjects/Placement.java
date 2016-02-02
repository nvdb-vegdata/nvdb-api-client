/*
 * Copyright (c) 2015-2016, Statens vegvesen
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

package no.vegvesen.nvdbapi.client.model.roadobjects;

import no.vegvesen.nvdbapi.client.model.Direction;
import no.vegvesen.nvdbapi.client.model.SidePosition;

public class Placement {
    private final int netElementId;
    private final double startPosition;
    private final double endPosition;
    private final Direction direction;
    private final SidePosition sidePos;
    private final String lane;

    public Placement(int netElementId, double startPosition, double endPosition, Direction direction, SidePosition sidePos, String lane) {
        this.netElementId = netElementId;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.direction = direction;
        this.sidePos = sidePos;
        this.lane = lane;
    }

    public int getNetElementId() {
        return netElementId;
    }

    public double getStartPosition() {
        return startPosition;
    }

    public double getEndPosition() {
        return endPosition;
    }

    public Direction getDirection() {
        return direction;
    }

    public SidePosition getSidePos() {
        return sidePos;
    }

    public boolean isPoint() {
        return startPosition == endPosition;
    }

    public String getLane() {
        return lane;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Placement placement = (Placement) o;

        if (netElementId != placement.netElementId) return false;
        if (Double.compare(placement.startPosition, startPosition) != 0) return false;
        if (Double.compare(placement.endPosition, endPosition) != 0) return false;
        if (direction != placement.direction) return false;
        if (sidePos != placement.sidePos) return false;
        return !(lane != null ? !lane.equals(placement.lane) : placement.lane != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = netElementId;
        temp = Double.doubleToLongBits(startPosition);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(endPosition);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        result = 31 * result + (sidePos != null ? sidePos.hashCode() : 0);
        result = 31 * result + (lane != null ? lane.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Placement{" +
                "netElementId=" + netElementId +
                ", startPosition=" + startPosition +
                ", endPosition=" + endPosition +
                ", direction=" + direction +
                ", sidePos=" + sidePos +
                ", lane='" + lane + '\'' +
                '}';
    }
}
