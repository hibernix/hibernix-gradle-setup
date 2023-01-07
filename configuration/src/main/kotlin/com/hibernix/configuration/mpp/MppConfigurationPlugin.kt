package com.hibernix.configuration.mpp

import com.hibernix.configuration.utils.log
import com.hibernix.configuration.utils.projectProperties
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * Plugin for configuration of a multiplatform module
 */
open class MppConfigurationPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        pluginManager.apply("org.jetbrains.kotlin.multiplatform")
        target.extensions.create("hxModule", MppConfigurationExtension::class.java, target)

        log("Project properties: $projectProperties")

        group = projectProperties.group
        version = projectProperties.version

        extensions.configure(KotlinMultiplatformExtension::class.java) {
            sourceSets {
                maybeCreate("commonMain").dependencies {
                }
                maybeCreate("commonTest").dependencies {
                    //implementation(Deps.kotlin.test.common)
                    //implementation(Deps.kotlin.test.annotationsCommon)
                }
            }
        }
    }
}
