plugins {
    `kotlin-dsl`
    //id("org.gradle.kotlin.kotlin-dsl") version "4.0.0-rc-2"
    id("com.gradle.plugin-publish") version "1.1.0"
    id("com.github.ben-manes.versions") version "0.44.0"
}

repositories {
    mavenLocal()
    mavenCentral()
    gradlePluginPortal()
    google()
}

group = "com.hibernix"
version = "0.0.1-SNAPSHOT"

dependencies {
    // TODO: Version Catalog
    // compileOnly("com.android.tools.build:gradle:7.3.1")
    compileOnly("com.android.tools.build:gradle:8.0.0-alpha11")
    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0")
    compileOnly("com.github.ben-manes.versions:com.github.ben-manes.versions.gradle.plugin:0.44.0")
}

gradlePlugin {
    website.set("https://hibernix.com")
    plugins {
        register("hxProject") {
            id = "com.hibernix.project"
            displayName = "Root project configuration plugin"
            description = "Plugin for configuration of the whole project"
            implementationClass = "com.hibernix.configuration.project.ProjectConfigurationExtension"
        }
        register("hxMppModule") {
            id = "com.hibernix.mpp.lib"
            displayName = "Multiplatform lib module configuration plugin"
            description = "Plugin for common multiplatform module/lib"
            implementationClass = "com.hibernix.configuration.mpp.MppConfigurationPlugin"
        }
    }
}
