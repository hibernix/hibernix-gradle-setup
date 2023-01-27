package com.hibernix.tools.setup.platforms

import com.hibernix.tools.setup.core.kotlinMultiplatform
import com.hibernix.tools.setup.core.log
import org.gradle.api.Project

fun Platforms.tvos() {
    project.setupTvOs()
}

private fun Project.setupTvOs() {
    log("Configuring tvOS targets")
    kotlinMultiplatform {
        tvosArm64()
        tvosX64()
        tvosSimulatorArm64()
    }
}
