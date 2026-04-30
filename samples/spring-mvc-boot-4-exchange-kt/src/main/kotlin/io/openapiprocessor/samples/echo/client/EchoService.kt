package io.openapiprocessor.samples.echo.client

import io.openapiprocessor.openapi.client.api.EchoApi
import org.springframework.stereotype.Service

@Service
class EchoService(val echoRestApi: EchoApi, val echoWebApi: EchoApi) {

    fun echoRest(echo: Echo): Echo {
        return echoRestApi.postEcho(true, echo)
    }

    fun echoWeb(echo: Echo): Echo {
        return echoWebApi.postEcho(true, echo)
    }
}
