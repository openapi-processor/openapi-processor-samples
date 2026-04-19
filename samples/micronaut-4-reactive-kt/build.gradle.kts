import io.openapiprocessor.gradle.OpenApiProcessorTask

plugins {
    id("org.jetbrains.kotlin.jvm") version "2.2.21"
    id("org.jetbrains.kotlin.plugin.allopen") version "2.2.21"
    id("com.google.devtools.ksp") version "2.2.21-2.0.5"
    id("io.micronaut.application") version "4.6.2"
    id("com.gradleup.shadow") version "8.3.9"
    id("io.micronaut.aot") version "4.6.2"

    // add openapi-processor-gradle plugin
    alias(oap.plugins.processor.gradle.next)
}

version = "0.1"
group = "io.openapiprocessor.samples"

val kotlinVersion=project.properties.get("kotlinVersion")

//repositories {
//    mavenCentral()
//}

dependencies {
    ksp("io.micronaut:micronaut-http-validation")
    ksp("io.micronaut.serde:micronaut-serde-processor")
    implementation("io.micrometer:context-propagation")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut.reactor:micronaut-reactor")
    implementation("io.micronaut.reactor:micronaut-reactor-http-client")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    implementation("jakarta.validation:jakarta.validation-api")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin") {
      exclude(group = "org.jetbrains.kotlin", module = "kotlin-reflect")
    }
}

/*
    implementation("io.micronaut.validation:micronaut-validation")
    implementation("jakarta.validation:jakarta.validation-api")
 */

application {
    mainClass = "io.openapiprocessor.samples.ApplicationKt"
}

java {
    sourceCompatibility = JavaVersion.toVersion("21")
}

graalvmNative.toolchainDetection = false

micronaut {
    runtime("netty")
    testRuntime("kotest5")
    processing {
        incremental(true)
        annotations("io.openapiprocessor.samples.*")
    }
    aot {
        // Please review carefully the optimizations enabled below
        // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
        optimizeServiceLoading = false
        convertYamlToJava = false
        precomputeOperations = true
        cacheEnvironment = true
        optimizeClassLoading = true
        deduceEnvironment = true
        optimizeNetty = true
        replaceLogbackXml = true
    }
}

tasks.named<io.micronaut.gradle.docker.NativeImageDockerfile>("dockerfileNative") {
    jdkVersion = "21"
}

// configure an openapi-processor inside the 'openapiProcessor' configuration by adding a nested
// configuration with the name of the openapi-processor and its options inside it.
//
openapiProcessor {
    // the path to the open api yaml file. Usually the same for all processors.
//     apiPath(layout.projectDirectory.file("/src/api/openapi.yaml").asFile.absolutePath)
    apiPath("$projectDir/src/api/openapi.yaml")

    // based on the name of the processor configuration, the plugin creates a Gradle task with the name
    // "process${name of processor}" (in this case "processMicronaut") to run the processor.
    process("micronaut") {
        // the processor dependency
        dependencies {
            //process("${libs.processor.core.get()}")
            process("${oap.processor.micronaut.get()}")
        }

        // setting api path inside a processor configuration overrides the one at the top.
        //apiPath(layout.projectDirectory.file("/src/api/openapi.yaml").toString())

        // the destination folder for generating interfaces & DTOs. This is the parent of the
        // {package-name} folder tree configured in the mapping file.
        targetDir(layout.buildDirectory.file("openapi").get().toString())

        // processor-specific options, creates a key => value map passed to the processors

        // file name of the mapping YAML configuration file. Note that the YAML file name must end
        // with either {@code .yaml} or {@code .yml}.
//        prop("mapping", layout.projectDirectory.file("/src/api/mapping.yaml").toString())
        prop("mapping", "$projectDir/src/api/mapping.yaml")
    }
}

sourceSets {
    create("api") {
        resources {
            // add api resources
            srcDir(layout.projectDirectory.dir("src/api"))
        }
    }

    main {
        val processTask = tasks.named<OpenApiProcessorTask>("processMicronaut")

        java {
            // add generated files
            srcDir(processTask.map { it.getTargetDir().dir("java") })
        }
        resources {
            // add generated resources
            srcDir(processTask.map { it.getTargetDir().dir("resources") })
        }
    }
}

//tasks.withType<Jar> {
//    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
//}






