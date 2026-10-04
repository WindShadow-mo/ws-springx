package ws.spring.testdemo.text.service;

import lombok.extern.slf4j.Slf4j;
import ws.spring.text.annotation.SqlEscape;

@Slf4j
public class CustomTextServiceImpl implements CustomTextService {

    public String processSqlValue(@SqlEscape String value) {
        log.info("value: {}", value);
        return value;
    }

    public String processObjectValue(@SqlEscape Object value) {
        log.info("value: {}", value);
        return String.valueOf(value);
    }

    public String overrideProcessValue(@SqlEscape String value) {
        log.info("value: {}", value);
        return value;
    }

    @Override
    public String processMissEscape(String value) {
        log.info("value: {}", value);
        return value;
    }
}