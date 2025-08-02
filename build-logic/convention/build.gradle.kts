plugins {
    `kotlin-dsl`
}

group = "com.ilsangtech.ilsang.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.compiler.gradle.plugin)
    compileOnly(libs.plugin.kotlin.serializationPlugin)
}

gradlePlugin {
    plugins {
        register("androidFeature") {
            id = "ilsang.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
    }
}