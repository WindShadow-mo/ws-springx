package ws.spring.testdemo.autoconfigure.condition;

import ws.spring.autoconfigure.condition.ConditionalOnEnumProperty;
import ws.spring.testdemo.lang.enums.LogEnum;

import java.lang.annotation.*;

/**
 * @author WindShadow
 * @version 2026-05-02
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@ConditionalOnEnumProperty(key = "app.custom.log", enumType = LogEnum.class, enumValueField = "value")
public @interface ConditionalOnLog {

    LogEnum[] value();
}
