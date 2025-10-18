/*
 * Copyright 2025 https://github.com/openapi-processor/openapi-processor-samples
 * PDX-License-Identifier: Apache-2.0
 */

package io.openapiprocessor.samples.jsonl

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody
import tools.jackson.databind.ObjectMapper


@RestController
class JsonlController(val mapper: ObjectMapper) {

    @GetMapping(value = ["/jsonl"], produces = ["application/jsonl"])
    fun getJsonl(source: String?): StreamingResponseBody {
        return StreamingResponseBody { os ->
            mapper.createGenerator(os).use { generator ->
                try {
                    for (i in 1..100) {
                        generator.writePOJO(JsonlGetResponse200("foo $i"))
                        generator.flush()
                        Thread.sleep(1000)
                    }
                } catch (ex: Exception) {
                    throw ex
                } finally {
                    os.close()
                }
            }
        }
    }
}
