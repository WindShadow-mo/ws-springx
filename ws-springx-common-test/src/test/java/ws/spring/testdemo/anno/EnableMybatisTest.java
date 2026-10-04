package ws.spring.testdemo.anno;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author WindShadow
 * @version 2026-09-06
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MybatisTestConfiguration.class)
public @interface EnableMybatisTest {
}
