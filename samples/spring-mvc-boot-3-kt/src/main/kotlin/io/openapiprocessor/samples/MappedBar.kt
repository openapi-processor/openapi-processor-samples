package io.openapiprocessor.samples

import jakarta.validation.constraints.NotNull

data class MappedBar(@NotNull val bar: String = "bar")
