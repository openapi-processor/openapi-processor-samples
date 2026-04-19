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

rootProject.name="micronaut-4-reactive-kt"

//includeBuild("../../../openapi-processor-micronaut")
//includeBuild("../../../openapi-processor-base")
