package io.openapiprocessor.samples.foo.api

import io.openapiprocessor.samples.foo.domain.FooService
import io.openapiprocessor.samples.foo.model.FooDto
import org.springframework.web.bind.annotation.RestController

@RestController
class FooController(val mapper: FooMapper, val service: FooService): FooApi {

    override fun postFoo(foo: FooDto): FooDto {
        return mapper.map(service.echo(mapper.map(foo)))
    }
}
