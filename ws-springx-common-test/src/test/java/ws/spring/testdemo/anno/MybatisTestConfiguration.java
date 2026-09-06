package ws.spring.testdemo.anno;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisLanguageDriverAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.DataSourceInitializationAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * @author WindShadow
 * @version 2026-09-06
 */
@ImportAutoConfiguration({
        MybatisAutoConfiguration.class,
        MybatisLanguageDriverAutoConfiguration.class,
        DataSourceAutoConfiguration.class,
        DataSourceInitializationAutoConfiguration.class
})
@Configuration(proxyBeanMethods = false)
class MybatisTestConfiguration {
}
