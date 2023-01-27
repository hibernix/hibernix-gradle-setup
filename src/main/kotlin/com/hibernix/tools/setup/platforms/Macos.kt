package com.hibernix.tools.setup.platforms

import com.hibernix.tools.setup.core.kotlinMultiplatform
import com.hibernix.tools.setup.core.log
import org.gradle.api.Project

fun Platforms.macos() {
    project.setupMacOs()
}

private fun Project.setupMacOs() {
    log("Configuring macOS target")
    kotlinMultiplatform {
        macosArm64()
    }
}
