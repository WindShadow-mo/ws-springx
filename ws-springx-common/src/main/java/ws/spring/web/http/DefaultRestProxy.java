/*
 * SPDX-License-Identifier: MIT
 * Copyright (c) 2025 莫千风
 */

package ws.spring.web.http;

import org.jspecify.annotations.Nullable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Objects;

/**
 * @author WindShadow
 * @version 2025-07-15
 */
public class DefaultRestProxy extends AbstractRestProxy {

    private final RestClient rest;

    public DefaultRestProxy(HttpMethod method, HttpHeaders headers, URI uri, @Nullable RequestBodyFetcher<?> bodySupplier, RestClient rest) {
        super(method, headers, uri, bodySupplier);
        this.rest = Objects.requireNonNull(rest);
    }

    public DefaultRestProxy(HttpMethod method, HttpHeaders headers, UriComponentsBuilder builder, @Nullable RequestBodyFetcher<?> bodySupplier, RestClient rest) {
        super(method, headers, builder, bodySupplier);
        this.rest = Objects.requireNonNull(rest);
    }

    @Override
    protected <T> ResponseEntity<T> doProxy(RequestEntity<Object> entity, Class<T> type) {

        return rest.method(entity.getMethod())
                .uri(entity.getUrl())
                .headers(httpHeaders -> httpHeaders.putAll(entity.getHeaders()))
                .body(entity.getBody())
                .retrieve()
                .toEntity(type);
    }

    @Override
    protected <T> ResponseEntity<T> doProxy(RequestEntity<Object> entity, ParameterizedTypeReference<T> typeRef) {

        return rest.method(entity.getMethod())
                .uri(entity.getUrl())
                .headers(httpHeaders -> httpHeaders.putAll(entity.getHeaders()))
                .body(entity.getBody())
                .retrieve()
                .toEntity(typeRef);
    }
}
