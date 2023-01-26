package com.hibernix.setup.core

import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

fun Project.addMppDependency(name: String) {
    dependencies.add("commonMainImplementation", name)
}

fun Project.addMppTestDependency(name: String) {
    dependencies.add("commonTestImplementation", name)
}

fun Project.addJvmDependency(name: String) {
    dependencies.add("jvmMainImplementation", name)
}

fun Project.addAndroidDependency(name: String) {
    dependencies.add("androidMainImplementation", name)
}

fun Project.addJsDependency(name: String) {
    dependencies.add("jsMainImplementation", name)
}

fun Project.addModuleDependency(name: String) {
    dependencies.add("commonMainImplementation", project(name))
}

fun Project.kotlinMultiplatform(action: KotlinMultiplatformExtension.() -> Unit) {
    extensions.configure<KotlinMultiplatformExtension>(action)
}

internal fun Project.ensureRootProject() {
    check(rootProject == this) { "Must be called on a root project" }
}

internal val Project.isRootProject get() = rootProject == this
