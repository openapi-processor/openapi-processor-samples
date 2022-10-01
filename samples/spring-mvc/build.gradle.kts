// this build file is using a version catalog (see ../gradle/libs.versions.toml)

// to try this, delete or rename the groovy based build.gradle. gradle will pick up build.gradle
// first if it is available.

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("java")
    id("groovy")
    alias(libs.plugins.boot)
    alias(libs.plugins.boot.deps)
    alias(libs.plugins.versions)
    alias(libs.plugins.test.sets)

    // add processor-gradle plugin
    alias(libs.plugins.processor.gradle)
}

group = "io.openapiprocessor.samples"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral ()
}

testSets {
    "testInt"()
}

tasks.withType<Test>() {
    useJUnitPlatform()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(libs.groovy)
    testImplementation(platform(libs.spock.bom.get()))
    testImplementation("org.spockframework:spock-core")
    testImplementation("org.spockframework:spock-spring")
}

// configure an openapi-processor inside the 'openapiProcessor' configuration by adding a nested
// configuration with the name of the openapi-processor and its options inside it.
//
// ... using 'spring' and 'json'.
openapiProcessor {

    // the path to the open api yaml file. Usually the same for all processors.
    apiPath("$projectDir/src/api/openapi.yaml")

    // based on the name of the processor configuration the plugin creates a gradle task with name
    // "process${name of processor}"  (in this case "processSpring") to run the processor.
    process("spring") {
        // the spring processor dependency
//        processor("io.openapiprocessor:openapi-processor-spring:2022.5-SNAPSHOT")
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

        // sets the parser to SWAGGER, OPENAPI4J or INTERNAL. if not set SWAGGER is used.
        // OPENAPI4J provides better validation but is not maintained anymore.
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

    // applying the rules described above the task to run this one is "processJson".
    process("json") {
        // the json processor dependency
//        processor("io.openapiprocessor:openapi-processor-json:2021.2")
        processor("${libs.processor.json.get()}")

        targetDir("$buildDir/json")
    }

}

// add the targetDir of the processor as additional source folder to java.
sourceSets {
    main {
        java {
            // add generated files
            srcDir("build/openapi")
        }

        resources {
            srcDir("$buildDir/json")
        }
    }
}

// generate api before compiling
tasks.compileJava {
    dependsOn("processSpring")
}

tasks.processResources {
    dependsOn("processJson")
}

tasks.check {
    dependsOn("testInt")
}
