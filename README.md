# Gradle plugin for basic projects setup

Basic project gradle configuration / setup

### Usage

```kotlin
plugins {
    id("com.hibernix.tools.setup")
}

platforms {
    // all platform targets
    /* if optional mainClass is defined, it will be set up as runnable java app and will bundle all dependencies into the (fat)jar */
    jvm(mainClass = "com.hibernix.PathToMainClassKt")
    android { /* custom configuration in android extension context */ }
    ios()
    macos()
    linux()
    tvos()
    watchos()
    js()
}

features {
    serialization() // modules that require kotlinx.serialization
    detekt() // root and modules
    dokka() // for root and all modules that require documentation
    publish() // for all modules that should be published
    dependencyUpdates() // only for root
}
```

### Global configuration properties

These gradle properties are supported by the plugin:
```properties
# mandatory base project properties
project.name=Project Name
project.group=com.hibernix.group
project.description=Project Description
project.version=0.0.1-SNAPSHOT

# overrides the default versions of plugin
project.versions.kotlin=...
project.versions.coroutines=...
project.versions.detekt=...
project.versions.dokka=...
project.versions.dependencyUpdates=...

# possible android properties
project.android.minSdk=19
project.adnroid.compileSdk=33
project.android.targetSdk=33
```
