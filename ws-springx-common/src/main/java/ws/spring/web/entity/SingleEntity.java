/*
 * SPDX-License-Identifier: MIT
 * Copyright (c) 2025 莫千风
 */

package ws.spring.web.entity;

import org.jspecify.annotations.Nullable;
import tools.jackson.databind.annotation.JsonDeserialize;
import ws.spring.beans.SingleBean;

import java.util.Map;

/**
 * @author WindShadow
 * @version 2025-05-30
 */
@JsonDeserialize(using = SingleEntityDeserializer.class)
public sealed interface SingleEntity<E> extends SingleBean<E> permits JacksonSingleEntity{

    String getKey();

    /**
     * @param key new key
     * @return old key
     */
    String replaceKey(String key);

    /**
     * @param value new value
     * @return old value
     */
    @Nullable
    E replaceValue(@Nullable E value);

    default Map<String,@Nullable E> toMap() {
        return Map.of(getKey(), getValue());
    }

    static <T> SingleEntity<T> of(String key, T value) {
        return new JacksonSingleEntity<>(key, value);
    }
}
