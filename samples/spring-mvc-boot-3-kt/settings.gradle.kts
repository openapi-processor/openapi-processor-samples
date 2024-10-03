pluginManagement {
    includeBuild("../../gradle/repositories")
    includeBuild("../../gradle/conventions")
}

plugins {
    id("repositories")
}

dependencyResolutionManagement {
    versionCatalogs {
        create("oap") {
            from(files("../../gradle/oap.versions.toml"))
        }
    }
}

rootProject.name = "spring-mvc-boot-3-kt"

//includeBuild("../../../openapi-processor-gradle")
//includeBuild("../../../openapi-processor-spring")
