package com.hibernix.configuration.project

import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import javax.inject.Inject
import org.gradle.api.Project

open class ProjectConfigurationExtension @Inject constructor(
    private val project: Project
) {

    data class ProjectFeatures(
        val checkUpdates: Boolean = false,
        val stableUpdatesOnly: Boolean = false,
    )

    fun features(block: ProjectFeatures.() -> Unit) {
        val features = ProjectConfigurationExtension.ProjectFeatures()
        block(features)
        project.setupFeatures(features)
    }

    private fun Project.setupFeatures(features: ProjectConfigurationExtension.ProjectFeatures) {
        if (features.checkUpdates) {
            project.pluginManager.apply("com.github.ben-manes.versions")
            tasks.named("dependencyUpdates", DependencyUpdatesTask::class.java).configure {
                checkForGradleUpdate = true
                resolutionStrategy {
                    componentSelection {
                        all {
                            if (isNonStable(candidate.version) && !isNonStable(currentVersion)) {
                                reject("Release candidate")
                            }
                        }
                    }
                }
            }
        }
    }

    fun isNonStable(version: String): Boolean {
        val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
        val regex = "^[0-9,.v-]+(-r)?$".toRegex()
        val isStable = stableKeyword || regex.matches(version)
        return isStable.not()
    }
}
