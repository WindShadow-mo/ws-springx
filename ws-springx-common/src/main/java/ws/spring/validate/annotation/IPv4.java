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
 * @author WindShadow
 * @version 2022-09-18
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Constraint(validatedBy = {})
@Pattern(regexp = RegexpConstants.REGEXP_IP_V4)
@ReportAsSingleViolation
public @interface IPv4 {

    String message() default "{ws.spring.validate.annotation.IPv4.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
