package ws.spring.web.entity;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdSerializer;

/**
 * @author WindShadow
 * @version 2026-02-01
 */
public class SingleEntitySerializer extends StdSerializer<SingleEntity<Object>> {

    public SingleEntitySerializer() {
        super(SingleEntity.class);
    }

    @Override
    public void serialize(SingleEntity<Object> value, JsonGenerator gen, SerializationContext provider) throws JacksonException {

        gen.writeStartObject();
        gen.writePOJOProperty(value.getKey(), value.getValue());
        gen.writeEndObject();
    }
}
