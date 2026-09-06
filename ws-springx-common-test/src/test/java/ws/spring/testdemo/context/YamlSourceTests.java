/*
 * SPDX-License-Identifier: MIT
 * Copyright (c) 2025 莫千风
 */

package ws.spring.testdemo.context;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;
import ws.spring.context.annotation.YamlSource;

/**
 * @author WindShadow
 * @version 2024-10-24
 */
@SpringBootTest(classes = YamlSourceTests.BaseConfig.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class YamlSourceTests {

    @YamlSource("classpath:custom-config.yml")
    @SpringBootApplication
    static class BaseConfig {
    }

    @Value("${app.test.yaml-source.custom-value:}")
    private String customValue;

    @Test
    void loadYamlSourceTest() {
        Assertions.assertTrue(StringUtils.hasText(customValue));
    }
}
