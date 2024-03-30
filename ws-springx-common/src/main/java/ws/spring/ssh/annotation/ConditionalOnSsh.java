package ws.spring.ssh.annotation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.*;

/**
 * @author WindShadow
 * @version 2026-02-01
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@ConditionalOnProperty(prefix = "spring.ext.ssh", name = "enabled", havingValue = "true")
public @interface ConditionalOnSsh {
}
