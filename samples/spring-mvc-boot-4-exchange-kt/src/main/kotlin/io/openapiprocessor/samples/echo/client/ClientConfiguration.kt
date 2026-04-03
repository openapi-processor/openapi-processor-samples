package io.openapiprocessor.samples.echo.client

import io.openapiprocessor.openapi.client.api.EchoApi
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient
import org.springframework.web.client.support.RestClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import org.springframework.web.service.invoker.createClient


@Configuration
class ClientConfiguration {

    @Bean
    fun echoClient(@Value($$"${server.servlet.context-path}") context: String): RestClient {
        return RestClient.builder()
            .baseUrl("http://localhost:8080${context}")
            .build()
    }

    @Bean
    fun echoApi(echoClient: RestClient): EchoApi {
        val factory = HttpServiceProxyFactory
            .builderFor(RestClientAdapter.create(echoClient))
            .build()

        return factory.createClient<EchoApi>()
    }
}
