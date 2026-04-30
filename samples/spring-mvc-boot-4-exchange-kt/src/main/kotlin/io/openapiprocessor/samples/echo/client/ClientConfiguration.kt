package io.openapiprocessor.samples.echo.client

import io.openapiprocessor.openapi.client.api.EchoApi
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient
import org.springframework.web.client.support.RestClientAdapter
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.support.WebClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import org.springframework.web.service.invoker.createClient

@Configuration
class ClientConfiguration {

    @Bean
    fun echoRestClient(@Value($$"${server.servlet.context-path}") context: String): RestClient {
        return RestClient.builder()
            .baseUrl("http://localhost:8080${context}")
            .build()
    }

    @Bean
    fun echoRestApi(echoClient: RestClient): EchoApi {
        val factory = HttpServiceProxyFactory
            .builderFor(RestClientAdapter.create(echoClient))
            .build()

        return factory.createClient<EchoApi>()
    }

    @Bean
    fun echoWebClient(@Value($$"${server.servlet.context-path}") context: String): WebClient {
        return WebClient.builder()
            .baseUrl("http://localhost:8080${context}")
            .build()
    }

    @Bean
    fun echoWebApi(echoWebClient: WebClient): EchoApi {
        val factory = HttpServiceProxyFactory
            .builderFor(WebClientAdapter.create(echoWebClient))
            .build()

        return factory.createClient<EchoApi>()
    }
}
