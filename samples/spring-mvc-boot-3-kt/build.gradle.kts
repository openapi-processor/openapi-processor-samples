import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.deps)
    alias(libs.plugins.kotlin.lang)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.versions)

    // add processor-gradle plugin
    alias(oap.plugins.processor.gradle)
}


group = "io.openapiprocessor.sample"
version = "1.0.0"

java {
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
    apiPath("${projectDir}/src/api/openapi.yaml")
    //apiPath = layout.projectDirectory.file("src/api/openapi.yaml")

    // based on the name of the processor configuration the plugin creates a gradle task with name
    // "process${name of processor}"  (in this case "processSpring") to run the processor.
    process("spring") {
        // the spring processor dependency
        processor("${oap.processor.core.get()}")
        processor("${oap.processor.spring.get()}")

        // setting api path inside a processor configuration overrides the one at the top.
        // apiPath = layout.projectDirectory.file("src/api/openapi.yaml")

        // the destination folder for generating interfaces & models. This is the parent of the
        // {package-name} folder tree configured in the mapping file.

        targetDir("$projectDir/build/openapi")
        //targetDir(layout.buildDirectory.dir("openapi"))
        //targetDir = layout.buildDirectory.dir("openapi")

        // processor specific options, creates a key => value map that is passed to the processor

        // file name of the mapping yaml configuration file. Note that the yaml file name must end
        // with either {@code .yaml} or {@code .yml}.
        prop("mapping", layout.projectDirectory.file("src/api/mapping.yaml"))

        // alternative way of setting processor specific properties
        /*
        prop(mapOf(
            "mapping" to "$projectDir/src/api/mapping.yaml",
            "parser" to "INTERNAL"
        ))
         */
    }
}

// add the targetDir of the processor as additional source folder to java.
sourceSets {
    main {
        java {
            // add generated files
            srcDir(layout.buildDirectory.dir("openapi/java"))
        }
        resources {
            // add generated resources
            srcDir(layout.buildDirectory.dir("openapi/resources"))
        }
    }
}

// generate api before compiling
tasks.withType<KotlinCompile> {
    dependsOn("processSpring")
}

// generate api resource before processing
tasks.withType<ProcessResources> {
    dependsOn("processSpring")
}
