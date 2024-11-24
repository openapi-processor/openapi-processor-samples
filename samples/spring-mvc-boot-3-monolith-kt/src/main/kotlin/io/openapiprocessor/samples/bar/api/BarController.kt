package io.openapiprocessor.samples.bar.api

import io.openapiprocessor.samples.bar.domain.BarService
import io.openapiprocessor.samples.bar.model.BarDto
import org.springframework.web.bind.annotation.RestController

@RestController
class BarController(private val mapper: BarMapper, private val service: BarService): BarApi {

    override fun postBar(bar: BarDto): BarDto {
        return mapper.map(service.echo(mapper.map(bar)))
    }
}
