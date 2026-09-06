package ws.spring.testdemo.autoconfigure.condition;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import ws.spring.testdemo.lang.enums.LogEnum;
import ws.spring.testdemo.lang.enums.Way;

/**
 * @author WindShadow
 * @version 2026-05-02
 */
@SpringBootTest(classes = ConditionalOnLogTests.Config.class, properties = "app.custom.log=ERROR", webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ConditionalOnLogTests {

    @Import({NeedInfoBean.class, NeedInfoOrErrorBean.class, NeedInfoMatchIfMissBean.class})
    @SpringBootConfiguration
    static class Config {
    }

    @ConditionalOnLog(LogEnum.INFO)
    @Component
    static class NeedInfoBean {
    }

    @ConditionalOnWay(value = Way.Up, matchIfMissing = true)
    @Component
    static class NeedInfoMatchIfMissBean {
    }

    @ConditionalOnLog({LogEnum.INFO, LogEnum.ERROR})
    @Component
    static class NeedInfoOrErrorBean {
    }

    @Autowired
    private ApplicationContext context;

    @Test
    void test() {

        Assertions.assertThrows(NoSuchBeanDefinitionException.class, () -> context.getBean(NeedInfoBean.class));
        Assertions.assertDoesNotThrow(() -> context.getBean(NeedInfoOrErrorBean.class));
        Assertions.assertDoesNotThrow(() -> context.getBean(NeedInfoMatchIfMissBean.class));
    }
}
