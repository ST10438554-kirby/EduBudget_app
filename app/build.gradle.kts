plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.edubudget"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.edubudget"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    // Core Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.material)

    // Lifecycle
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // ---------------- ROOM FIX ----------------
    implementation(libs.androidx.room.runtime)

    // IMPORTANT: ADD THIS (missing in your setup)
    implementation("androidx.room:room-ktx:2.6.1")

    // KSP compiler
    ksp("androidx.room:room-compiler:2.6.1")
}