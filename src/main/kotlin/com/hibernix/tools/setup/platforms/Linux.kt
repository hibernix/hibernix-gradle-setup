package com.hibernix.tools.setup.platforms

import com.hibernix.tools.setup.core.kotlinMultiplatform
import com.hibernix.tools.setup.core.log
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
