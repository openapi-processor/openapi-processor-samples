/*
 * Copyright 2026 https://github.com/openapi-processor/openapi-processor-samples
 * PDX-License-Identifier: Apache-2.0
 */

package io.openapiprocessor.samples.echo.server

import io.openapiprocessor.openapi.server.api.EchoApi
import org.springframework.web.bind.annotation.RestController

@RestController
class EchoController: EchoApi {
    override fun postEcho(reverse: Boolean, body: Echo): Echo {
        return if (reverse) {
            body.reverse()
        } else {
            body
        }
    }
}
