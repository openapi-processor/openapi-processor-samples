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

rootProject.name = "spring-mvc-boot-3-monolith-kt"
