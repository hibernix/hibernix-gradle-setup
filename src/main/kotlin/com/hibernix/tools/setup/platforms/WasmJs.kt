package com.hibernix.tools.setup.platforms

import com.hibernix.tools.setup.core.kotlinMultiplatform
import com.hibernix.tools.setup.core.log
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

/**
 *
 */
fun Platforms.wasmjs(name: String) {
    project.setupWasmJs(name)
}

private fun Project.setupWasmJs(name: String) {
    log("Configuring WasmJS target")
    kotlinMultiplatform {
        wasmJs() {
            moduleName = "name"
            browser {
                commonWebpackConfig {
                    outputFileName = "name.js"
                    devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                        static = (static ?: mutableListOf()).apply {
                            // Serve sources to debug inside browser
                            add(project.projectDir.path)
                            add(project.projectDir.path + "/commonMain/")
                            add(project.projectDir.path + "/wasmJsMain/")
                        }
                    }
                }
            }
            binaries.executable()
        }
    }
}
