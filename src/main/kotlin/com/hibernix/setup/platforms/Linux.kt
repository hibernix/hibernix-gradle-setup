package com.hibernix.setup.platforms

import com.hibernix.setup.core.kotlinMultiplatform
import com.hibernix.setup.core.log
import org.gradle.api.Project

/**
 */

fun Platforms.linux() {
    project.setupLinux()
}

private fun Project.setupLinux() {
    log("Configuring linux target")
    kotlinMultiplatform {
        linuxX64()
    }
}
