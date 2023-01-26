package com.hibernix.setup.features

import com.hibernix.setup.core.isRootProject
import java.time.Year
import org.gradle.api.Project
import org.gradle.api.tasks.Delete
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.getByName
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.DokkaTask

fun Features.dokka() {
    project.setupDokka()
}

internal fun Project.setupDokka() {

    plugins.apply("org.jetbrains.dokka")

    if (!isRootProject) return

    tasks.withType<DokkaTask>().configureEach {
        pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
            footerMessage = "© ${Year.now().value} hibernix s.r.o."
        }
    }

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
