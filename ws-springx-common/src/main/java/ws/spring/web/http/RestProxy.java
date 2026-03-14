/*
 * SPDX-License-Identifier: MIT
 * Copyright (c) 2025 莫千风
 */

package ws.spring.web.http;

import org.jspecify.annotations.Nullable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * REST 代理接口，用于构建和执行 HTTP 请求。
 * <p>
 * <strong>设计说明：</strong>
 * <p>
 * 本接口采用"一次性发送"设计，即代理对象在调用任意 {@code proxy()} 方法后会被标记为"已发送"状态。
 * 这是为了确保请求的不可变性，防止意外重复使用已配置的代理对象。
 * <p>
 * <strong>重要注意事项：</strong>
 * <ul>
 *   <li>调用任意 {@code proxy()} 方法后（无论请求成功或失败），{@link #isSend()} 将返回 {@code true}</li>
 *   <li>在"已发送"状态下调用任何配置方法（如 {@code addHeader}、{@code replaceQueryParam} 等）将抛出 {@link IllegalStateException}</li>
 *   <li>若需重试请求，请创建新的代理实例</li>
 * </ul>
 *
 * @author WindShadow
 * @version 2025-07-14
 */
public interface RestProxy {

    void addHeader(String header, String value);

    // ~ opt
    // ==================================

    void removeHeader(String header);

    default void removeHeaders(List<String> headers) {
        headers.forEach(this::removeHeader);
    }

    default void replaceHeader(String header, String value) {

        removeHeader(header);
        addHeader(header, value);
    }

    default void replaceHeader(String header, List<String> values) {

        removeHeader(header);
        values.forEach(value -> addHeader(header, value));
    }

    void resetHeaders(HttpHeaders headers);

    void removeQueryParam(String name);

    default void removeQueryParams(List<String> names) {
        names.forEach(this::removeQueryParam);
    }

    void replaceQueryParam(String name, String value);

    void replaceQueryParam(String name, List<String> values);

    void resetQueryParams(MultiValueMap<String, String> params);

    void resetQueryParams(Map<String, String> params);

    void replaceMethod(HttpMethod method);

    default void replaceBody(Object body) {
        replaceBody(() -> body);
    }

    void replaceBody(RequestBodyFetcher<Object> bodySupplier);

    void replacePath(@Nullable String path);

    boolean isSend();

    // ~ proxy
    // ==================================

    /**
     * @param origin 如 http://127.0.0.1:8080
     * @param type
     * @param <T>
     * @return
     */
    <T> ResponseEntity<T> proxy(String origin, Class<T> type);

    <T> ResponseEntity<T> proxy(String origin, ParameterizedTypeReference<T> typeRef);

    <T> ResponseEntity<T> proxy(URI uri, Class<T> type);

    <T> ResponseEntity<T> proxy(URI uri, ParameterizedTypeReference<T> typeRef);

    <T> ResponseEntity<T> proxy(String host, int port, Class<T> type);

    <T> ResponseEntity<T> proxy(String host, int port, ParameterizedTypeReference<T> typeRef);

    <T> ResponseEntity<T> proxy(String scheme, String host, int port, Class<T> type);

    /**
     * @param scheme  http或https
     * @param host    如 127.0.0.1或localhost
     * @param port    如 8080
     * @param typeRef
     * @param <T>
     * @return
     */
    <T> ResponseEntity<T> proxy(String scheme, String host, int port, ParameterizedTypeReference<T> typeRef);

    @FunctionalInterface
    interface RequestBodyFetcher<T> {

        T getBody() throws IOException;
    }
}