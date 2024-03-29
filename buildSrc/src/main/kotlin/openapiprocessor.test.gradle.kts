@file:Suppress("UnstableApiUsage")

import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    java
}

// see buildSrc/build.gradle.kts
val libs = the<LibrariesForLibs>()

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter(libs.versions.junit.get())
        }
    }
}
