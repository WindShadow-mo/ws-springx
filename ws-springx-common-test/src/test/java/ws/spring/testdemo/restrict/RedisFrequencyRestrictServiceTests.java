/*
 * SPDX-License-Identifier: MIT
 * Copyright (c) 2025 莫千风
 */

package ws.spring.testdemo.restrict;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.data.redis.autoconfigure.DataRedisAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import ws.spring.restrict.FrequencyRestrictService;
import ws.spring.restrict.support.RedisFrequencyRestrictService;
import ws.spring.testdemo.anno.EnableEmbeddedRedis;

/**
 * @author WindShadow
 * @version 2024-10-17
 */
@ActiveProfiles("redis")
@SpringBootTest(
        classes = RedisFrequencyRestrictServiceTests.Config.class,
        properties = {"app.frequency-restrictor=redis"},
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class RedisFrequencyRestrictServiceTests extends FrequencyRestrictServiceTests {

    @EnableEmbeddedRedis
    @Import(FrequencyRestrictConfig.class)
    @ImportAutoConfiguration(DataRedisAutoConfiguration.class)
    @SpringBootConfiguration
    static class Config {}

    @Autowired
    private RedisFrequencyRestrictService restrictService;

    @Override
    protected FrequencyRestrictService createFrequencyRestrictService() {
        return restrictService;
    }

    @Override
    protected long calculateRunDuration(long durationSeconds) {

        // 访问redis可能存在网络延迟，适当加1秒执行时间
        return durationSeconds + 1L;
    }
}
