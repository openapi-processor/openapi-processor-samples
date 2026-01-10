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
class EchoControllerSpec extends Specification {

    @Inject
    EmbeddedServer embeddedServer

    @Inject
    @Client("/")
    HttpClient client

    void "echo returns 'pong'"() {
        def req = HttpRequest.GET('/echo')
        req.parameters.add ('source', 'pong')

        expect:
            client.toBlocking()
                    .retrieve(req) == 'pong'
    }

}
