package com.hibernix.tools.setup

import org.gradle.api.Project

/**
 * Versions of components provided by plugin, which can be overridden by project using predefined properties.
 */
enum class Versions(var version: String, val property: String) {
    Kotlin("2.0.20", "project.versions.kotlin"),
    Ksp("2.0.20-1.0.25", "project.versions.ksp"),
    Coroutines("1.9.0", "project.versions.coroutines"),
    Serialization("1.7.3", "project.versions.serialization"),
    Datetime("0.6.1", "project.versions.datetime"),
    Logging("0.2.1", "project.versions.logging"),
    ;

    fun fromProject(project: Project): String =
        project.findProperty(property)?.toString() ?: version
}

/*
object LibVersions {
    var kotlin = "2.0.0"
    var ksp = "2.0.0-1.0.22"
    var coroutines = "1.9.0-RC"
    var serialization = "1.7.2"
    var datetime = "0.6.1"
    var logging = "0.2.0"
}

fun libVersions(init: LibVersions.() -> Unit) {
    init(LibVersions)
}
*/
