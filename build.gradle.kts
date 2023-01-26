plugins {
    `kotlin-dsl`
    id("com.gradle.plugin-publish") version "1.1.0"
    id("com.github.ben-manes.versions") version "0.44.0"
}

repositories {
    mavenCentral()
    gradlePluginPortal()
    google()
    mavenLocal()
}

group = "com.hibernix.tools"
version = "0.0.2-SNAPSHOT"

dependencies {
    // TODO: Version Catalog
    // compileOnly("com.android.tools.build:gradle:7.3.1")
    compileOnly("com.android.tools.build:gradle:8.0.0-alpha11")
    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0")
    compileOnly("com.github.ben-manes.versions:com.github.ben-manes.versions.gradle.plugin:0.44.0")
    compileOnly("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.22.0")
    compileOnly("org.jetbrains.dokka:dokka-gradle-plugin:1.7.20")
    compileOnly("org.jetbrains.dokka:dokka-core:1.7.20")
    implementation("org.jetbrains.dokka:dokka-base:1.7.20")
}

gradlePlugin {
    website.set("https://hibernix.com")
    plugins {
        register("hibernix-gradle-setup") {
            id = "com.hibernix.setup"
            displayName = "Root project configuration plugin"
            description = "Plugin for configuration of the whole project"
            implementationClass = "com.hibernix.setup.ProjectSetupPlugin"
        }
    }
}
