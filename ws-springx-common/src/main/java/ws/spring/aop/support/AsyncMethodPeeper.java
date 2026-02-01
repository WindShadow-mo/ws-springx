/*
 * SPDX-License-Identifier: MIT
 * Copyright (c) 2025 莫千风
 */

package ws.spring.aop.support;

import org.jspecify.annotations.Nullable;
import org.springframework.util.Assert;
import ws.spring.aop.MethodPeeper;
import ws.spring.aop.ReturnValuePeeper;

import java.util.concurrent.Executor;
import java.util.function.Supplier;

/**
 * @author WindShadow
 * @version 2022-02-12
 */

public abstract class AsyncMethodPeeper<T> extends AbstractMethodPeeper<T> {

    private Supplier<Executor> executor;

    public AsyncMethodPeeper() {
    }

    public AsyncMethodPeeper(GlobalMethodPeekHandler globalMethodPeekHandler) {
        super(globalMethodPeekHandler);
    }

    public AsyncMethodPeeper(Supplier<Executor> executor) {
        this.executor = executor;
    }

    public AsyncMethodPeeper(GlobalMethodPeekHandler globalMethodPeekHandler, Supplier<Executor> executor) {
        super(globalMethodPeekHandler);
        this.executor = executor;
    }

    public void setExecutor(Executor executor) {
        this.executor = () -> executor;
    }

    public void setExecutor(Supplier<Executor> executor) {
        this.executor = executor;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        Assert.state(this.executor != null, "The executor is not set");
    }

    @Nullable
    protected MethodPeeper<T> getAsyncMethodPeeper(@Nullable MethodPeeper<T> methodPeeper) {

        return methodPeeper == null ?
                null : (exposurePoint, clazz, method, args) -> getAsyncMethodReturnValuePeeper(methodPeeper.peekArguments(exposurePoint, clazz, method, args));
    }

    @Nullable
    protected ReturnValuePeeper<T> getAsyncMethodReturnValuePeeper(@Nullable ReturnValuePeeper<T> returnValuePeeper) {

        return returnValuePeeper == null ?
                null : (returnValue, ex) -> this.executor.get().execute(() -> returnValuePeeper.peekReturnValue(returnValue, ex));
    }
}
