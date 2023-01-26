package com.hibernix.setup.platforms

import com.hibernix.setup.core.kotlinMultiplatform
import com.hibernix.setup.core.log
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
