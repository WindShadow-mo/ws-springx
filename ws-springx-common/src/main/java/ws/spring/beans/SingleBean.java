/*
 * SPDX-License-Identifier: MIT
 * Copyright (c) 2025 莫千风
 */

package ws.spring.beans;

import org.jspecify.annotations.Nullable;

/**
 * @author WindShadow
 * @version 2023-07-20
 */

public interface SingleBean<T> {

    @Nullable
    T getValue();
}
