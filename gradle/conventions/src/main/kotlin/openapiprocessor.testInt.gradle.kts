plugins {
    java
}

testing {
    suites {
        @Suppress("UnstableApiUsage") val test by getting(JvmTestSuite::class)

        @Suppress("UnstableApiUsage") val testInt by registering(JvmTestSuite::class) {
            useJUnitJupiter()
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
    @Suppress("UnstableApiUsage")
    dependsOn(testing.suites.named("testInt"))
}
