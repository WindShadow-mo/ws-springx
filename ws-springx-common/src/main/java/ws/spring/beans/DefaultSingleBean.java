/*
 * SPDX-License-Identifier: MIT
 * Copyright (c) 2025 莫千风
 */

package ws.spring.beans;

import org.jspecify.annotations.Nullable;

/**
 * @author WindShadow
 * @version 2023-07-21
 */

public class DefaultSingleBean<T> implements SingleBean<T> {

    @Nullable
    private T value;

    public DefaultSingleBean() {
    }

    public DefaultSingleBean(@Nullable T value) {
        this.value = value;
    }

    @Nullable
    @Override
    public T getValue() {
        return value;
    }

    public void setValue(@Nullable T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "DefaultSingleBean{" +
                "value=" + value +
                '}';
    }
}
