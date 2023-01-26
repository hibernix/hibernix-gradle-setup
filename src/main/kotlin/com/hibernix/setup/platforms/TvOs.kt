package com.hibernix.setup.platforms

import com.hibernix.setup.core.kotlinMultiplatform
import com.hibernix.setup.core.log
import org.gradle.api.Project

fun Platforms.tvOs() {
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
