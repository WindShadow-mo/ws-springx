/*
 * SPDX-License-Identifier: MIT
 * Copyright (c) 2025 莫千风
 */

package ws.spring.restrict.support;

import org.springframework.data.redis.core.RedisOperations;
import ws.spring.restrict.FrequencyRestrictor;
import ws.spring.restrict.FrequencyRestrictorDefinition;
import ws.spring.restrict.RestrictorDeclarationException;

/**
 * 基于 Redis ZSet + Lua 脚本实现的分布式频控服务
 * <p>
 * 使用滑动窗口算法，支持真正的分布式限流
 *
 * @author WindShadow
 * @version 2026-03-15
 */
public class RedisFrequencyRestrictService extends GenericFrequencyRestrictService {

    private final RedisOperations<String, String> redisOperations;

    public RedisFrequencyRestrictService(RedisOperations<String, String> redisOperations) {
        this.redisOperations = redisOperations;
    }

    @Override
    protected FrequencyRestrictor createRestrictor(FrequencyRestrictorDefinition definition) throws RestrictorDeclarationException {
        return new RedisSlidingWindowRestrictor(definition, redisOperations);
    }
}
