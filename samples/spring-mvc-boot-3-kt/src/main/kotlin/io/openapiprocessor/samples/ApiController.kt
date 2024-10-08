package io.openapiprocessor.samples

import io.openapiprocessor.samples.api.BarApi
import io.openapiprocessor.samples.api.FooApi
import io.openapiprocessor.samples.model.Bar
import io.openapiprocessor.samples.model.Foo
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class ApiController: BarApi, FooApi {

    override fun postFoo(foo: Foo?): Foo {
        return foo ?: Foo("foo", UUID.randomUUID(), Bar("value"))
    }

    override fun echoBar(bar: Bar): Bar {
        return bar
    }

    override fun getBars(pageable: Pageable?): Page<Bar> {
        return PageImpl(listOf(
            Bar("value 1"),
            Bar("value 2")
        ))
    }
}
