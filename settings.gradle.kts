dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "ReadKeeper"
include(":app")
include(":common:model")
include(":common:uicomponent")
include(":feature:reading")
