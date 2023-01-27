package com.hibernix.tools.setup.features

import com.hibernix.tools.setup.core.isRootProject
import org.gradle.api.Project
import org.gradle.api.tasks.Delete
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.getByName
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.register
import org.jetbrains.dokka.gradle.DokkaTask

fun Features.dokka() {
    project.setupDokka()
}

internal fun Project.setupDokka() {

    plugins.apply("org.jetbrains.dokka")

    if (!isRootProject) return

    val dokkaOutputDir = "$buildDir/dokka"

    tasks.getByName<DokkaTask>("dokkaHtml") {
        dokkaSourceSets.maybeCreate("jvmCommonTest")
        outputDirectory.set(file(dokkaOutputDir))
    }

    val deleteDokkaOutputDir by tasks.register<Delete>("deleteDokkaOutputDirectory") {
        delete(dokkaOutputDir)
    }

    tasks.register<Jar>("javadocJar") {
        dependsOn(deleteDokkaOutputDir, tasks.getByName("dokkaHtml"))
        archiveClassifier.set("javadoc")
        from(dokkaOutputDir)
    }
}
