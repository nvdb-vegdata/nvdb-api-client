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

package no.vegvesen.nvdbapi.client.clients;

import no.vegvesen.nvdbapi.client.model.Projection;

import java.time.LocalDate;
import java.util.Optional;

public class RefLinkRequest {

    private final long linksequenceId;
    private final double position;
    private final Projection projection;
    private final LocalDate dateFilter;

    public RefLinkRequest(long linksequenceId, double position) {
        this(linksequenceId, position, null, null);
    }

    public RefLinkRequest(long linksequenceId, double position, Projection projection) { this(linksequenceId, position, projection, null); }

    public RefLinkRequest(long linksequenceId, double position, Projection projection, LocalDate dateFilter) {
        this.linksequenceId = linksequenceId;
        this.position = position;
        this.projection = projection;
        this.dateFilter = dateFilter;
    }

    public long getLinksequenceId() {
        return linksequenceId;
    }

    public double getPosition() {
        return position;
    }

    public Optional<Projection> getProjection() {
        return Optional.ofNullable(projection);
    }

    public Optional<LocalDate> getDateFilter() { return Optional.ofNullable(dateFilter); }

    public String getQueryParam() {
        return position + "@" + linksequenceId;
    }

    @Override
    public String toString() {
        return getQueryParam();
    }
}
