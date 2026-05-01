package ws.spring.testdemo.autoconfigure.condition;

import org.springframework.core.annotation.AliasFor;
import ws.spring.autoconfigure.condition.ConditionalOnEnumProperty;
import ws.spring.testdemo.lang.enums.Way;

import java.lang.annotation.*;

/**
 * @author WindShadow
 * @version 2026-05-02
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@ConditionalOnEnumProperty(key = "app.custom.way", enumType = Way.class, enumValueField = "value")
public @interface ConditionalOnWay {

    Way[] value();

    @AliasFor(annotation = ConditionalOnEnumProperty.class)
    boolean matchIfMissing() default false;
}
