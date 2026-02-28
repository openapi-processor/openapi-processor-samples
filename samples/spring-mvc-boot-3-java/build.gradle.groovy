plugins {
    id 'java'
    id 'groovy'
    id 'updates'
    id 'openapiprocessor.testInt'
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.deps)

    // add processor-gradle plugin
    alias(oap.plugins.processor.gradle.next)
}

group = 'io.openapiprocessor.samples'
version = '1.0.0-SNAPSHOT'

dependencies {
    implementation(libs.spring.web)
    implementation(libs.spring.validation)

    testImplementation(libs.bundles.spring.test)
    testImplementation(libs.bundles.groovy.test)
    testIntImplementation(libs.bundles.spring.test)
    testIntImplementation(libs.bundles.groovy.test)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17

    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

// configure an openapi-processor inside the 'openapiProcessor' configuration by adding a nested
// configuration with the name of the openapi-processor and its options inside it.
//
// ... using 'spring'
openapiProcessor {
    // the path to the open api YAML file. Usually the same for all processors.
    //apiPath "${projectDir}/src/api/openapi.yaml"  // set as String by method call
    apiPath = layout.projectDirectory.file("src/api/openapi.yaml")

    // based on the name of the processor configuration the plugin creates a Gradle task with name
    // "process${name of processor}"  (in this case "processSpring") to run the processor.
    spring {
        // the spring processor dependency
        // processor 'io.openapiprocessor:openapi-processor-core:2022.6-SNAPSHOT'
        // processor 'io.openapiprocessor:openapi-processor-spring:2022.4-SNAPSHOT'
        //processor "${libs.processor.core.get()}"
        processor "${oap.processor.spring.get()}"

        // setting api path inside a processor configuration overrides the one at the top.
        apiPath "${projectDir}/src/api/openapi.yaml"
        //apiPath = layout.projectDirectory.file("src/api/openapi.yaml")

        // the destination folder for generating interfaces & models. This is the parent of the
        // {package-name} folder tree configured in the mapping file.
        //targetDir "$projectDir/build/openapi"
        targetDir layout.buildDirectory.dir("openapi")

        // processor specific options, creates a key => value map that is passed to the processor

        // file name of the mapping YAML configuration file. Note that the YAML file name must end
        // with either {@code .yaml} or {@code .yml}.
        //mapping "${projectDir}/src/api/mapping.yaml"
        mapping layout.projectDirectory.file("src/api/mapping.yaml")

        // sets the parser to SWAGGER or INTERNAL. if not set INTERNAL is used.
        // INTERNAL provides full JSON schema validation
        // prop("parser", "SWAGGER")
        //parser 'SWAGGER'
    }
}

// "modern" configuration
sourceSets {
    api {
        resources {
            // add api resources
            srcDir layout.projectDirectory.dir("src/api")
        }
    }

    main {
        java {
            // add generated files
            srcDir tasks.named("processSpring")
        }
    }
}
