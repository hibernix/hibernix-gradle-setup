package com.hibernix.setup.core

import org.gradle.api.Project

/**
 * Centralized logging for plugins and scripts
 */
/*
    TODO: logger class, configuration of styles, minLogLevel
 */

enum class LogLevel {
    DEBUG,
    INFO,
    WARN,
    ERROR,
}

fun Project.log(text: String, logLevel: LogLevel = LogLevel.INFO) {
    println("[$name] >>> $text")
}
