package com.hibernix.tools.setup

import org.gradle.api.Project

/**
 * Versions of components provided by plugin, which can be overriden by project using predefined properties.
 */
enum class Versions(val version: String, val property: String) {
    Kotlin("1.8.0", "project.versions.kotlin"),
    Coroutines("1.6.4", "project.versions.coroutines"),
    Serialization("1.5.0-RC", "project.versions.serialization"),
    Datetime("0.4.0", "project.versions.datetime"),
    Logging("0.1.0-SNAPSHOT", "project.versions.logging"),
    ;

    fun fromProject(project: Project) : String =
        project.findProperty(property)?.toString() ?: version
}
