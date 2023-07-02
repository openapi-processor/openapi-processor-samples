package io.openapiprocessor.samples

import io.openapiprocessor.openapi.api.BarApi
import io.openapiprocessor.openapi.api.FooApi
import io.openapiprocessor.openapi.model.Foo
import org.springframework.web.bind.annotation.RestController

@RestController
class ApiController: BarApi, FooApi {

    override fun postBar(bar: MappedBar?): MappedBar {
        TODO("Not yet implemented")
    }

    override fun postFoo(foo: Foo?): Foo {
        TODO("Not yet implemented")
    }
}
