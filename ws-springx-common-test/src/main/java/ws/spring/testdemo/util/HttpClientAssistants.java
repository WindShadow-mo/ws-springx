/*
 * SPDX-License-Identifier: MIT
 * Copyright (c) 2025 莫千风
 */

package ws.spring.testdemo.util;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.DefaultClientTlsStrategy;
import org.apache.hc.client5.http.ssl.TlsSocketStrategy;
import org.apache.hc.core5.util.Timeout;
import org.jspecify.annotations.NonNull;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import ws.spring.net.TlsAssistants;

import javax.net.ssl.SSLContext;

/**
 * http请求客户端工具类
 *
 * @author WindShadow
 * @version 2022-08-28
 * @see RestTemplate
 * @see HttpComponentsClientHttpRequestFactory
 * @see HttpClient
 */

public class HttpClientAssistants {

    /**
     * 忽略认证服务端证书的http客户端请求工厂
     *
     * @return
     */
    public static HttpComponentsClientHttpRequestFactory getIgnoreAuthServerClientHttpRequestFactory() {

        return getIgnoreHostnameValidityClientHttpRequestFactory(
                TlsAssistants.createIgnoreAuthServerTlsContext());
    }

    /**
     * 忽略认证服务端证书的http客户端请求工厂，并指定客户端证书
     *
     * @param keyStoreResource 密钥库资源
     * @param keyStorePassword 密钥库密码
     * @param keyPassword      密钥密码
     * @return
     */
    public static HttpComponentsClientHttpRequestFactory getIgnoreAuthServerClientHttpRequestFactory(@NonNull String keyStoreResource, @NonNull char[] keyStorePassword, @NonNull char[] keyPassword) {

        return getIgnoreHostnameValidityClientHttpRequestFactory(
                TlsAssistants.createIgnoreAuthServerTlsContext(keyStoreResource, keyStorePassword, keyPassword));
    }

    /**
     * 获取单向认证http客户端请求工厂
     *
     * @param trustStoreResource 密钥库资源
     * @param trustStorePassword 密钥库密码
     * @return 单向认证http客户端请求工厂
     */
    public static HttpComponentsClientHttpRequestFactory getUnidirectionalAuthClientHttpRequestFactory(@NonNull String trustStoreResource, @NonNull char[] trustStorePassword) {

        return getIgnoreHostnameValidityClientHttpRequestFactory(
                TlsAssistants.createUnidirectionalAuthTlsContext(trustStoreResource, trustStorePassword));
    }

    /**
     * 获取双向认证http客户端请求工厂
     *
     * @param keyStoreResource 密钥库资源
     * @param keyStorePassword 密钥库密码
     * @param keyPassword      密钥密码
     * @param trustResource    信任密钥库资源
     * @param trustPassword    信任密钥库密码
     * @return 双向认证http客户端请求工厂
     */
    public static HttpComponentsClientHttpRequestFactory getMutualAuthClientHttpRequestFactory(@NonNull String keyStoreResource, @NonNull char[] keyStorePassword, @NonNull char[] keyPassword,
                                                                                               @NonNull String trustResource, @NonNull char[] trustPassword) {
        return getIgnoreHostnameValidityClientHttpRequestFactory(
                TlsAssistants.createMutualAuthTlsContext(keyStoreResource, keyStorePassword, keyPassword, trustResource, trustPassword));
    }

    public static HttpComponentsClientHttpRequestFactory getIgnoreHostnameValidityClientHttpRequestFactory(@NonNull SSLContext sslContext) {

        return new HttpComponentsClientHttpRequestFactory(getIgnoreHostnameValidityHttpClient(sslContext));
    }

    /**
     * 获取忽略域名校验的http客户端
     *
     * @param sslContext ssl上下文，开发者最好使用tls的安全层协议
     * @return CloseableHttpClient
     */
    public static CloseableHttpClient getIgnoreHostnameValidityHttpClient(@NonNull SSLContext sslContext) {

        Assert.notNull(sslContext, "The sslContext must not be null");
        TlsSocketStrategy tss = new DefaultClientTlsStrategy(sslContext, TlsAssistants.getSimpleHostnameVerifier());
        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setConnectTimeout(Timeout.ofSeconds(30))
                .setSocketTimeout(Timeout.ofSeconds(30))
                .build();
        PoolingHttpClientConnectionManager connManager = PoolingHttpClientConnectionManagerBuilder.create()
                .setTlsSocketStrategy(tss)
                .setDefaultConnectionConfig(connectionConfig)
                .setMaxConnTotal(200)
                .setMaxConnPerRoute(20)
                .build();
        RequestConfig requestConfig = RequestConfig.custom()
                .setResponseTimeout(Timeout.ofSeconds(30))
                .build();
        return HttpClients.custom()
                .setConnectionManager(connManager)
                .setDefaultRequestConfig(requestConfig)
                .build();
    }
}
