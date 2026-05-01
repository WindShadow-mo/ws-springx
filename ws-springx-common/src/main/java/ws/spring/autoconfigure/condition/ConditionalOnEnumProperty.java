package ws.spring.autoconfigure.condition;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * 枚举类型条件装配注解
 * <p>
 * 需要结合自定义枚举使用，将自定义枚举称为实际注解，示例：
 * <pre>{@snippet :
 * 
 * @Retention(RetentionPolicy.RUNTIME)
 * @Target({ElementType.TYPE, ElementType.METHOD})
 * @Documented
 * @ConditionalOnEnumProperty(key = "app.custom.log", enumType = LogEnum.class, enumValueField = "value")
 * public @interface ConditionalOnLog {
 *
 *     LogEnum[] value();
 * }
 *
 * }</pre>
 * </p>
 *
 *
 * @author WindShadow
 * @version 2026-05-01
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
@Documented
@Conditional(OnEnumProperty.class)
public @interface ConditionalOnEnumProperty {

    /**
     * 配置key
     */
    String key();

    /**
     * 枚举类型
     */
    Class<? extends Enum<?>> enumType();

    /**
     * 实际注解预期包含的枚举值的字段
     */
    String enumValueField();

    /**
     * 未查找到配置key时的匹配结果
     */
    boolean matchIfMissing() default false;
}
