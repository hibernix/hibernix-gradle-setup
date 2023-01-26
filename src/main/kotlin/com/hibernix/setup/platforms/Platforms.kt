package com.hibernix.setup.platforms

import com.hibernix.setup.core.kotlinMultiplatform
import com.hibernix.setup.core.projectProperties
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

    kotlinMultiplatform {
        sourceSets {
            maybeCreate("commonMain").dependencies {
                api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
            }
            maybeCreate("commonTest").dependencies {
                // TODO: deps kotlin.test.common and kotlin.test.annotationsCommon?
            }
        }
    }

    Platforms(this).apply(config)
}
