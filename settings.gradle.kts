pluginManagement {
    includeBuild("build-logic")
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
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "GibInteraktifSosyalApp"
include(":app")

include(":core:designsystem")
include(":core:common")
include(":core:network")
include(":core:di")

include(":feature:tasks:domain")
include(":feature:tasks:data")
include(":feature:tasks:presentation")

include(":feature:shop:domain")
include(":feature:shop:data")
include(":feature:shop:presentation")

include(":feature:events:domain")
include(":feature:events:data")
include(":feature:events:presentation")

include(":feature:game:domain")
include(":feature:game:data")
include(":feature:game:presentation")
