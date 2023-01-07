# hibernix-plugins

Basic project configuration plugins / settings


### com.hibernix.project

<i>Can be applied to main/root project</i>

```kotlin
plugins {
    id("com.hibernix.project")
}

hxProject {
  // currently no configuration available
}
```

### com.hibernix.mpp.lib

<i>Can be applied to any MPP module</i>

```kotlin
plugins {
    id("com.hibernix.mpp.lib")
}

hxModule {
    features {
        coroutines = true
        serialization = true
        publish = true
    }

    // define targets (currently only these supported)

    /* if optional mainClass is defined, it will be set up as runnable java app and will bundle all dependencies into the (fat)jar */
    jvm(mainClass = "com.hibernix.PathToMainClassKt")
    android()
    js()
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
