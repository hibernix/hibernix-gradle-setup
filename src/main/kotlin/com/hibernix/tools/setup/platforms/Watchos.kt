package com.hibernix.tools.setup.platforms

import com.hibernix.tools.setup.core.kotlinMultiplatform
import com.hibernix.tools.setup.core.log
import org.gradle.api.Project

fun Platforms.watchos() {
    project.setupWatchOs()
}

private fun Project.setupWatchOs() {
    log("Configuring watchOS targets")
    kotlinMultiplatform {
        watchosArm64()
        watchosX64()
        watchosSimulatorArm64()
    }
}
