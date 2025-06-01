/*
 * Copyright 2015 https://github.com/openapi-processor/openapi-processor-samples
 * PDX-License-Identifier: Apache-2.0
 */

package io.openapiprocessor.samples.echo

import org.springframework.web.bind.annotation.RestController

@RestController
class EchoController: EchoApi {
    override fun getEcho(source: String): String {
        return source
    }
}
