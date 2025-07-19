import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.ilsangtech.ilsang.configureCoilCompose
import com.ilsangtech.ilsang.configureComposeAndroid
import com.ilsangtech.ilsang.configureHiltAndroid
import com.ilsangtech.ilsang.findLibrary
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            configureComposeAndroid()
            configureHiltAndroid()
            configureCoilCompose()

            pluginManager.apply {
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            extensions.configure<LibraryExtension> {
                buildFeatures {
                    buildConfig = true
                }

                buildTypes {
                    val releaseImageUrl =
                        gradleLocalProperties(rootDir, providers).getProperty("RELEASE_IMAGE_URL")
                    val debugImageUrl =
                        gradleLocalProperties(rootDir, providers).getProperty("DEBUG_IMAGE_URL")
                    release {
                        isMinifyEnabled = false
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                        buildConfigField("String", "IMAGE_URL", releaseImageUrl)
                    }
                    debug {
                        buildConfigField("String", "IMAGE_URL", debugImageUrl)
                    }
                }
            }

            dependencies {
                "implementation"(project(":core:designsystem"))
                "implementation"(project(":core:model"))
                "implementation"(project(":core:domain"))
                "implementation"(project(":core:util"))

                "implementation"(findLibrary("hilt.navigation.compose").get())
                "implementation"(findLibrary("androidx.navigation.compose").get())
            }
        }
    }
}