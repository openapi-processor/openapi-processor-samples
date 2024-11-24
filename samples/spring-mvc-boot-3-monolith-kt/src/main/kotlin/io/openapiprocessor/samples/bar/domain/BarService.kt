package io.openapiprocessor.samples.bar.domain

import io.openapiprocessor.samples.bar.BarEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class BarService(val events: ApplicationEventPublisher) {

    fun echo(bar: Bar): Bar {
        events.publishEvent(BarEvent(this, bar.bar))
        return bar
    }
}
