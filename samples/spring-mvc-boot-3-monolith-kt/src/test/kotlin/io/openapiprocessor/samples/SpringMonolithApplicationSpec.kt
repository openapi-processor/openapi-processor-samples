package io.openapiprocessor.samples

import io.kotest.core.spec.style.StringSpec
import org.springframework.modulith.core.ApplicationModules

class SpringMonolithApplicationSpec: StringSpec({

    "verify modules" {
        val modules = ApplicationModules.of(SpringMonolithApplication::class.java)
        modules.forEach { println(it) }
        modules.verify()
    }

})
