pluginManagement {
    repositories {
        maven {
            url("https://oss.sonatype.org/content/repositories/snapshots")
        }
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        maven {
            url("https://oss.sonatype.org/content/repositories/snapshots")
        }
        mavenCentral ()
    }

    versionCatalogs {
        create("libs") {
            from(files("../../gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "spring-mvc-boot-3-kt"
