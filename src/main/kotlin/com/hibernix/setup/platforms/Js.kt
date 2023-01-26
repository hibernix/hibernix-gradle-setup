package com.hibernix.setup.platforms

import com.hibernix.setup.core.kotlinMultiplatform
import com.hibernix.setup.core.log
import org.gradle.api.Project

/**
 *
 */
fun Platforms.js() {
    project.setupJs()
}

private fun Project.setupJs() {
    log("Configuring JS target")
    kotlinMultiplatform {
        js(IR) {
            browser {
                testTask {
                    useKarma {
                        useChromeHeadless()
                    }
                }
            }
            nodejs {
                testTask {
                    useCommonJs()
                }
            }
        }
    }
}
