/*
 * Copyright 2021 https://github.com/openapi-processor/openapi-processor-samples
 * PDX-License-Identifier: Apache-2.0
 */

package io.openapiprocessor.samples

import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest
class PingControllerSpec extends Specification {

    @Inject
    EmbeddedServer embeddedServer

    @Inject
    @Client("/")
    HttpClient client

    void "ping returns 'pong'"() {
        expect:
            client.toBlocking()
                    .retrieve(HttpRequest.GET('/ping')) == 'pong'
    }

}
