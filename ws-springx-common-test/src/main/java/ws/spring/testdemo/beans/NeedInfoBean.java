package ws.spring.testdemo.beans;

import org.springframework.stereotype.Component;
import ws.spring.testdemo.autoconfigure.condition.ConditionalOnLog;
import ws.spring.testdemo.lang.enums.LogEnum;

@ConditionalOnLog(LogEnum.INFO)
@Component
public class NeedInfoBean {
}