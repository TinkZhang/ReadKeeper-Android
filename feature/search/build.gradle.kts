plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
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
}

dependencies {
    implementation(project(mapOf("path" to ":common:basic")))
    implementation(project(mapOf("path" to ":common:uicomponent")))

    // Compose
    implementation(libs.bundles.androidx.compose)
    androidTestImplementation(libs.composeUiTestJunit)
    debugImplementation(libs.composeUiTest)

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Paging
    implementation(libs.bundles.paging)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.testing)

    // Firebase
    implementation(libs.firebaseFirestore)

    // Retrofit
    implementation(libs.bundles.retrofit)

    // Google Ad
    implementation(libs.googleAd)
    
    // Timber
    implementation(libs.timber)
}