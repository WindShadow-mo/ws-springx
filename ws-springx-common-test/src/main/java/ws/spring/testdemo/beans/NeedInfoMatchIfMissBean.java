package ws.spring.testdemo.beans;

import org.springframework.stereotype.Component;
import ws.spring.testdemo.autoconfigure.condition.ConditionalOnWay;
import ws.spring.testdemo.lang.enums.Way;

@ConditionalOnWay(value = Way.Up, matchIfMissing = true)
@Component
public class NeedInfoMatchIfMissBean {
}