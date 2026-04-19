package io.openapiprocessor.samples

import io.micronaut.runtime.Micronaut


fun main(args: Array<String>) {
    Micronaut
        .build(*args)
        .defaultEnvironments("openapi")
        .start()

//    run(*args)
}
