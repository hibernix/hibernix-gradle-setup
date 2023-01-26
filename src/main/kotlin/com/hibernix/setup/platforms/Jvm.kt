package com.hibernix.setup.platforms

import com.hibernix.setup.core.kotlinMultiplatform
import com.hibernix.setup.core.log
import org.gradle.api.Project
import org.gradle.api.file.DuplicatesStrategy
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.withType

fun Platforms.jvm(mainClass: String? = null) {
    project.setupJvm(mainClass)
}

fun Project.setupJvm(mainClass: String? = null) {
    log("Configuring JVM target")
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

/**
 * Creates standalone "fat" jar with all dependencies bundled.
 */
fun Project.configureFatJarTask(mainClass: String) {

    tasks.withType<Jar> {
        manifest {
            attributes["Main-Class"] = mainClass
        }

        // To avoid the duplicate handling strategy error
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE

        // To add all of the dependencies
        //from(target.kotlinExt().sourceSets.getByName("jvmMain").output)

        dependsOn(configurations.getByName("jvmRuntimeClasspath"))
        from({
            configurations.getByName("jvmRuntimeClasspath").filter { it.name.endsWith("jar") }
                .map { zipTree(it) }
        })
    }
}

