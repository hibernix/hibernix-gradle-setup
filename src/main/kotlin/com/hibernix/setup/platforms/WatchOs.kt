package com.hibernix.setup.platforms

import com.hibernix.setup.core.kotlinMultiplatform
import com.hibernix.setup.core.log
import org.gradle.api.Project

fun Platforms.watchOs() {
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
