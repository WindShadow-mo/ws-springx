/*
 * SPDX-License-Identifier: MIT
 * Copyright (c) 2025 莫千风
 */

package ws.spring.restrict.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author WindShadow
 * @version 2024-01-26
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MethodRestrictorConfiguration.class)
public @interface EnableFrequencyRestrict {
}
