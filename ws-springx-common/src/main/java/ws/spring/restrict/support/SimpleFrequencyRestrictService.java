/*
 * SPDX-License-Identifier: MIT
 * Copyright (c) 2025 莫千风
 */

package ws.spring.restrict.support;

import ws.spring.restrict.FrequencyRestrictor;
import ws.spring.restrict.FrequencyRestrictorDefinition;
import ws.spring.restrict.RestrictorDeclarationException;

/**
 * 频控服务一种简单实现
 *
 * @author WindShadow
 * @version 2024-01-26
 */

public class SimpleFrequencyRestrictService extends GenericFrequencyRestrictService {

    @Override
    protected FrequencyRestrictor createRestrictor(FrequencyRestrictorDefinition definition) throws RestrictorDeclarationException {
        return new BlockingFrequencyRestrictor(definition);
    }
}
