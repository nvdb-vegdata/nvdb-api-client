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

package no.vegvesen.nvdbapi.client.model.roadobjects;

import no.vegvesen.nvdbapi.client.model.Direction;
import no.vegvesen.nvdbapi.client.model.HeightLevel;
import no.vegvesen.nvdbapi.client.model.SidePosition;

import java.util.List;
import java.util.Objects;

public class Placement {
    private final int netElementId;
    private final double startPosition;
    private final double endPosition;
    private final Direction direction;
    private final SidePosition sidePos;
    private final HeightLevel heightLevel;
    private final List<String> lane;

    public Placement(int netElementId,
                     double startPosition,
                     double endPosition,
                     Direction direction,
                     SidePosition sidePos,
                     HeightLevel heightLevel,
                     List<String> lane) {
        this.netElementId = netElementId;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.direction = direction;
        this.sidePos = sidePos;
        this.heightLevel = heightLevel;
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

    public List<String> getLane() {
        return lane;
    }

    public HeightLevel getHeightLevel() {
        return heightLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Placement placement = (Placement) o;
        return netElementId == placement.netElementId &&
                Double.compare(placement.startPosition, startPosition) == 0 &&
                Double.compare(placement.endPosition, endPosition) == 0 &&
                direction == placement.direction &&
                sidePos == placement.sidePos &&
                heightLevel == placement.heightLevel &&
                Objects.equals(lane, placement.lane);
    }

    @Override
    public int hashCode() {
        return Objects.hash(netElementId, startPosition, endPosition, direction, sidePos, heightLevel, lane);
    }

    @Override
    public String toString() {
        return "Placement{" +
                "netElementId=" + netElementId +
                ", startPosition=" + startPosition +
                ", endPosition=" + endPosition +
                ", direction=" + direction +
                ", sidePos=" + sidePos +
                ", heightLevel=" + heightLevel +
                ", lane=" + lane +
                '}';
    }
}
