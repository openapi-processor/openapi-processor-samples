import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    id("com.github.ben-manes.versions")
}

tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf {
      isNonStable(candidate.version)
    }
}

fun isNonStable(version: String): Boolean {
    val nonStable = listOf(
        ".M[0-9]+$",
        ".RC[0-9]*$",
        ".alpha.?[0-9]+$",
        ".beta.?[0-9]+$",
    )

    for (n in nonStable) {
       if (version.contains("(?i)$n".toRegex())) {
           //println("not stable: $this")
           return true
       }
    }

    return false
}
