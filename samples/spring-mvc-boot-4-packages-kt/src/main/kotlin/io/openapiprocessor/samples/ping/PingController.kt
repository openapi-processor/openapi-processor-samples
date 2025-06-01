/*
 * Copyright 2015 https://github.com/openapi-processor/openapi-processor-samples
 * PDX-License-Identifier: Apache-2.0
 */

package io.openapiprocessor.samples.ping

import org.springframework.web.bind.annotation.RestController

@RestController
class PingController: PingApi {
    override fun getPing(): String {
        return "pong!"
    }
}
