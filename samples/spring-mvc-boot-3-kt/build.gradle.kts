import io.openapiprocessor.gradle.OpenApiProcessorTask

plugins {
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.deps)
    alias(libs.plugins.kotlin.lang)
    alias(libs.plugins.kotlin.spring)

    // add processor-gradle plugin
    alias(oap.plugins.processor.gradle)
}


group = "io.openapiprocessor.sample"
version = "1.0.0"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17

    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

dependencies {
    implementation(libs.spring.web)
    implementation(libs.spring.validation)
    implementation(libs.spring.data.common)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}


// configure an openapi-processor inside the 'openapiProcessor' configuration by adding a nested
// configuration with the name of the openapi-processor and its options inside it.
//
// ... using 'spring'.
openapiProcessor {

    // the path to the open api yaml file. Usually the same for all processors.
    //apiPath("${projectDir}/src/api/openapi.yaml")
    apiPath(layout.projectDirectory.file("src/api/openapi.yaml"))

    // based on the name of the processor configuration the plugin creates a Gradle task with name
    // "process${name of processor}"  (in this case "processSpring") to run the processor.
    process("spring") {
        // the spring processor dependency
        processor("${oap.processor.core.get()}")
        processor("${oap.processor.spring.get()}")

        // setting api path inside a processor configuration overrides the one at the top.
        // apiPath = layout.projectDirectory.file("src/api/openapi.yaml")

        // the destination folder for generating interfaces & models. This is the parent of the
        // {package-name} folder tree configured in the mapping file.

        //targetDir("$projectDir/build/openapi")
        targetDir(layout.buildDirectory.dir("openapi"))

        // processor specific options, creates a key => value map that is passed to the processor

        // file name of the mapping YAML configuration file. Note that the YAML file name must end
        // with either {@code .yaml} or {@code .yml}.
        prop("mapping", layout.projectDirectory.file("src/api/mapping.yaml"))
        prop("logging", "stdout")

        // alternative way of setting processor specific properties
        /*
        prop(mapOf(
            "mapping" to "$projectDir/src/api/mapping.yaml",
            "parser" to "INTERNAL"
        ))
         */
    }
}

sourceSets {
    create("api") {
        resources {
            // add api resources
            srcDir(layout.projectDirectory.dir("src/api"))
        }
    }

    afterEvaluate {
        main {
            java {
                // add generated files
                srcDir(tasks.named<OpenApiProcessorTask>("processSpring").map { it.getTargetDir().dir("java") })
            }
            resources {
                // add generated resources
                srcDir(tasks.named<OpenApiProcessorTask>("processSpring").map { it.getTargetDir().dir("resources") })
            }
        }
    }
}
