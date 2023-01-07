package com.hibernix.configuration.mpp

import com.android.build.gradle.BaseExtension
import com.hibernix.configuration.jvm.configureFatJarTask
import javax.inject.Inject
import com.hibernix.configuration.utils.log
import com.hibernix.configuration.utils.projectProperties
import org.gradle.api.Project
import org.gradle.kotlin.dsl.invoke

open class MppConfigurationExtension @Inject constructor(
    private val project: Project
) {

    data class ModuleFeatures(
        var coroutines: Boolean = true,
        var serialization: Boolean = false,
        var publish: Boolean = false,
    )

    fun features(block: ModuleFeatures.() -> Unit) {
        val features = ModuleFeatures()
        block(features)
        setupFeatures(features)
    }

    /**
     * Creates and configures jvm target.
     * @param mainClass if defined, the resulting jar will be automatically considered as standalone executable
     * as "fatJar" with all dependencies bundled.
     */
    // TODO: Use separate config DSL
    fun jvm(mainClass: String? = null) {
        project.setupJvm(mainClass)
    }

    /**
     * Creates and configures android target.
     */
    fun android() {
        project.setupAndroid()
    }

     /**
     * Creates and configures JS target
     */
    fun js() {
        project.setupJs()
    }

    private fun Project.setupJvm(mainClass: String? = null) {
        log("Configuring JVM target ...")
        kotlinMultiplatform {
            jvm()
            mainClass?.let { configureFatJarTask(it) }
            sourceSets {
                maybeCreate("jvmCommonMain").dependsOn(getByName("commonMain"))
                maybeCreate("jvmCommonTest").dependsOn(getByName("commonTest"))

                maybeCreate("jvmMain").dependsOn(getByName("jvmCommonMain"))
                maybeCreate("jvmTest").dependsOn(getByName("jvmCommonTest"))
            }
        }
    }

    private fun Project.setupAndroid() {
        log("Configuring android target ...")
        project.pluginManager.apply("com.android.library")
        project.extensions.configure(BaseExtension::class.java) {
            compileSdkVersion(projectProperties.androidCompileSdk.toInt())
            defaultConfig {
                minSdk = projectProperties.androidMinSdk.toInt()
                targetSdk = projectProperties.androidTargetSdk.toInt()
            }
        }
        project.kotlinMultiplatform {
            android {
                publishLibraryVariants("release", "debug")
            }
            /*
                        sourceSets {
                            //maybeCreate("androidTest").dependencies { implementation(Deps.kotlin.test.junit) }
                        }
            */
        }
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

    private fun setupFeatures(features: ModuleFeatures) {
        if (features.coroutines) setupCoroutines()
        if (features.serialization) setupSerialization()
        if (features.publish) setupPublish()
    }

    private fun setupSerialization() = with(project) {
        log("Configuring serialization ...")
        pluginManager.apply("org.jetbrains.kotlin.plugin.serialization")
        addMppDependency("org.jetbrains.kotlinx:kotlinx-serialization-json:${projectProperties.serializationVersion}")
    }

    private fun setupCoroutines() = with(project) {
        log("Configuring coroutines ...")
        addMppDependency("org.jetbrains.kotlinx:kotlinx-coroutines-core:${projectProperties.coroutinesVersion}")
        addMppTestDependency("org.jetbrains.kotlinx:kotlinx-coroutines-test:${projectProperties.coroutinesVersion}")
    }

    private fun setupPublish() = with(project) {
        log("Enabling publish ...")
        project.pluginManager.apply("maven-publish")
    }
}
