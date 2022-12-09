package com.hibernix.configuration.utils

import org.gradle.api.Project
import org.gradle.internal.impldep.org.bouncycastle.asn1.x500.style.RFC4519Style.name

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
