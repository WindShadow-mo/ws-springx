/*
 * SPDX-License-Identifier: MIT
 * Copyright (c) 2025 莫千风
 */

package ws.spring.web.entity;

import org.jspecify.annotations.Nullable;
import tools.jackson.databind.annotation.JsonSerialize;

/**
 * @author WindShadow
 * @version 2026-02-01
 */
@JsonSerialize(using = SingleEntitySerializer.class)
final class JacksonSingleEntity<T> implements SingleEntity<T> {

    private String key;

    @Nullable
    private T value;

    JacksonSingleEntity(String key, @Nullable T value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String replaceKey(String key) {

        if (key == null || key.isEmpty())
            throw new IllegalArgumentException("The key of SingleEntity must not be empty/null");
        String oldKey = this.key;
        this.key = key;
        return oldKey;
    }

    @Override
    public T replaceValue(@Nullable T value) {

        T oldValue = this.value;
        this.value = value;
        return oldValue;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.key + " = " + value;
    }
}
