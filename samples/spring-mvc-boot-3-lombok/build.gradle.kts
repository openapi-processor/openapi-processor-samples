// this build file is using a version catalog (see settings.gradle.kts)

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("java")
    id("groovy")
    alias(libs.plugins.boot3)
    alias(libs.plugins.boot.deps)
    alias(libs.plugins.versions)
    alias(libs.plugins.lombok)

    // add processor-gradle plugin
    alias(libs.plugins.processor.gradle)
}

group = "io.openapiprocessor"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
}

// configure an openapi-processor inside the 'openapiProcessor' configuration by adding a nested
// configuration with the name of the openapi-processor and its options inside it.
//
// ... using 'spring'.
openapiProcessor {

    // the path to the open api yaml file. Usually the same for all processors.
    apiPath("$projectDir/src/api/openapi.yaml")

    // based on the name of the processor configuration the plugin creates a gradle task with name
    // "process${name of processor}"  (in this case "processSpring") to run the processor.
    process("spring") {
        // the spring processor dependency
        processor("${libs.processor.spring.get()}")

        // setting api path inside a processor configuration overrides the one at the top.
        // apiPath "${projectDir}/src/api/openapi.yaml"

        // the destination folder for generating interfaces & models. This is the parent of the
        // {package-name} folder tree configured in the mapping file.
        targetDir("$projectDir/build/openapi")

        // processor specific options, creates a key => value map that is passed to the processor

        // file name of the mapping yaml configuration file. Note that the yaml file name must end
        // with either {@code .yaml} or {@code .yml}.
        prop("mapping", "$projectDir/src/api/mapping.yaml")

        // sets the parser to SWAGGER or INTERNAL. if not set SWAGGER is used.
        // INTERNAL provides full JSON schema validation
        prop("parser", "INTERNAL")

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
            srcDir("$projectDir/build/openapi")
        }
    }
}

// generate api before compiling
tasks.compileJava {
    dependsOn("processSpring")
}


/*
// build.gradle
afterEvaluate {
    def processSpring = tasks.named ('processSpring')
    sourceSets.main.java.srcDir (processSpring)
    compileJava.dependsOn (processSpring)
}
 */
