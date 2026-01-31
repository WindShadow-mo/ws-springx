/*
 * SPDX-License-Identifier: MIT
 * Copyright (c) 2025 莫千风
 */

package ws.spring.testdemo.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;


public class JacksonUtils {

    private static final JsonMapper JSON_MAPPER = JsonMapper.builderWithJackson2Defaults()
            .changeDefaultPropertyInclusion(value -> value.withValueInclusion(JsonInclude.Include.NON_NULL))
            .build();

    public static <T> String toJson(T obj) {

        return toJsonWithMapper(JSON_MAPPER, obj);
    }

    public static <T> T parse(String json, Class<T> cla) {

        return parseWithMapper(JSON_MAPPER, json, cla);
    }

    public static <T> T parse(String json, TypeReference<T> trf) {

        return parseWithMapper(JSON_MAPPER, json, trf);
    }

    public static <T> String toJsonWithMapper(JsonMapper mapper, T obj) {

        try {
            return mapper.writeValueAsString(obj);
        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T parseWithMapper(JsonMapper mapper, String json, Class<T> cla) {

        try {
            return mapper.readValue(json, cla);
        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T parseWithMapper(JsonMapper mapper, String json, TypeReference<T> trf) {

        try {
            return mapper.readValue(json, trf);
        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
    }
}
