pluginManagement {
    includeBuild("../../gradle/repositories")
    includeBuild("../../gradle/conventions")

    repositories {
        maven {
            url = uri("https://oss.sonatype.org/content/repositories/snapshots")
        }
        gradlePluginPortal()
    }
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

    repositories {
        maven {
            url = uri("https://oss.sonatype.org/content/repositories/snapshots")
        }
        mavenCentral ()
    }
}

rootProject.name="micronaut-4-kt"
