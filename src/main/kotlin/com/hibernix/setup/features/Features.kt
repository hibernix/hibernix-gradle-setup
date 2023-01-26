package com.hibernix.setup.features

import com.hibernix.setup.core.addMppDependency
import com.hibernix.setup.core.log
import org.gradle.api.Project

/**
 * Base for basic features of projects
 */

class Features(val project: Project)

fun Project.features(config: Features.() -> Unit) {
    Features(this).apply(config)
}

fun Features.serialization(version: String) {
    project.pluginManager.apply("org.jetbrains.kotlin.plugin.serialization")
    project.addMppDependency("org.jetbrains.kotlinx:kotlinx-serialization-json:$version")
}

fun Features.logging(version: String? = null) = with(project) {
    val loggingVersion = version ?: "0.0.1-SNAPSHOT"
    log("Configuring logging")
    addMppDependency("com.monolyte.monolog:monolog-core:$loggingVersion")
}

private fun Features.dateTime(version: String? = null) = with(project) {
    log("Configuring datetime")
    addMppDependency("org.jetbrains.kotlinx:kotlinx-datetime:${version ?: "0.4.0"}")
}
