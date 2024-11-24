package io.openapiprocessor.samples.foo.domain

import io.openapiprocessor.samples.foo.FooEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class FooService(val events: ApplicationEventPublisher) {

    fun echo(foo: Foo): Foo {
        events.publishEvent(FooEvent(this, foo.foo))
        return foo
    }
}
