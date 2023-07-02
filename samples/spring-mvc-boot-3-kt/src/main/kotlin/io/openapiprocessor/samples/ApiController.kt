package io.openapiprocessor.samples

import io.openapiprocessor.openapi.api.BarApi
import io.openapiprocessor.openapi.api.FooApi
import io.openapiprocessor.openapi.model.Foo
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class ApiController: BarApi, FooApi {

    override fun postBar(bar: MappedBar?): MappedBar {
        return bar ?: MappedBar()
    }

    override fun postFoo(foo: Foo?): Foo {
        return foo ?: Foo("foo", UUID.randomUUID(), MappedBar())
    }
}
