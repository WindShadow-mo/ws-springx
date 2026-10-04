package ws.spring.testdemo.text.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ws.spring.text.annotation.SqlEscape;

@Slf4j
@Service
public class MainCustomTextServiceImpl extends CustomTextServiceImpl {

    @Override
    public String overrideProcessValue(String value) {
        return super.processSqlValue(value);
    }

    public String processMainValue(@SqlEscape String value) {
        log.info("value: {}", value);
        return String.valueOf(value);
    }
}