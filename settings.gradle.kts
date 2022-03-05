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
include(":feature:wish")
include(":feature:archived")
include(":common:firebaseRemoteConfig")
include(":feature:homepage")
include(":feature:settings")
