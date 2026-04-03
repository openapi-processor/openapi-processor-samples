package io.openapiprocessor.samples.echo.client

import io.openapiprocessor.openapi.client.api.EchoApi
import org.springframework.stereotype.Service

@Service
class EchoService(val echoApi: EchoApi) {
    fun echo(echo: Echo): Echo {
        return echoApi.postEcho(true, echo)
    }
}
