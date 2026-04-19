package io.openapiprocessor.samples

import io.micronaut.context.annotation.Context
import io.micronaut.runtime.event.annotation.EventListener
import io.micronaut.runtime.server.event.ServerStartupEvent
import io.openapiprocessor.openapi.model.Echo
import io.openapiprocessor.samples.echo.client.EchoService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono


@Context
class Runner(val echoService: EchoService) {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass.name)

    @EventListener
    fun onApplicationEvent(event: ServerStartupEvent) {
        Mono.from(echoService.echo(Echo("openapi-processor!")))
            .map {
                log.info("echo: {}", it.toString())
            }
            .block()
    }
}
