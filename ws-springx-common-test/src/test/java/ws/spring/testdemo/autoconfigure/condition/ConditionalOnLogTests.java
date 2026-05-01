package ws.spring.testdemo.autoconfigure.condition;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import ws.spring.testdemo.SpringxAppTests;
import ws.spring.testdemo.beans.NeedInfoBean;
import ws.spring.testdemo.beans.NeedInfoMatchIfMissBean;
import ws.spring.testdemo.beans.NeedInfoOrErrorBean;

/**
 * @author WindShadow
 * @version 2026-05-02
 */
@SpringBootTest(properties = "app.custom.log=ERROR")
public class ConditionalOnLogTests extends SpringxAppTests {

    @Autowired
    private ApplicationContext context;

    @Test
    void test() {

        Assertions.assertThrows(NoSuchBeanDefinitionException.class, () -> context.getBean(NeedInfoBean.class));
        Assertions.assertDoesNotThrow(() -> context.getBean(NeedInfoOrErrorBean.class));
        Assertions.assertDoesNotThrow(() -> context.getBean(NeedInfoMatchIfMissBean.class));
    }
}
