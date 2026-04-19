package io.openapiprocessor.samples.echo.client

import io.micronaut.http.client.annotation.Client
import io.openapiprocessor.openapi.api.EchoApi

@Client($$"${openapi.base.path}")
interface EchoClient: EchoApi
