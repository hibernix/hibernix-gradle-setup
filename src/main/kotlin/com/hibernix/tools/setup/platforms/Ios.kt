package com.hibernix.tools.setup.platforms

import com.hibernix.tools.setup.core.kotlinMultiplatform
import com.hibernix.tools.setup.core.log
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
