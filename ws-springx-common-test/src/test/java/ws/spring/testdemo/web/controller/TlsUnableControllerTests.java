/*
 * SPDX-License-Identifier: MIT
 * Copyright (c) 2025 莫千风
 */

package ws.spring.testdemo.web.controller;

import org.springframework.boot.web.server.Ssl;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClient;
import ws.spring.testdemo.util.HttpClientAssistants;


/**
 * @author WindShadow
 * @version 2022-10-02
 */
@ActiveProfiles({"ssl_unable"})
public class TlsUnableControllerTests extends BaseTlsControllerTests {

    @Override
    protected void doTlsTest(boolean tls, boolean need, Ssl ssl, RestClient.Builder builder) {

        doHttpTest(tls, true, builder.build());

        builder.requestFactory(HttpClientAssistants.getMutualAuthClientHttpRequestFactory(
                ssl.getKeyStore(), ssl.getKeyStorePassword().toCharArray(), ssl.getKeyPassword().toCharArray(),
                ssl.getTrustStore(), ssl.getTrustStorePassword().toCharArray()));

        doHttpTest(tls, true, builder.build());
        builder.requestFactory(HttpClientAssistants.getUnidirectionalAuthClientHttpRequestFactory(ssl.getTrustStore(), ssl.getTrustStorePassword().toCharArray()));
        doHttpTest(tls, true, builder.build());

        builder.requestFactory(HttpClientAssistants.getIgnoreAuthServerClientHttpRequestFactory(ssl.getKeyStore(), ssl.getKeyStorePassword().toCharArray(), ssl.getKeyPassword().toCharArray()));
        doHttpTest(tls, true, builder.build());

        builder.requestFactory(HttpClientAssistants.getIgnoreAuthServerClientHttpRequestFactory());
        doHttpTest(tls, true, builder.build());
    }
}
