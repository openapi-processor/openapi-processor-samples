/*
 * Copyright 2025 https://github.com/openapi-processor/openapi-processor-samples
 * PDX-License-Identifier: Apache-2.0
 */

package io.openapiprocessor.samples.sse

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import tools.jackson.databind.ObjectMapper
import java.util.concurrent.Executors

@RestController
class SseController(val mapper: ObjectMapper): SseApi {
    class Foo(val value: String)

    override fun getSse(source: String?): SseEmitter {
        val emitter = SseEmitter()
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            try {
                // some data
                val items = (1..10)
                    .toList()
                    .map { Foo("foo $it") }

                for (i in items.indices) {
                    val item = items[i]

                    // to dto
                    val dto = SseGetResponse200(item.value)
                    val json = mapper.writeValueAsString(dto)

                    // send it
                    val event = SseEmitter.event()
                        .data(json)
                        .id(i.toString())
                    emitter.send(event)

                    Thread.sleep(1000)
                }
                emitter.complete()
            } catch (ex: Exception) {
                emitter.completeWithError(ex)
            }
        }
        return emitter
    }
}
