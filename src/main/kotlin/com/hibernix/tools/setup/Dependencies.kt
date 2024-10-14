package com.hibernix.tools.setup

/**
 *
 */

val depsPropertyPrefix = "versions."

sealed class Dependency {
}

class Lib(var version: String, val group: String, val name: String) {
        constructor( name: String) : this("","","") {}
    }


object Libs {

//kotlin
}

enum class Lxibs {

    kotlin(),



    ;
    object Versions {
        var kotlin = "2.0.0"
    }


}