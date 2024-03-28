package com.hibernix.tools.setup

import org.gradle.api.Project

/**
 * Versions of components provided by plugin, which can be overridden by project using predefined properties.
 */
enum class Versions(var version: String, val property: String) {
    Kotlin("1.9.23", "project.versions.kotlin"),
    Ksp("1.9.23-1.0.19", "project.versions.ksp"),
    Coroutines("1.8.0", "project.versions.coroutines"),
    Serialization("1.6.3", "project.versions.serialization"),
    Datetime("0.6.0-RC.2", "project.versions.datetime"),
    Logging("0.1.0-SNAPSHOT", "project.versions.logging"),
    ;

    fun fromProject(project: Project): String =
        project.findProperty(property)?.toString() ?: version
}

object LibVersions {
    var kotlin = "1.9.23"
    var ksp = "1.9.23-1.0.19"
    var coroutines = "1.8.0"
    var serialization = "1.6.2"
    var datetime = "0.6.0-RC.2"
    var logging = "0.1.0-SNAPSHOT"
}

fun libVersions(init: LibVersions.() -> Unit) {
    init(LibVersions)
}
