/*
 * SPDX-License-Identifier: MIT
 * Copyright (c) 2025 莫千风
 */

package ws.spring.restrict.support;

import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import ws.spring.restrict.FrequencyRestrictorDefinition;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

/**
 * 基于 Redis ZSet + Lua 脚本实现的分布式滑动窗口限流器
 * <p>
 * 核心原理：
 * 1. 使用 ZSet 存储请求记录，Score 为时间戳，Member 为请求唯一 ID
 * 2. 通过 Lua 脚本保证原子性：删除过期数据 → 统计窗口内数量 → 判断是否限流
 * 3. 滑动窗口：任意时刻统计 [now - window, now] 时间范围内的请求数
 *
 * @author WindShadow
 * @version 2026-03-15
 */
class RedisSlidingWindowRestrictor extends GenericFrequencyRestrictor {

    private static final String KEY_FORMAT = "$Restrictor:%s::%s";

    /**
     * 限流 Lua 脚本
     */
    private static final RedisScript<Long> LUA_SCRIPT;

    static {
        String script = """
                local key = KEYS[1]
                local windowMs = tonumber(ARGV[1])
                local maxCount = tonumber(ARGV[2])
                local now = tonumber(ARGV[3])
                local randomValue = ARGV[4]
                
                local windowStart = now - windowMs
                
                -- 删除过期数据
                redis.call('ZREMRANGEBYSCORE', key, 0, windowStart)
                
                -- 统计当前窗口请求数
                local count = redis.call('ZCOUNT', key, windowStart, now)
                
                if count < maxCount then
                    redis.call('ZADD', key, now, randomValue)
                    redis.call('EXPIRE', key, math.ceil(windowMs / 1000) + 1)
                    return 0  -- 允许
                else
                    return 1  -- 拒绝
                end
                """;
        LUA_SCRIPT = new DefaultRedisScript<>(script, Long.class);
    }

    private final String redisPatten;

    /**
     * Redis 操作对象
     */
    private final RedisOperations<String, String> redisOperations;

    /**
     * 构造方法
     *
     * @param definition      限流器定义
     * @param redisOperations Redis 操作对象
     */
    public RedisSlidingWindowRestrictor(FrequencyRestrictorDefinition definition,
                                        RedisOperations<String, String> redisOperations) {
        super(definition);
        this.redisOperations = redisOperations;
        this.redisPatten = String.format(KEY_FORMAT, this.getName(), "*");
    }

    /**
     * 生成 Redis Key
     *
     * @param refer 引用标识
     * @return Redis Key
     */
    private String buildRedisKey(String refer) {
        return String.format(KEY_FORMAT, this.getName(), refer);
    }

    @Override
    protected boolean doTryRestrict(String refer) {

        String key = buildRedisKey(refer);
        String randomValue = UUID.randomUUID().toString();
        long now = System.currentTimeMillis();

        Long result = redisOperations.execute(
                LUA_SCRIPT,
                Collections.singletonList(key),
                String.valueOf(durationMillis),
                String.valueOf(frequency),
                String.valueOf(now),
                randomValue
        );

        // 返回 1 表示被限流，0 表示允许
        return result != null && result == 1;
    }

    @Override
    protected void doResetRestrict(String refer) {

        String key = buildRedisKey(refer);
        redisOperations.delete(key);
    }

    @Override
    public void resetRestrictor() {

        Set<String> keys = redisOperations.keys(this.redisPatten);
        if (keys != null && !keys.isEmpty()) {
            redisOperations.delete(keys);
        }
    }
}
