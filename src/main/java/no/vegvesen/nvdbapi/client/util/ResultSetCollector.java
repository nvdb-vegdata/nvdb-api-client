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

package no.vegvesen.nvdbapi.client.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * This class can be used as convenience for paging the API
 * and consuming the result.
 * @param <T> Result type
 */
public class ResultSetCollector<T> {

    private final List<T> collection = new ArrayList<>();
    private int numberOfIterations = 0;

    public ResultSetCollector() {
    }

    public List<T> getCollection() {
        return collection;
    }

    public void forEach(Consumer<? super T> action) {
        collection.forEach(action);
    }

    public Stream<T> stream() {
        return collection.stream();
    }

    public void collect(Iterator<List<T>> it) {
        while (it.hasNext()) {
            collection.addAll(it.next());
            numberOfIterations++;
        }
    }

    public int getNumberOfIterations() {
        return numberOfIterations;
    }

    public int size() {
        return collection.size();
    }

    public static <T> List<T> getAll(Iterator<List<T>> it) {
        ResultSetCollector<T> rsc = new ResultSetCollector<>();
        rsc.collect(it);
        return rsc.getCollection();
    }
}
