plugins {
    `kotlin-dsl`
}

repositories {
    mavenLocal()
    mavenCentral()
    gradlePluginPortal()
    google()
}

group = "com.hibernix.build-config"

gradlePlugin {
    plugins {
        register("mppLibrary") {
            id = "com.hibernix.multiplatform.lib"
            implementationClass = "com.hibernix.configuration.MultiplatformConfigurationPlugin"
        }
    }
}
