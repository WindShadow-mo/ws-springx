/*
 * SPDX-License-Identifier: MIT
 * Copyright (c) 2025 莫千风
 */

package ws.spring.testdemo.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tools.jackson.core.type.TypeReference;
import ws.spring.testdemo.SpringxAppTests;
import ws.spring.testdemo.pojo.City;
import ws.spring.testdemo.util.JacksonUtils;
import ws.spring.web.entity.SingleEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

/**
 * @author WindShadow
 * @version 2025-06-01
 * @see SingleEntity
 */
@Slf4j
public class SimpleEntityTests extends SpringxAppTests {

    private final Random random = new Random();

    private String randomKey() {
        return "key" + random.nextInt(10);
    }

    private String randomValue() {
        return "value" + random.nextInt(10);
    }

    @Test
    void baseTest() {

        String simpleJson = JacksonUtils.toJson(Collections.singletonMap(randomKey(), randomValue()));
        Assertions.assertDoesNotThrow(() -> JacksonUtils.parse(simpleJson, new TypeReference<SingleEntity<Object>>() {
        }));

        City city = new City();
        city.setName(randomValue());
        city.setDesc(randomValue());
        String cityJson = JacksonUtils.toJson(Collections.singletonMap(randomKey(), city));
        Supplier<SingleEntity<City>> supplier = () -> JacksonUtils.parse(cityJson, new TypeReference<SingleEntity<City>>() {
        });
        Assertions.assertDoesNotThrow(supplier::get);
        Assertions.assertEquals(city, supplier.get().getValue());

        Map<String, Integer> map = new HashMap<>();
        map.put("numA", 1);
        map.put("numB", 2);
        String json = JacksonUtils.toJson(map);

        Exception e;
        e = Assertions.assertThrows(RuntimeException.class, () -> JacksonUtils.parse(json, new TypeReference<SingleEntity<Object>>() {
        }));
        log.info("{}", e.getMessage());

        SingleEntity<String> entity = SingleEntity.of(randomKey(), randomValue());
        String newKey = entity.getKey() + randomKey();
        Assertions.assertDoesNotThrow(() -> entity.replaceKey(newKey));
        Assertions.assertEquals(newKey, entity.getKey());
        e = Assertions.assertThrows(RuntimeException.class, () -> entity.replaceKey(null));
        log.info("{}", e.getMessage());
        e = Assertions.assertThrows(RuntimeException.class, () -> entity.replaceKey(""));
        log.info("{}", e.getMessage());

        String oldValue = entity.getValue();
        String newValue = oldValue + randomValue();
        String replaceValue = entity.replaceValue(newValue);
        Assertions.assertEquals(replaceValue, oldValue);
        Assertions.assertEquals(newValue, entity.getValue());
    }
}
