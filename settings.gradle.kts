pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        google()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
        mavenLocal()
    }
/*
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
*/
}

rootProject.name = "hibernix-gradle-setup"
