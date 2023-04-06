plugins {
    val publishVersion: String by System.getProperties()
    val updateDepsVersion: String by System.getProperties()
    `kotlin-dsl`
    id("com.gradle.plugin-publish") version publishVersion
    id("com.github.ben-manes.versions") version updateDepsVersion
    signing
}

repositories {
    mavenCentral()
    gradlePluginPortal()
    google()
    mavenLocal()
}

group = requireNotNull(property("project.group")) { "No groupId declared in 'project.group' property" }
version = requireNotNull(property("project.version")) { "No project version declared in 'project.version'" }

dependencies {
    val agpVersion: String by project
    val kotlinVersion: String by project
    val detektVersion: String by project
    val dokkaVersion: String by project
    val updateDepsVersion: String by System.getProperties()

    // compileOnly("com.android.tools.build:gradle:7.3.1")
    compileOnly("com.android.tools.build:gradle:$agpVersion")
    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    compileOnly("com.github.ben-manes.versions:com.github.ben-manes.versions.gradle.plugin:$updateDepsVersion")
    compileOnly("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:$detektVersion")
    compileOnly("org.jetbrains.dokka:dokka-gradle-plugin:$dokkaVersion")
    compileOnly("org.jetbrains.dokka:dokka-core:$dokkaVersion")
    //implementation("org.jetbrains.dokka:dokka-base:$dokkaVersion")
}

gradlePlugin {
    website.set("https://github.com/hibernix/hibernix-gradle-setup")
    vcsUrl.set("https://github.com/hibernix/hibernix-gradle-setup.git")
    plugins {
        register("hibernix-gradle-setup") {
            id = "com.hibernix.tools.setup"
            displayName = "Multiplatform project setup plugin"
            tags.set(listOf("kotlin-multiplatform", "kotlin"))
            description = "Convenience gradle plugin for basic multiplatform project setup"
            implementationClass = "com.hibernix.tools.setup.ProjectSetupPlugin"
        }
    }
}

val javadocJar = tasks.register<Jar>("javadocJar") {
    //dependsOn(deleteDokkaOutputDir, tasks.dokkaHtml)
    archiveClassifier.set("javadoc")
    //from(dokkaOutputDir)
}

publishing {
    repositories {
        maven {
            name = "Release"
            setUrl { "https://hibernix.jfrog.io/artifactory/release" }
            credentials {
                username = System.getenv("SONATYPE_USERNAME")
                password = System.getenv("SONATYPE_PASSWORD")
            }
        }
        maven {
            name = "Snapshot"
            setUrl { "https://hibernix.jfrog.io/artifactory/snapshot" }
            credentials {
                username = System.getenv("SONATYPE_USERNAME")
                password = System.getenv("SONATYPE_PASSWORD")
            }
        }
    }
    publications {
        withType<MavenPublication> {
            //artifact(tasks["javadocJar"])

            pom {
                name.set(project.name)
                description.set("Multiplatform project setup plugin")
                url.set("https://github.com/hibernix/hibernix-gradle-setup")

                licenses {
                    license {
                        name.set("Apache License 2.0")
                        url.set("https://github.com/hibernix/hibernix-gradle-setup/LICENSE")
                    }
                }
                scm {
                    url.set("https://github.com/hibernix/hibernix-gradle-setup")
                    connection.set("scm:git:git://github.com/hibernix/hibernix-gradle-setup.git")
                }
                developers {
                    developer {
                        name.set("hibernix")
                        url.set("https://hibernix.com")
                    }
                }
            }
        }
    }
}

signing {
/*
    useInMemoryPgpKeys(
        System.getenv("GPG_PRIVATE_KEY"),
        System.getenv("GPG_PRIVATE_PASSWORD")
    )
*/
    sign(publishing.publications)
}
