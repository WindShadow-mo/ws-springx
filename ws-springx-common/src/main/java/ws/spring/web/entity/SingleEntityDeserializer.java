package ws.spring.web.entity;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.databind.BeanProperty;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.deser.std.StdDeserializer;

/**
 * @author WindShadow
 * @version 2026-02-01
 */
class SingleEntityDeserializer extends StdDeserializer<SingleEntity<Object>> {

    public SingleEntityDeserializer() {
        super(SingleEntity.class);
    }

    private SingleEntityDeserializer(JavaType valueType) {
        super(valueType);
    }

    @Override
    public ValueDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {

        JavaType type = ctxt.getContextualType();
        return this.getValueType(ctxt).equals(type) ? this : new SingleEntityDeserializer(type);
    }

    @Override
    public SingleEntity<Object> deserialize(JsonParser parser, DeserializationContext ctxt) throws JacksonException {

        JavaType type = this.getValueType(ctxt);
        if (parser.isExpectedStartObjectToken()) {
            if (JsonToken.PROPERTY_NAME == parser.nextToken()) {

                String key = parser.getString();
                parser.nextToken();
                Object value;
                if (type.hasGenericTypes()) {

                    JavaType valueType = type.getBindings().getTypeParameters().getFirst();
                    value = parser.readValueAs(valueType);
                } else {
                    value = parser.readValueAs(Object.class);
                }
                if (JsonToken.END_OBJECT == parser.nextToken()) {

                    if (parser.nextToken() == null) {
                        return SingleEntity.of(key, value);
                    } else {
                        ctxt.reportWrongTokenException(type, null, "Expected end of object");
                    }
                } else {
                    ctxt.reportWrongTokenException(type, JsonToken.END_OBJECT, "Expected end of object");
                }
            } else {
                ctxt.reportWrongTokenException(type, JsonToken.PROPERTY_NAME, "Expected property name");
            }
        } else {
            ctxt.reportWrongTokenException(type, JsonToken.START_OBJECT, "Expected start of object");
        }
        // never call
        throw new UnsupportedOperationException();
    }
}
