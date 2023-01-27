package com.hibernix.tools.setup.platforms

import com.hibernix.tools.setup.Versions
import com.hibernix.tools.setup.core.kotlinMultiplatform
import com.hibernix.tools.setup.core.projectProperties
import org.gradle.api.Project
import org.gradle.kotlin.dsl.invoke

/**
 * Main configuration wrapper for multiplatform targets and other related settings
 */

class Platforms(val project: Project)

fun Project.platforms(config: Platforms.() -> Unit) {

    group = projectProperties.group
    version = projectProperties.version

    pluginManager.apply("org.jetbrains.kotlin.multiplatform")

    val coroutinesVersion = Versions.Coroutines.fromProject(this)

    kotlinMultiplatform {
        sourceSets {
            maybeCreate("commonMain").dependencies {
                api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
            }
            maybeCreate("commonTest").dependencies {
                api("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
            }
        }
    }

    Platforms(this).apply(config)
}
