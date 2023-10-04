@file:Suppress("UnstableApiUsage")
import com.gperre.jopit.android.gradle.dependencies.*

plugins {
    id("com.android.library")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
    id("jopit.configuration")
}

android {
    namespace = "com.gperre.jopit.architecture.components.android"
    compileSdk = AndroidSdk.compile

    defaultConfig {
        minSdk = AndroidSdk.min
        targetSdk = AndroidSdk.target
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    hilt {
        enableAggregatingTask = true
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    sourceSets["main"].java {
        srcDir("src/main/java")
    }

    packagingOptions {
        jniLibs.excludes.add("META-INF/licenses/**")
        jniLibs.excludes.add("META-INF/AL2.0")
        jniLibs.excludes.add("META-INF/LGPL2.1")
    }
}

dependencies {
    implementation(Okhttp.interceptors)
    implementation(Okhttp.core)

    implementation(Gson.gson)

    implementation(Retrofit.retrofit)
    implementation(Retrofit.converterGson)
}
