// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.0.0-alpha03")
        classpath(libs.kotlin.gradlePlugin)
        classpath("com.google.gms:google-services:4.3.13")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.43.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

subprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            freeCompilerArgs = listOf(
                "-Xjvm-default=all",
                "-opt-in=org.mylibrary.OptInAnnotation"
            )
        }
    }
}
