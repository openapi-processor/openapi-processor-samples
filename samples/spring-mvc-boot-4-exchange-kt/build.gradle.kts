import io.openapiprocessor.gradle.OpenApiProcessorTask

plugins {
    alias(libs.plugins.kotlin.lang)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.deps)
    //id("updates")

    // add processor-gradle plugin
    alias(oap.plugins.processor.gradle.current)
}

group = "io.openapiprocessor.sample"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("tools.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
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
openapiProcessor {

    // the path to the open api YAML file. Usually the same for all processors.
    apiPath("${projectDir}/src/api/openapi.yaml")
    //apiPath("${projectDir}/src/api/openapi.json")

    // based on the name of the processor configuration, the plugin creates a Gradle task with name
    // "process${name of processor}" (in this case "processSpring") to run the processor.
    process("server") {
        processorName("spring")

        // the spring processor dependency
        dependencies {
           //process("${oap.processor.core.get()}")
            process("${oap.processor.spring.get()}")
        }

        // setting api path inside a processor configuration overrides the one at the top.
        // apiPath = layout.projectDirectory.file("src/api/openapi.yaml")

        // the destination folder for generating interfaces & models. This is the parent of the
        // {package-name} folder tree configured in the mapping file.

        targetDir("$projectDir/build/openapi/server")
        //targetDir(layout.buildDirectory.dir("openapi"))

        // processor-specific options, creates a key => value map passed to the processor

        // file name of the mapping YAML configuration file. Note that the YAML file name must end
        // with either {@code .yaml} or {@code .yml}.
        prop("mapping", layout.projectDirectory.file("src/api/mapping-server.yaml"))
        prop("logging", "stdout")
    }

    process("client") {
        processorName("spring")

        // the spring processor dependencies
        dependencies {
            //process("${oap.processor.core.get()}")
            process("${oap.processor.spring.get()}")
        }

        // setting api path inside a processor configuration overrides the one at the top.
        // apiPath = layout.projectDirectory.file("src/api/openapi.yaml")

        // the destination folder for generating interfaces & models. This is the parent of the
        // {package-name} folder tree configured in the mapping file.

        targetDir("$projectDir/build/openapi/client")
        //targetDir(layout.buildDirectory.dir("openapi"))

        // processor-specific options, creates a key => value map passed to the processor

        // file name of the mapping YAML configuration file. Note that the YAML file name must end
        // with either {@code .yaml} or {@code .yml}.
        prop("mapping", layout.projectDirectory.file("src/api/mapping-client.yaml"))
        prop("logging", "stdout")
    }

}

// "modern" configuration
sourceSets {
    create("api") {
        resources {
            // add api resources
            srcDir("${projectDir}/src/api")
        }
    }

    main {
        val serverTask = tasks.named<OpenApiProcessorTask>("processServer")
        val clientTask = tasks.named<OpenApiProcessorTask>("processClient")

        java {
            // add generated files
            srcDir(serverTask.map { it.getTargetDir().dir("java") })
            srcDir(clientTask.map { it.getTargetDir().dir("java") })
        }
        resources {
            // add generated resources
            srcDir(serverTask.map { it.getTargetDir().dir("resources") })
            srcDir(clientTask.map { it.getTargetDir().dir("resources") })
        }
    }
}
