// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.4.0-alpha01")
        classpath(libs.kotlin.gradlePlugin)
        // Downgrade it to workaround the bug https://issuetracker.google.com/issues/223240936
        classpath("com.google.gms:google-services:4.3.5")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.40.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")

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
            freeCompilerArgs  = listOf("-Xjvm-default=all")
        }
    }
}
