package io.openapiprocessor.samples.foo

import org.springframework.context.ApplicationEvent

class FooEvent(source: Any, val foo: String): ApplicationEvent(source) {
}
