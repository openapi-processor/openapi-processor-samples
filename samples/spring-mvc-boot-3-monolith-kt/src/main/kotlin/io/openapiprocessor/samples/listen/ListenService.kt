package io.openapiprocessor.samples.listen

import io.openapiprocessor.samples.bar.BarEvent
import io.openapiprocessor.samples.foo.FooEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class ListenService {

    @EventListener
    fun on(event: BarEvent) {
        println("bar event: ${event.bar}")
    }

    @EventListener
    fun on(event: FooEvent) {
        println("foo event: ${event.foo}")
    }
}
