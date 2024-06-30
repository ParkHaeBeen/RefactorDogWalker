package com.project.walker.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.locationtech.jts.geom.Point;
import java.io.IOException;

public class PointSerializer extends StdSerializer<Point> {

    public PointSerializer(Class<Point> t) {
        super(t);
    }

    @Override
    public void serialize(Point point, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("lat", point.getCoordinate().getX() + "");
        jsonGenerator.writeStringField("lnt", point.getCoordinate().getY() + "");
        jsonGenerator.writeEndObject();
    }
}
