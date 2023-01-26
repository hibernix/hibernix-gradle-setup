package com.hibernix.setup.platforms

import com.android.build.gradle.BaseExtension
import com.hibernix.setup.core.DefaultConfigHolder
import com.hibernix.setup.core.kotlinMultiplatform
import com.hibernix.setup.platforms.AndroidConfig.Companion.asDefault
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Android platform setup
 */
data class AndroidConfig(
    var minSdk: Int? = null,
    var targetSdk: Int? = null,
    var compileSdk: Int? = null,
) {
    companion object : DefaultConfigHolder<AndroidConfig>() {
        override fun newInstance() = AndroidConfig()
        override fun copyInstance(instance: AndroidConfig) = instance.copy()

        const val DEFAULT_ANDROID_MIN_SDK = 19
        const val DEFAULT_ANDROID_COMPILE_SDK = 33
        const val DEFAULT_ANDROID_TARGET_SDK = 33
    }
}

fun Project.defaultAndroidConfig(): AndroidConfig =
    AndroidConfig(
        minSdk = findProperty("project.android.minSdk")?.toString()?.toInt()
            ?: AndroidConfig.DEFAULT_ANDROID_MIN_SDK,
        targetSdk = findProperty("project.android.targetSdk")?.toString()?.toInt()
            ?: AndroidConfig.DEFAULT_ANDROID_TARGET_SDK,
        compileSdk = findProperty("project.android.compileSdk")?.toString()?.toInt()
            ?: AndroidConfig.DEFAULT_ANDROID_COMPILE_SDK,
    ).asDefault()

fun Project.setupAndroid(
    androidConfig: AndroidConfig? = null,
    block: (BaseExtension.() -> Unit)? = null,
) {

    val config = androidConfig ?: defaultAndroidConfig()

    pluginManager.apply("com.android.library")
    extensions.configure<BaseExtension> {

        compileSdkVersion(requireNotNull(config.compileSdk) { "Android compileSdk not defined!" })
        defaultConfig {
            minSdk = requireNotNull(config.minSdk) { "Android minSdk not defined!" }
            targetSdk = requireNotNull(config.targetSdk) { "Android targetSdk not defined!" }
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }

        tasks.withType<KotlinCompile> {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
        block?.invoke(this)
    }

    kotlinMultiplatform {
        android {
            publishLibraryVariants("release", "debug")
        }
        sourceSets {
            maybeCreate("jvmCommonMain").dependsOn(getByName("commonMain"))
            maybeCreate("jvmCommonTest").dependsOn(getByName("commonTest"))

            maybeCreate("androidMain").dependsOn(getByName("jvmCommonMain"))
            //maybeCreate("androidTest").dependsOn(getByName("jvmCommonTest"))
        }
    }
}

fun Platforms.android(
    androidConfig: AndroidConfig? = null,
    block: (BaseExtension.() -> Unit)? = null,
) = project.setupAndroid(androidConfig, block)
