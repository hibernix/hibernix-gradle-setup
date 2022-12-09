package com.hibernix.configuration.multiplatform

import org.gradle.api.Action
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

fun Project.addMppDependency(name: String) {
    dependencies.add("commonMainImplementation", name)
}

fun Project.addMppTestDependency(name: String) {
    dependencies.add("commonTestImplementation", name)
}

fun Project.kotlinMultiplatform(action: Action<KotlinMultiplatformExtension>) {
    extensions.configure(KotlinMultiplatformExtension::class.java, action)
}
