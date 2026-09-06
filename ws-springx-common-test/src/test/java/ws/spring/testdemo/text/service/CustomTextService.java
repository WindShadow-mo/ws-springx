package ws.spring.testdemo.text.service;

import ws.spring.text.annotation.SqlEscape;

public interface CustomTextService {

    String processMissEscape(@SqlEscape String value);
}