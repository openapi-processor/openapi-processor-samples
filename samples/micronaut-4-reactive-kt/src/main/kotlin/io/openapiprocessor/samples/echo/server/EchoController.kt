package io.openapiprocessor.samples.echo.server

import io.micronaut.http.annotation.Controller
import io.openapiprocessor.openapi.api.EchoApi
import io.openapiprocessor.openapi.model.Echo
import jakarta.validation.Valid
import org.reactivestreams.Publisher

@Controller
open class EchoController: EchoApi {

    override fun postEcho(body: @Valid Publisher<Echo>): Publisher<Echo> {
        return body
    }
}
