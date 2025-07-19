package com.ilsangtech.ilsang

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureHiltAndroid() {
    with(pluginManager) {
        apply("com.google.devtools.ksp")
        apply("dagger.hilt.android.plugin")
    }
    dependencies {
        "implementation"(findLibrary("hilt.android"))
        "ksp"(findLibrary("hilt.android.compiler"))
    }
}