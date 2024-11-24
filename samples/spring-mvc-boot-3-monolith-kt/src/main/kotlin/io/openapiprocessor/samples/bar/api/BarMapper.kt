package io.openapiprocessor.samples.bar.api

import io.openapiprocessor.samples.bar.domain.Bar
import io.openapiprocessor.samples.bar.model.BarDto
import org.mapstruct.Mapper

@Mapper
interface BarMapper {
    fun map(dto: BarDto): Bar
    fun map(domain: Bar): BarDto
}
