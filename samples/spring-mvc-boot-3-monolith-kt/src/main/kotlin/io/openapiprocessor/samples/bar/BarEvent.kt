package io.openapiprocessor.samples.bar

import org.springframework.context.ApplicationEvent

class BarEvent(source: Any, val bar: String): ApplicationEvent(source) {
}
