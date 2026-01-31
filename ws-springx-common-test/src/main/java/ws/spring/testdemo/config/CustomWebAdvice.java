/*
 * SPDX-License-Identifier: MIT
 * Copyright (c) 2025 莫千风
 */

package ws.spring.testdemo.config;

import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ws.spring.testdemo.web.rest.GlobalRest;

/**
 * @author WindShadow
 * @version 2024-10-22
 */
@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class CustomWebAdvice extends ResponseEntityExceptionHandler {


    @Override
    protected @Nullable ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {

        if (statusCode.is4xxClientError()) {
            log.error("ClientError: {}", ex.getMessage());
        } else if (statusCode.is5xxServerError()) {
            log.error("ServerError: {}", ex.getMessage());
        }
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    @Override
    protected ResponseEntity<Object> createResponseEntity(@Nullable Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {

        if (body instanceof ProblemDetail detail) {
            String title = detail.getTitle();
            return super.createResponseEntity(GlobalRest.FAILED.of(title, null), headers, statusCode, request);
        } else {
            return super.createResponseEntity(body, headers, statusCode, request);
        }
    }
}
