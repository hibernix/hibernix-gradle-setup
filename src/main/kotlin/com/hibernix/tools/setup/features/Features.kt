package com.hibernix.tools.setup.features

import com.hibernix.tools.setup.Versions
import com.hibernix.tools.setup.core.addMppDependency
import com.hibernix.tools.setup.core.log
import org.gradle.api.Project

/**
 * Base for basic features of projects
 */

class Features(val project: Project)

fun Project.features(config: Features.() -> Unit) {
    Features(this).apply(config)
}

fun Features.serialization(version: String? = null) {
    val serializationVersion = version ?: Versions.Serialization.fromProject(project)
    project.pluginManager.apply("org.jetbrains.kotlin.plugin.serialization")
    project.addMppDependency("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
}

fun Features.logging(version: String? = null) = with(project) {
    val loggingVersion = version ?: Versions.Logging.fromProject(project)
    log("Configuring logging")
    addMppDependency("com.monolyte.monolog:monolog-core:$loggingVersion")
}

private fun Features.dateTime(version: String? = null) = with(project) {
    val dateTimeVersion = version ?: Versions.Datetime.fromProject(project)
    log("Configuring datetime")
    addMppDependency("org.jetbrains.kotlinx:kotlinx-datetime:$dateTimeVersion")
}
