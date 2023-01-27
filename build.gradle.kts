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
