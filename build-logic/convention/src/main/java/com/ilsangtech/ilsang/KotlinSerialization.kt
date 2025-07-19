package com.ilsangtech.ilsang

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureKotlinSerialization() {
    with(pluginManager) {
        apply("kotlinx-serialization")
    }
    dependencies {
        "implementation"(findLibrary("kotlinx-serialization-json"))
    }
}