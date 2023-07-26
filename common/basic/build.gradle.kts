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
    namespace = "app.tinks.readkeeper.basic"
}

dependencies {

    // Firebase
    implementation(libs.bundles.google.login)
    implementation(libs.bundles.firestore)
    implementation(libs.firebaseRemoteConfig)

    // Compose
    implementation(libs.bundles.androidx.compose)

    // Paging
    implementation(libs.pagingCompose)

    // Room
    implementation(libs.room)
    implementation(libs.roomKtx)
    implementation(libs.roomPaging)
    kapt(libs.roomKotlin)

    // Timber
    implementation(libs.timber)

    // Datastore
    implementation(libs.datastore)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.testing)
}