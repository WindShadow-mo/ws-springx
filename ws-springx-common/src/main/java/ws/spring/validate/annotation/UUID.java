/*
 * SPDX-License-Identifier: MIT
 * Copyright (c) 2025 莫千风
 */

package ws.spring.validate.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.Pattern;
import ws.spring.constant.RegexpConstants;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * 约束字符序列为UUID
 *
 * @author WindShadow
 * @version 2022-08-27
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Constraint(validatedBy = {})
@Pattern(regexp = RegexpConstants.REGEXP_UUID)
@ReportAsSingleViolation
public @interface UUID {

    String message() default "{ws.spring.validate.annotation.UUID.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
