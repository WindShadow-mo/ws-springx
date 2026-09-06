package ws.spring.testdemo.anno;

import com.mybatisflex.spring.boot.MybatisFlexAdminAutoConfiguration;
import com.mybatisflex.spring.boot.v4.FlexTransactionAutoConfiguration;
import com.mybatisflex.spring.boot.v4.MultiDataSourceAutoConfiguration;
import com.mybatisflex.spring.boot.v4.MybatisFlexAutoConfiguration;
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
        FlexTransactionAutoConfiguration.class,
        MultiDataSourceAutoConfiguration.class,
        MybatisFlexAutoConfiguration.class,
        MybatisFlexAdminAutoConfiguration.class,
        MybatisLanguageDriverAutoConfiguration.class,
        DataSourceAutoConfiguration.class,
        DataSourceInitializationAutoConfiguration.class
})
@Configuration(proxyBeanMethods = false)
class MybatisFlexTestConfiguration {
}
