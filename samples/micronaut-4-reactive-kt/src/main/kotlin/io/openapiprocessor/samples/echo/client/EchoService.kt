package io.openapiprocessor.samples.echo.client

import io.openapiprocessor.openapi.model.Echo
import jakarta.inject.Singleton
import org.reactivestreams.Publisher
import reactor.core.publisher.Mono

@Singleton
class EchoService(val echoClient: EchoClient) {

    fun echo(echo: Echo): Publisher<Echo> {
        return echoClient.postEcho(Mono.just(echo))
    }

}
