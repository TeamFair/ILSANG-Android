pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ILSANG"
include(":app")
include(":core")
include(":feature")
include(":core:designsystem")
include(":feature:login")
include(":feature:tutorial")
include(":core:ui")
include(":feature:home")
include(":core:network")
include(":core:model")
include(":core:data")
include(":core:domain")
include(":core:util")
include(":core:datastore")
include(":feature:profile")
include(":feature:my")
