/*
 * SPDX-License-Identifier: MIT
 * Copyright (c) 2025 莫千风
 */

package ws.spring.validate.valueextraction;

import jakarta.validation.valueextraction.ExtractedValue;
import jakarta.validation.valueextraction.ValueExtractor;
import ws.spring.beans.SingleBean;

/**
 * @author WindShadow
 * @version 2023-07-20
 */

public class SingleBeanValueExtractor implements ValueExtractor<SingleBean<@ExtractedValue ?>> {

    static final String NODE_NAME = "<value>";

    @Override
    public void extractValues(SingleBean<?> singleBean, ValueReceiver receiver) {

        Object value = singleBean.getValue();
        receiver.value(NODE_NAME, value);
    }
}
