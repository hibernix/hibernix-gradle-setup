plugins {
    `kotlin-dsl`
    id("com.gradle.plugin-publish") version "1.1.0"
}

repositories {
    mavenLocal()
    mavenCentral()
    gradlePluginPortal()
    google()
}

group = "com.hibernix.build-config"
version = "0.0.1-SNAPSHOT"

dependencies {
    // TODO: Version Catalog
    compileOnly("com.android.tools.build:gradle:7.3.1")
    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0-RC")
}

gradlePlugin {
    website.set("https://hibernix.com")
    plugins {
        register("mppLibrary") {
            id = "com.hibernix.multiplatform.lib"
            displayName = "Project build configuration plugin"
            description = "Plugin for common basic project build configuration and logic"
            implementationClass = "com.hibernix.configuration.multiplatform.MultiplatformConfigurationPlugin"
        }
    }
}
