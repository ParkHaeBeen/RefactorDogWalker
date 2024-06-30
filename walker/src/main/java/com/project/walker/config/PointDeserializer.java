package com.project.walker.config;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.project.core.common.service.LocationUtil;
import org.locationtech.jts.geom.Point;

import java.io.IOException;

public class PointDeserializer extends StdDeserializer<Point> {
    public PointDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Point deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        double lat = node.get("lat").asDouble();
        double lnt = node.get("lnt").asDouble();
        return LocationUtil.createPoint(lat, lnt);
    }
}
