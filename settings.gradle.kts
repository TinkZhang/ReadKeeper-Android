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
include(":feature:search")
include(":common:firebaseRemoteConfig")
include(":feature:homepage")
include(":feature:settings")
