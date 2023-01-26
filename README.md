# hibernix-plugins

Basic project gradle configuration / setup

### Usage

```kotlin
plugins {
    id("com.hibernix.setup")
}

platforms {
    // all platform targets
    /* if optional mainClass is defined, it will be set up as runnable java app and will bundle all dependencies into the (fat)jar */
    jvm(mainClass = "com.hibernix.PathToMainClassKt")
    android { /* custom configuration in android extension context */ }
    ios()
    macOs()
    linux()
    tvOs()
    watchOs()
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

These gradle properties, when defined, will be respected and applied by plugins:
```properties
project.name=Project Name
project.group=com.hibernix.group
project.description=Project Description
project.version=0.0.1-SNAPSHOT

project.android.minSdk=19
project.adnroid.compileSdk=33
project.adroid.targetSdk=33
```
