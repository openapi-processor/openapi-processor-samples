plugins {
    id("java")
    id("groovy")
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.deps)
    alias(libs.plugins.versions)
    alias(libs.plugins.lombok)

    // add processor-gradle plugin
    alias(oap.plugins.processor.gradle)
}

group = "io.openapiprocessor"
version = "1.0.0-SNAPSHOT"

dependencies {
    implementation(libs.spring.web)
    implementation(libs.spring.validation)
    testImplementation(libs.spring.test)
}

// configure multiple openapi-processors inside the 'openapiProcessor' configuration by adding a nested
// configuration with different names of the openapi-processor and its options inside it.
openapiProcessor {
    checkUpdates("always")

    // processor setup for the first api
    process("spring1") {
        // by using a different name than the processor we have to say which processor we want
        processorName("spring")
        processor("${oap.processor.spring.get()}")
//        processor("${oap.processor.core.get()}")

        apiPath("${projectDir}/src/api1/openapi.yaml")
        targetDir("$projectDir/build/api1")

        prop("mapping", "$projectDir/src/api1/mapping.yaml")
    }

    process("spring2") {
        // by using a different name than the processor we have to say which processor we want
        processorName("spring")
        processor("${oap.processor.spring.get()}")
//        processor("${oap.processor.core.get()}")

        apiPath("${projectDir}/src/api2/openapi.yaml")
        targetDir("$projectDir/build/api2")

        prop("mapping", "$projectDir/src/api2/mapping.yaml")
    }
}

sourceSets {
    main {
        java {
            srcDir("$projectDir/build/api1")
            srcDir("$projectDir/build/api2")
        }
    }
}

tasks.compileJava {
    dependsOn("processSpring1", "processSpring2")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
