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

package no.vegvesen.nvdbapi.client.model;

import java.util.Objects;
import java.util.Optional;

public final class Page {
    private static final Page DEFAULT = new Page(1000, Optional.empty());
    private final int count;
    private final Optional<String> start;

    private Page(Integer count, String start) {
        this.count = Objects.requireNonNull(count, "Missing count argument!");
        this.start = Optional.ofNullable(start);
    }

    private Page(Integer count, Optional<String> start) {
        this.count = Objects.requireNonNull(count, "Missing count argument!");
        this.start = start;
    }

    public static Page defaults() {
        return DEFAULT;
    }

    public static Page count(Integer count) {
        Objects.requireNonNull(count, "Missing count argument!");
        if (count < 1) {
            throw new IllegalArgumentException("Count must be greater than 0.");
        }
        return new Page(count, Optional.empty());
    }

    public Optional<String> getStart() {
        return start;
    }

    public Integer getCount() {
        return count;
    }

    public Page withStart(String start) {
        return new Page(count, start);
    }

    public Page withCount(Integer count) {
        return new Page(count, start);
    }
}
