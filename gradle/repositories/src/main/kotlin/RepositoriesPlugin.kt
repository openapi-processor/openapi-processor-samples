import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings

class RepositoriesPlugin : Plugin<Settings> {
    override fun apply(settings: Settings) {

        settings.pluginManagement {
            repositories {
                gradlePluginPortal()
                maven {
                    setUrl("https://oss.sonatype.org/content/repositories/snapshots")
                }
            }
        }

        settings.dependencyResolutionManagement {
            @Suppress("UnstableApiUsage")
            repositories {
                //mavenLocal()
                mavenCentral()
                maven {
                    setUrl("https://oss.sonatype.org/content/repositories/snapshots")
                }
            }
        }

    }
}
