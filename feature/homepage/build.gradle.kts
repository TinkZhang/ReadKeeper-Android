plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
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
    namespace = "app.tinks.readkeeper.homepage"
}

dependencies {
    implementation(project(mapOf("path" to ":common:basic")))
    implementation(project(mapOf("path" to ":common:uicomponent")))
    implementation(project(mapOf("path" to ":common:firebaseRemoteConfig")))

    // Compose
    implementation(libs.bundles.androidx.compose)
    androidTestImplementation(libs.composeUiTestJunit)
    debugImplementation(libs.composeUiTest)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.testing)

    // Room
    implementation(libs.room)
    implementation(libs.roomKtx)
    kapt(libs.roomKotlin)

    // Firebase
    implementation(libs.firebaseFirestore)

    // Google Ad
    implementation(libs.googleAd)

    // Datastore
    implementation(libs.datastore)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}