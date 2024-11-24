package io.openapiprocessor.samples.foo.api

import io.openapiprocessor.samples.foo.domain.Foo
import io.openapiprocessor.samples.foo.model.FooDto
import org.mapstruct.Mapper

@Mapper
interface FooMapper {
    fun map(dto: FooDto): Foo
    fun map(domain: Foo): FooDto
}
