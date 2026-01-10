package io.openapiprocessor.samples

import io.micronaut.http.annotation.Controller
import io.openapiprocessor.openapi.api.BarApi
import io.openapiprocessor.openapi.api.FooApi
import io.openapiprocessor.openapi.model.Bar
import io.openapiprocessor.openapi.model.Foo
import java.util.*

//@Controller
@Controller("/foo/bar/v1")
open class ApiController: FooApi, BarApi {

    override fun postFoo(foo: Foo?): Foo {
        return foo ?: Foo("foo", UUID.randomUUID(), Bar("value"))
    }

    override fun postBar(bar: Bar?): Bar {
        return bar ?: Bar("bar value")
    }
}
