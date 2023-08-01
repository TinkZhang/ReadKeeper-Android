plugins {
    id("com.android.application")
    id("kotlin-android")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    kotlin("kapt")
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()

    packagingOptions {
        excludes.add("META-INF/AL2.0")
        excludes.add("META-INF/LGPL2.1")
    }

    defaultConfig {
        applicationId = "app.tinks.readkeeper"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 13
        versionName = "1.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
    namespace = "app.tinks.readkeeper"
}

dependencies {

    // Child-Modules
    implementation(project(mapOf("path" to ":common:basic")))
    implementation(project(mapOf("path" to ":common:firebaseRemoteConfig")))
    implementation(project(mapOf("path" to ":common:uicomponent")))
    implementation(project(mapOf("path" to ":feature:homepage")))
    implementation(project(mapOf("path" to ":feature:search")))
    implementation(project(mapOf("path" to ":feature:settings")))

    // Core
    implementation(libs.androidCore)
    implementation(libs.appCompat)
    implementation(libs.material)

    // Compose
    implementation(libs.bundles.androidx.compose)
    implementation(libs.navigation.testing)
    androidTestImplementation(libs.composeUiTestJunit)
    debugImplementation(libs.composeUiTest)

    // Firebase
    implementation(libs.bundles.google.login)

    // Timber
    implementation(libs.timber)

    // Google Ad
    implementation(libs.googleAd)

    // SplashScreen
    implementation(libs.splashScreen)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.testing)

    // Room
    implementation(libs.room)
    implementation(libs.roomKtx)
    kapt(libs.roomKotlin)

    // Datastore
    implementation(libs.datastore)

    // Accompanist SystemUI Controller
    implementation(libs.accompanistSystemUiController)

    // Crashlytics
    implementation("com.google.firebase:firebase-crashlytics-ktx:18.3.7")
    implementation("com.google.firebase:firebase-analytics-ktx:21.3.0")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}
