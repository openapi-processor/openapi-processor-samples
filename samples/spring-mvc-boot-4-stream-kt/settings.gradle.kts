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

rootProject.name = "spring-mvc-boot-4-stream-kt"

//includeBuild("../../../openapi-processor-spring")
//includeBuild("../../../openapi-processor-base")
