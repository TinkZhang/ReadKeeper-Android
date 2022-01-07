dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "ReadKeeper"
include(":app")
include(":common:basic")
include(":common:uicomponent")
include(":feature:reading")
include(":feature:search")
enableFeaturePreview("VERSION_CATALOGS")
