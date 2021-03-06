// to test this, delete or rename the groovy based build.gradle. gradle will pick up build.gradle
// first if it is available.

plugins {
    id("java")
    id("groovy")
    id("io.spring.dependency-management") version("1.0.9.RELEASE")
    id("org.springframework.boot") version("2.3.1.RELEASE")
    id("org.unbroken-dome.test-sets") version("3.0.1")
    id("com.github.ben-manes.versions") version("0.28.0")

    // add processor-gradle plugin
    id("io.openapiprocessor.openapi-processor") version ("2021.3")
}

group = "io.openapiprocessor.samples"
version = "1.0.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

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
    testImplementation("org.codehaus.groovy:groovy:2.5.12")
    testImplementation(platform("org.spockframework:spock-bom:2.0-M3-groovy-2.5"))
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
        processor("io.openapiprocessor:openapi-processor-spring:2021.3")
        // processor("io.openapiprocessor:openapi-processor-core:2021.3-SNAPSHOT")

        // setting api path inside a processor configuration overrides the one at the top.
        // apiPath "${projectDir}/src/api/openapi.yaml"

        // the destination folder for generating interfaces & models. This is the parent of the
        // {package-name} folder tree configured in the mapping file.
        targetDir("$projectDir/build/openapi")

        // processor specific options, creates a key => value map that is passed to the processor

        // file name of the mapping yaml configuration file. Note that the yaml file name must end
        // with either {@code .yaml} or {@code .yml}.
        prop("mapping", "$projectDir/src/api/mapping.yaml")

        // sets the parser to SWAGGER or OPENAPI4J. if not set SWAGGER is used.
        // OPENAPI4J provides better validation.
        prop("parser", "OPENAPI4J")

        // alternative way of setting processor specific properties
        /*
        prop(mapOf(
            "mapping" to "$projectDir/src/api/mapping.yaml",
            "parser" to "OPENAPI4J"
        ))
         */
    }

    // applying the rules described above the task to run this one is "processJson".
    process("json") {
        // the json processor dependency
        processor("io.openapiprocessor:openapi-processor-json:2021.2")

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
