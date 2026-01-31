/*
 * SPDX-License-Identifier: MIT
 * Copyright (c) 2025 莫千风
 */

package ws.spring.testdemo.web.controller.advice;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.http.converter.autoconfigure.ClientHttpMessageConvertersCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ws.spring.web.bind.FormModelResolver;
import ws.spring.web.bind.RestProxyResolver;

import java.util.List;

/**
 * @author WindShadow
 * @version 2022-07-05
 */
@Configuration
public class PracticalWebBindSupport implements WebMvcConfigurer {

    private final RestClient rest;

    public PracticalWebBindSupport(@Qualifier("clientConvertersCustomizer") ClientHttpMessageConvertersCustomizer customizer) {

        this.rest = RestClient.builder()
                .configureMessageConverters(customizer::customize)
                .build();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {

        resolvers.add(new FormModelResolver());
        resolvers.add(new RestProxyResolver(rest));
    }
}
