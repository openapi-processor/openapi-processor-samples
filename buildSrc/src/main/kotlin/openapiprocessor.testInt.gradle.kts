@file:Suppress("UnstableApiUsage")

import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    java
}

// see buildSrc/build.gradle.kts
val libs = the<LibrariesForLibs>()

testing {
    suites {
        val test by getting(JvmTestSuite::class)

        val testInt by registering(JvmTestSuite::class) {
            useJUnitJupiter(libs.versions.junit.get())
            testType.set(TestSuiteType.INTEGRATION_TEST)

            dependencies {
                implementation(project())
            }

            sources {
                java {
                    srcDirs("src/testInt/groovy")
                }
            }

            targets {
                all {
                    testTask.configure {
                        shouldRunAfter(test)
                    }
                }
            }
        }
    }
}

tasks.named("check") {
    dependsOn(testing.suites.named("testInt"))
}
