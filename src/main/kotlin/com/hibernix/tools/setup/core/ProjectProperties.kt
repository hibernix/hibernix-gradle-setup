package com.hibernix.tools.setup.core

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
)

private var projectProperties: ProjectProperties? = null

private fun getProjectProps(project: Project): ProjectProperties =
    projectProperties
        ?: retrieveProjectProperties(project).also { projectProperties = it }

private fun Project.projectProperty(id: String, default: String) = findProperty(id) as String? ?: default

fun retrieveProjectProperties(project: Project): ProjectProperties = with(project) {
    ProjectProperties(
        name = projectProperty("project.name", name),
        group = projectProperty("project.group", group.toString()),
        version = projectProperty("project.version", version.toString()),
        description = projectProperty("project.description", description ?: ""),

        kotlinVersion = projectProperty("project.versions.kotlin", "1.8.20"),
        coroutinesVersion = projectProperty("project.versions.coroutines", "1.6.4"),
        serializationVersion = projectProperty("project.versions.serialization", "1.5.0"),

        androidMinSdk = projectProperty("project.android.minSdk", "19"),
        androidTargetSdk = projectProperty("project.android.targetSdk", "33"),
        androidCompileSdk = projectProperty("project.android.compileSdk", "33"),
    )
}

val Project.projectProperties get() = getProjectProps(this)
