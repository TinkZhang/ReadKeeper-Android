plugins {
    id("com.android.application")
    id("kotlin-android")
    id("com.google.gms.google-services")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()

    packagingOptions {
        excludes.add("META-INF/AL2.0")
        excludes.add("META-INF/LGPL2.1")
    }

    defaultConfig {
        applicationId = "com.github.tinkzhang.readkeeper"
        minSdk = 24
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.asProvider().get()
    }
}

dependencies {

    // Child-Modules
    implementation(project(mapOf("path" to ":common:basic")))
    implementation(project(mapOf("path" to ":common:firebaseRemoteConfig")))
    implementation(project(mapOf("path" to ":common:uicomponent")))
    implementation(project(mapOf("path" to ":feature:reading")))
    implementation(project(mapOf("path" to ":feature:wish")))
    implementation(project(mapOf("path" to ":feature:homepage")))
    implementation(project(mapOf("path" to ":feature:search")))
    implementation(project(mapOf("path" to ":feature:archived")))

    // Core
    implementation(libs.androidCore)
    implementation(libs.appCompat)
    implementation(libs.material)

    // Compose
    implementation(libs.bundles.androidx.compose)
    androidTestImplementation(libs.composeUiTestJunit)
    debugImplementation(libs.composeUiTest)

    // Firebase
    implementation(libs.bundles.google.login)

    // Timber
    implementation(libs.timber)

    // Instabug
    implementation(libs.instabug)

    // Google Ad
    implementation(libs.googleAd)

    // SplashScreen
    implementation(libs.splashScreen)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.testing)

    // Hilt
    implementation(libs.bundles.hilt)
    kapt(libs.bundles.hiltComplier)

    // Room
    implementation(libs.room)
    implementation(libs.roomKtx)
    kapt(libs.roomKotlin)

    // Datastore
    implementation(libs.datastore)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}
