package com.hibernix.setup.platforms

import com.hibernix.setup.core.kotlinMultiplatform
import com.hibernix.setup.core.log
import org.gradle.api.Project

fun Platforms.ios() {
    project.setupIos()
}

private fun Project.setupIos() {
    log("Configuring ios targets")
    kotlinMultiplatform {
        iosArm64()
        iosX64()
        iosSimulatorArm64()
    }
}
