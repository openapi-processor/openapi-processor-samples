plugins {
    id("java")
    id("groovy")
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.deps)
    alias(libs.plugins.versions)
    alias(libs.plugins.lombok)

    // add processor-gradle plugin
    alias(oap.plugins.processor.gradle.current)
}

group = "io.openapiprocessor"
version = "1.0.0-SNAPSHOT"

dependencies {
    implementation(libs.spring.web)
    implementation(libs.spring.validation)
    testImplementation(libs.spring.test)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17

    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

// configure multiple openapi-processors inside the 'openapiProcessor' configuration by adding a nested
// configuration with different names of the openapi-processor and its options inside it.
openapiProcessor {
    checkUpdates("always")

    // processor setup for the first api
    process("spring1") {
        // by using a different name than the processor, we have to say which processor we want
        processorName("spring")
        processor("${oap.processor.spring.get()}")

        apiPath("${projectDir}/src/api1/openapi.yaml")
        targetDir("$projectDir/build/api1")

        prop("mapping", "$projectDir/src/api1/mapping.yaml")
    }

    process("spring2") {
        // by using a different name than the processor, we have to say which processor we want
        processorName("spring")
        processor("${oap.processor.spring.get()}")

        apiPath("${projectDir}/src/api2/openapi.yaml")
        targetDir("$projectDir/build/api2")

        prop("mapping", "$projectDir/src/api2/mapping.yaml")
    }
}

sourceSets {
    create("api1") {
        resources {
            // add api resources
            srcDir(layout.projectDirectory.dir("src/api1"))
        }
    }
    create("api2") {
        resources {
            // add api resources
            srcDir(layout.projectDirectory.dir("src/api2"))
        }
    }

    main {
        java {
            // add generated files
            srcDir(tasks.named<io.openapiprocessor.gradle.OpenApiProcessorTask>("processSpring1"))
            srcDir(tasks.named<io.openapiprocessor.gradle.OpenApiProcessorTask>("processSpring2"))
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
