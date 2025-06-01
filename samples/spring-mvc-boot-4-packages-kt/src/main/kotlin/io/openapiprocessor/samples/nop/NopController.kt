/*
 * Copyright 2015 https://github.com/openapi-processor/openapi-processor-samples
 * PDX-License-Identifier: Apache-2.0
 */

package io.openapiprocessor.samples.nop

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.RestController

@RestController
class NopController: NopApi {
    val log: Logger = LoggerFactory.getLogger(this::class.java)

    override fun getNop() {
        log.info("NopController::getNop()")
    }
}
