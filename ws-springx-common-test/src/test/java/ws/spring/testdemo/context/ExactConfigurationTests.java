/*
 * SPDX-License-Identifier: MIT
 * Copyright (c) 2025 莫千风
 */

package ws.spring.testdemo.context;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import ws.spring.testdemo.context.example.CustomConfigurationForImport;
import ws.spring.testdemo.context.example.CustomConfigurationForScan;

/**
 * @author WindShadow
 * @version 2025-06-23
 */
@SpringBootTest(classes = ExactConfigurationTests.Config.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ExactConfigurationTests {

    @Import(CustomConfigurationForImport.class)
    @SpringBootApplication(scanBasePackages = "ws.spring.testdemo.context.example")
    static class Config {}

    @Autowired
    private ApplicationContext context;

    @Test
    void componentScanTest() {

        Assertions.assertTrue(context.getBeansOfType(CustomConfigurationForScan.class).isEmpty());
        Assertions.assertFalse(context.getBeansOfType(CustomConfigurationForImport.class).isEmpty());
    }
}
