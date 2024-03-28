package com.hibernix.tools.setup.features

import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import com.hibernix.tools.setup.core.log
import org.gradle.kotlin.dsl.named

/**
 *
 */
enum class DependencyUpdatesMinStability(val imaturity: Int) {
    RELEASE(-1),
    RC(0),
    MILESTONE(1),
    BETA(2),
    ALPHA(3),
    PREVIEW(4),
    DEV(5),
    SNAPSHOT(6),
}

fun Features.dependencyUpdates(stability: DependencyUpdatesMinStability = DependencyUpdatesMinStability.RELEASE) = with(project) {
    pluginManager.apply("com.github.ben-manes.versions")
    tasks.named<DependencyUpdatesTask>("dependencyUpdates").configure {
        val immaturityLevels = listOf("rc", "m", "beta", "alpha", "preview", "dev", "snapshot") // order is important
        val immaturityRegexes = immaturityLevels.map { ".*[.\\-]$it[.\\-\\d]*".toRegex(RegexOption.IGNORE_CASE) }
        fun immaturityLevel(version: String): Int = immaturityRegexes.indexOfLast { version.matches(it) }
        rejectVersionIf {
            //log("Candidate: ${candidate.version} (${immaturityLevel(candidate.version)}) / Current: $currentVersion (${immaturityLevel(currentVersion)}) | stability: ${stability.imaturity}")
            /*immaturityLevel(candidate.version) > immaturityLevel(currentVersion) && */stability.imaturity < immaturityLevel(candidate.version)
        }
    }
}
