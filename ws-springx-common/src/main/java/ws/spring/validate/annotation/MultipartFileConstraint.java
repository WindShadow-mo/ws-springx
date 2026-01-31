/*
 * SPDX-License-Identifier: MIT
 * Copyright (c) 2025 莫千风
 */

package ws.spring.validate.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ws.spring.validate.MultipartFileConstraintValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * @author WindShadow
 * @version 2022-11-22
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Constraint(validatedBy = {MultipartFileConstraintValidator.class})
@Documented
public @interface MultipartFileConstraint {

    long UNLIMITED_SIZE = -1L;

    String[] contentTypes() default {};

    boolean allowNullContentType() default false;

    long maxSize() default UNLIMITED_SIZE;

    String message() default "{ws.spring.validate.annotation.MultipartFileConstraint.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
