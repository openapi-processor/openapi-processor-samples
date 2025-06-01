/*
 * Copyright 2015 https://github.com/openapi-processor/openapi-processor-samples
 * PDX-License-Identifier: Apache-2.0
 */

package io.openapiprocessor.samples.foo

import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class FooController: FooApi {
    override fun postFoo(foo: Foo?): Foo {
        return foo ?: Foo("foo", UUID.randomUUID())
    }
}
