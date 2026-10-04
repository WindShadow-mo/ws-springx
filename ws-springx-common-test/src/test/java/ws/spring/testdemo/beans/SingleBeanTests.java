/*
 * SPDX-License-Identifier: MIT
 * Copyright (c) 2025 莫千风
 */

package ws.spring.testdemo.beans;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.validation.autoconfigure.ValidationAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ws.spring.beans.DefaultSingleBean;
import ws.spring.beans.SingleBean;
import ws.spring.testdemo.pojo.Person;

import java.util.Collection;
import java.util.Collections;

/**
 * @author WindShadow
 * @version 2024-10-21
 */
@Slf4j
@SpringBootTest(classes = SingleBeanTests.Config.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class SingleBeanTests {

    @Import(SingleBeanService.class)
    @ImportAutoConfiguration(ValidationAutoConfiguration.class)
    @SpringBootConfiguration
    static class Config {}

    @Validated
    @Service
    static class SingleBeanService {

        public void consumeStringSingleBean(SingleBean<@NotBlank String> bean) {
        }

        public void consumeIntegerSingleBean(SingleBean<@Min(100) Integer> bean) {
        }

        public void consumeCollectionSingleBean(SingleBean<@NotEmpty Collection<Object>> bean) {
        }

        public void consumePojoSingleBean(SingleBean<@Valid Person> bean) {
        }
    }

    @Autowired
    private SingleBeanService sbs;

    @Test
    void validTest() {

        Exception e;

        e = Assertions.assertThrows(ConstraintViolationException.class, () -> sbs.consumeStringSingleBean(of("")));
        log.info("ConstraintViolationException: {}", e.getMessage());

        e = Assertions.assertThrows(ConstraintViolationException.class, () -> sbs.consumeIntegerSingleBean(of(10)));
        log.info("ConstraintViolationException: {}", e.getMessage());

        e = Assertions.assertThrows(ConstraintViolationException.class, () -> sbs.consumeCollectionSingleBean(of(Collections.emptySet())));
        log.info("ConstraintViolationException: {}", e.getMessage());

        e = Assertions.assertThrows(ConstraintViolationException.class, () -> sbs.consumePojoSingleBean(of(new Person("fake-email"))));
        log.info("ConstraintViolationException: {}", e.getMessage());

        Assertions.assertDoesNotThrow(() -> sbs.consumeStringSingleBean(of("abc")));
        Assertions.assertDoesNotThrow(() -> sbs.consumeIntegerSingleBean(of(100)));
        Assertions.assertDoesNotThrow(() -> sbs.consumeCollectionSingleBean(of(Collections.singleton(new Object()))));
        Assertions.assertDoesNotThrow(() -> sbs.consumePojoSingleBean(of(new Person("valid-email@ws.com"))));
    }

    private static <T> SingleBean<T> of(T obj) {
        return new DefaultSingleBean<>(obj);
    }
}