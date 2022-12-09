package com.hibernix.configuration.jvm

import org.gradle.api.Project
import org.gradle.api.file.DuplicatesStrategy
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.withType

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
