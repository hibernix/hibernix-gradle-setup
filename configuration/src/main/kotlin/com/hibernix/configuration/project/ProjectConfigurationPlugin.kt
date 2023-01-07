package com.hibernix.configuration.project

import org.gradle.api.Plugin
import org.gradle.api.Project

class ProjectConfigurationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.extensions.create("hxProject", ProjectConfigurationExtension::class.java, target)
    }
}
