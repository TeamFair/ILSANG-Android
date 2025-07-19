package com.ilsangtech.ilsang

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureCoilCompose() {
    dependencies {
        "implementation"(findLibrary("coil.compose"))
        "implementation"(findLibrary("coil.network.okhttp"))
    }
}