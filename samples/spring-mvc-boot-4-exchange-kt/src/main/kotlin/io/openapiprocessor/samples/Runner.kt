package io.openapiprocessor.samples

import io.openapiprocessor.samples.echo.client.Echo
import io.openapiprocessor.samples.echo.client.EchoService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class SampleRunner(val echoService: EchoService) : CommandLineRunner {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass.name)

    override fun run(vararg args: String) {
        log.info("{}", echoService.echo(Echo("openapi-processor!")))
    }
}
