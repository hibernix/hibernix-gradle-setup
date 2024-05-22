package com.hibernix.tools.setup

import org.gradle.api.Project

/**
 * Versions of components provided by plugin, which can be overridden by project using predefined properties.
 */
enum class Versions(var version: String, val property: String) {
    Kotlin("2.0.0", "project.versions.kotlin"),
    Ksp("2.0.0-1.0.21", "project.versions.ksp"),
    Coroutines("1.8.1", "project.versions.coroutines"),
    Serialization("1.7.0-RC", "project.versions.serialization"),
    Datetime("0.6.0", "project.versions.datetime"),
    Logging("0.1.0-SNAPSHOT", "project.versions.logging"),
    ;

    fun fromProject(project: Project): String =
        project.findProperty(property)?.toString() ?: version
}

object LibVersions {
    var kotlin = "2.0.0"
    var ksp = "2.0.0-1.0.21"
    var coroutines = "1.8.1"
    var serialization = "1.7.0-RC"
    var datetime = "0.6.0"
    var logging = "0.1.0-SNAPSHOT"
}

fun libVersions(init: LibVersions.() -> Unit) {
    init(LibVersions)
}
