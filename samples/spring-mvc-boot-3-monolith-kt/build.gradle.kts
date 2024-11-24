import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.lang)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.deps)
    alias(oap.plugins.processor.gradle)
}

group = "io.openapiprocessor.samples"
version = "1.0.0"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

dependencies {
    kapt(libs.mapstruct.annotation)
    implementation(libs.spring.web)
    implementation(libs.spring.modulith.core)
    implementation(libs.kotlin.jackson)
    implementation(libs.kotlin.reflect)
    implementation(libs.mapstruct.core)
    testImplementation(libs.spring.test)
    testImplementation(libs.spring.modulith.test)
    testImplementation(libs.kotest.runner)
}

dependencyManagement {
    imports {
        mavenBom(libs.spring.modulith.bom.get().toString())
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}


kapt {
    arguments {
        arg("mapstruct.defaultComponentModel", "spring")
        arg("mapstruct.defaultInjectionStrategy", "constructor")
    }
}


tasks.withType<Test> {
    useJUnitPlatform()
}

openapiProcessor {
    val basePath = "${projectDir}/src/main/kotlin/io/openapiprocessor/samples"

    process("Bar") {
        processorName("spring")
        processor("${oap.processor.spring.get()}")

        val barPath = "${basePath}/bar/api"
        apiPath("${barPath}/openapi.yaml")
        targetDir("$projectDir/build/openapi/bar")
        prop("mapping", layout.projectDirectory.file("${barPath}/mapping.yaml"))
    }

    process("Foo") {
        processorName("spring")
        processor("${oap.processor.spring.get()}")

        val fooPath = "${basePath}/foo/api"
        apiPath("${fooPath}/openapi.yaml")
        targetDir("$projectDir/build/openapi/foo")
        prop("mapping", layout.projectDirectory.file("${fooPath}/mapping.yaml"))
    }
}

sourceSets {
    main {
        java {
            srcDir(layout.buildDirectory.dir("openapi/bar/java"))
            srcDir(layout.buildDirectory.dir("openapi/foo/java"))
        }
        resources {
            srcDir(layout.buildDirectory.dir("openapi/bar/resources"))
            srcDir(layout.buildDirectory.dir("openapi/foo/resources"))
        }
    }
}

// generate api before compiling
tasks.withType<KotlinCompile> {
    dependsOn("processBar", "processFoo")
}

// generate api resource before processing
tasks.withType<ProcessResources> {
    dependsOn("processBar", "processFoo")
}
