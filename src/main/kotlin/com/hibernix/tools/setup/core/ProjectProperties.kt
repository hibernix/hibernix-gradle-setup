package com.hibernix.tools.setup.core

import com.hibernix.tools.setup.Versions
import org.gradle.api.Project

/**
 * Manages common global project properties
 */
data class ProjectProperties(
    val name: String,
    val group: String,
    val version: String,
    val description: String,

    val kotlinVersion: String,
    val coroutinesVersion: String,
    val serializationVersion: String,

    val androidMinSdk: String,
    val androidTargetSdk: String,
    val androidCompileSdk: String,

    /** Custom properties */
    val properties: MutableMap<String, Any?> = mutableMapOf()
)

private var projectProperties: ProjectProperties? = null

private fun getProjectProps(project: Project): ProjectProperties =
    projectProperties
        ?: retrieveProjectProperties(project).also { projectProperties = it }

fun Project.projectProperty(id: String, default: String) = findProperty(id) as String? ?: default

fun retrieveProjectProperties(project: Project): ProjectProperties = with(project) {
    ProjectProperties(
        name = projectProperty("project.name", name),
        group = projectProperty("project.group", group.toString()),
        version = projectProperty("project.version", version.toString()),
        description = projectProperty("project.description", description ?: ""),

        kotlinVersion = projectProperty(Versions.Kotlin.property, Versions.Kotlin.version),
        coroutinesVersion = projectProperty("project.versions.coroutines", Versions.Coroutines.version),
        serializationVersion = projectProperty("project.versions.serialization", Versions.Serialization.version),

        androidMinSdk = projectProperty("project.android.minSdk", "21"),
        androidTargetSdk = projectProperty("project.android.targetSdk", "34"),
        androidCompileSdk = projectProperty("project.android.compileSdk", "34"),
    )
}

val Project.projectProperties get() = getProjectProps(this)
