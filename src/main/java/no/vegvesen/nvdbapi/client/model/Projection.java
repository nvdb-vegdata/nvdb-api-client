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

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class Projection implements Serializable {
    /**
     * ETRS89 / UTM zone 32 + NN2000 height
     * https://epsg.io/5972
     */
    public static final Projection UTM32 = new Projection(5972, "utm32");
    /**
     * ETRS89 / UTM zone 33 + NN2000 height
     * https://epsg.io/5973
     */
    public static final Projection UTM33 = new Projection(5973, "utm33");
    /**
     * ETRS89 / UTM zone 34 + NN2000 height
     * https://epsg.io/5974
     */
    public static final Projection UTM34 = new Projection(5974, "utm34");
    /**
     * ETRS89 / UTM zone 35 + NN2000 height
     * https://epsg.io/5975
     */
    public static final Projection UTM35 = new Projection(5975, "utm35");
    /**
     * WGS 84
     * https://epsg.io/4326
     */
    public static final Projection WGS84 = new Projection(4326, "wgs84");

    private final String alias;
    private final int srid;

    public Projection(int srid, String value) {
        this.srid = srid;
        this.alias = value;
    }

    public int getSrid() {
        return srid;
    }

    public String getAlias() {
        return alias;
    }

    @Override
    public String toString() {
        return "Projection{" +
                "alias='" + alias + '\'' +
                ", srid=" + srid +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Projection that = (Projection) o;
        return srid == that.srid &&
                Objects.equals(alias, that.alias);
    }

    @Override
    public int hashCode() {

        return Objects.hash(alias, srid);
    }

    public static Optional<Projection> of(int srid) {
        return Stream.of(UTM32, UTM33, UTM34, UTM35, WGS84).filter(p -> p.getSrid() == srid).findAny();
    }
}
