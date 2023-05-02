package no.vegvesen.nvdbapi.client.model;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static no.vegvesen.nvdbapi.client.model.Projection.*;

public class Coordinates {
    private final Projection projection;
    private final double lat_easting;
    private final double long_northing;

    private Coordinates(Projection projection, double lat_easting, double long_northing) {
        this.projection = projection;
        this.lat_easting = lat_easting;
        this.long_northing = long_northing;
    }

    public static Coordinates wgs84(double lat, double lng) {
        return new Coordinates(Projection.WGS84, lat, lng);
    }

    public static Coordinates utm33(double easting, double northing) {
        return new Coordinates(Projection.UTM33, easting, northing);
    }

    public Projection getProjection() {
        return projection;
    }

    public double getLatEasting() {
        return lat_easting;
    }

    public double getLongNorthing() {
        return long_northing;
    }

    @Override
    public String toString() {
        return lat_easting + "," + long_northing;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Double.compare(that.lat_easting, lat_easting) == 0 &&
            Double.compare(that.long_northing, long_northing) == 0 &&
            Objects.equals(projection, that.projection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projection, lat_easting, long_northing);
    }

    public static Optional<Coordinates> of(int srid, double x, double y){
        return Stream.of(UTM32, UTM33, UTM34, UTM35, WGS84).filter(p -> p.getSrid() == srid).findAny()
                .map(projection -> new Coordinates(projection, x, y));
    }
}
