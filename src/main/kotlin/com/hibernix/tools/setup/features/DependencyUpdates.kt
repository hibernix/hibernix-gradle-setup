package com.hibernix.tools.setup.features

import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.gradle.kotlin.dsl.named

/**
 */
fun Features.dependencyUpdates(stableOnly: Boolean = false) = with(project) {
    pluginManager.apply("com.github.ben-manes.versions")
    tasks.named<DependencyUpdatesTask>("dependencyUpdates").configure {
        checkForGradleUpdate = true
        if (stableOnly) {
            resolutionStrategy {
                componentSelection {
                    all {
                        if (isVersionNonStable(candidate.version) && !isVersionNonStable(currentVersion)) {
                            reject("Release candidate")
                        }
                    }
                }
            }
        }
    }
}

private fun isVersionNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}
