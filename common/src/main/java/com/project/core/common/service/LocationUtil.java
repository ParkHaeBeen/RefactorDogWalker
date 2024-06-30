package com.project.core.common.service;

import com.project.core.common.service.dto.Location;
import org.locationtech.jts.geom.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LocationUtil {

    public static Point createPoint(final Double latitude, final Double longitude) {
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        Coordinate coordinate = new Coordinate(longitude, latitude);
        return geometryFactory.createPoint(coordinate);
    }

    public static MultiPoint createLineString(final List<Point> routes) {
        final GeometryFactory geometryFactory = new GeometryFactory();
        return geometryFactory.createMultiPoint(routes.toArray(new Point[0]));
    }

    public static List<Location> createLocation(final String lineString){
        final List<Location> coordinates = new ArrayList<>();

        final String[] points = lineString
                .replace("lineString = MULTIPOINT ((","")
                .replace("MULTIPOINT ","")
                .replace(")","")
                .replace("(","")
                .trim()
                .split(",");

        for (String point : points) {
            final String[] parts = point.trim().split("\\s");
            coordinates.add(new Location(Double.parseDouble(parts[0]), Double.parseDouble(parts[1])));
        }
        return coordinates;
    }
}
