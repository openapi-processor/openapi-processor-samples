import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings

class RepositoriesPlugin : Plugin<Settings> {
    override fun apply(settings: Settings) {

        settings.pluginManagement {
            repositories {
//                mavenLocal()
                gradlePluginPortal()
                maven { setUrl("https://central.sonatype.com/repository/maven-snapshots") }
                maven { setUrl("https://repo.spring.io/milestone") }
                maven { setUrl("https://repo.spring.io/snapshot") }
            }
        }

        settings.dependencyResolutionManagement {
            @Suppress("UnstableApiUsage")
            repositories {
//                mavenLocal()
                mavenCentral()
                maven { setUrl("https://central.sonatype.com/repository/maven-snapshots") }
                maven { setUrl("https://repo.spring.io/milestone") }
                maven { setUrl("https://repo.spring.io/snapshot") }
            }
        }

    }
}
