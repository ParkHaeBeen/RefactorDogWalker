package com.project.core.common.service;

import org.locationtech.jts.geom.*;
import org.springframework.stereotype.Component;

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
}
