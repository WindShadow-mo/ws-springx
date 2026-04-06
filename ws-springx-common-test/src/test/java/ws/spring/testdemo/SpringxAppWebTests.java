/*
 * SPDX-License-Identifier: MIT
 * Copyright (c) 2025 莫千风
 */

package ws.spring.testdemo;

import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import tools.jackson.core.type.TypeReference;
import ws.spring.testdemo.util.JacksonUtils;
import ws.spring.testdemo.web.rest.GlobalRest;
import ws.spring.testdemo.web.rest.response.RestResponse;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author WindShadow
 * @version 2023-06-06
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class SpringxAppWebTests implements ServletContextAware {

    protected MockMvc mvc;

    @Override
    public void setServletContext(ServletContext servletContext) {

        WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        Assert.state(wac != null, "WebApplicationContext not found");
        this.mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    protected final <T> T request(RequestBuilder builder) {

        return request(builder, status().is2xxSuccessful());
    }

    protected final <T> T request(RequestBuilder builder, ResultMatcher matcher) {

        String json = requestString(builder, matcher);
        RestResponse<T> restResponse = JacksonUtils.parse(json, new TypeReference<RestResponse<T>>() {
        });
        Assertions.assertEquals(GlobalRest.SUCCESS.getCode(), restResponse.getCode());
        return restResponse.getData();
    }

    protected final String requestString(RequestBuilder builder) {

        return requestString(builder, status().is2xxSuccessful());
    }

    protected final String requestString(RequestBuilder builder, ResultMatcher matcher) {

        return new String(requestBytes(builder, matcher), StandardCharsets.UTF_8);
    }

    protected final byte[] requestBytes(RequestBuilder builder) {

        return requestBytes(builder, status().is2xxSuccessful());
    }

    protected final byte[] requestBytes(RequestBuilder builder, ResultMatcher matcher) {

        try {

            if (MockHttpServletRequestBuilder.class == builder.getClass()) {

                MockHttpServletRequestBuilder mockBuilder = (MockHttpServletRequestBuilder) builder;
                mockBuilder.characterEncoding(StandardCharsets.UTF_8.name());
                mockBuilder.contentType(MediaType.APPLICATION_JSON);
            }
            return mvc.perform(builder)
                    .andExpect(matcher)
                    .andReturn()
                    .getResponse()
                    .getContentAsByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
