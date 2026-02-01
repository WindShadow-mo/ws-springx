/*
 * SPDX-License-Identifier: MIT
 * Copyright (c) 2025 莫千风
 */

package ws.spring.aop.annotation;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;
import org.springframework.core.env.Environment;
import org.springframework.core.task.TaskExecutor;
import ws.spring.aop.MethodPeeper;
import ws.spring.aop.MethodPeeperPostProcessor;
import ws.spring.aop.support.AbortGlobalMethodPeekHandler;
import ws.spring.aop.support.AnnotationMethodPeeper;
import ws.spring.aop.support.GlobalMethodPeekHandler;
import ws.spring.context.annotation.ExactConfiguration;

/**
 * @author WindShadow
 * @version 2022-01-21
 */

@ExactConfiguration
class MethodPeekConfiguration {

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @ConditionalOnMissingBean(GlobalMethodPeekHandler.class)
    public static AbortGlobalMethodPeekHandler globalLogAdvice() {

        return new AbortGlobalMethodPeekHandler();
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @ConditionalOnMissingBean(MethodPeeper.class)
    public static AnnotationMethodPeeper<?> annotationMethodPeeper(GlobalMethodPeekHandler globalMethodPeekHandler, ObjectProvider<TaskExecutor> taskExecutor) {

        return new AnnotationMethodPeeper<>(globalMethodPeekHandler, taskExecutor::getObject);
    }

    @Bean
    @ConditionalOnMissingBean
    public static MethodPeeperPostProcessor methodPeeperPostProcessor(ObjectProvider<MethodPeeper<?>> methodPeeper, Environment environment) {

        MethodPeeperPostProcessor methodPeeperPostProcessor = new MethodPeeperPostProcessor(methodPeeper::getObject);
        boolean proxyTargetClass = environment.getProperty("spring.aop.proxy-target-class", Boolean.class, true);
        methodPeeperPostProcessor.setProxyTargetClass(proxyTargetClass);
        return methodPeeperPostProcessor;
    }
}
