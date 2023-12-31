@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.library")
    id("jopit.configuration")
}

android {
    namespace = "com.gperre.jopit.architecture.components.android"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = jopitCatalog.versions.compose.get()
    }

    kotlinOptions.jvmTarget = jopitCatalog.versions.kotlinJavaTarget.get()

    sourceSets["main"].java {
        srcDir("src/main/java")
    }
}

dependencies {
    implementation(jopitCatalog.junit)
    implementation(jopitCatalog.lifecycle.scope)
    implementation(jopitCatalog.lifecycle.viewmodel)

    androidTestImplementation(jopitCatalog.robolectric)
    androidTestImplementation(jopitCatalog.test.core)
    androidTestImplementation(jopitCatalog.test.runner)

    implementation(jopitCatalog.hilt.core)
    implementation(jopitCatalog.hilt.navigationcompose)
    kapt(jopitCatalog.hilt.compiler)
    kapt(jopitCatalog.dagger.compiler)

    implementation(jopitCatalog.retrofit)
    implementation(jopitCatalog.gson.converter)
    implementation(jopitCatalog.gson)
    implementation(jopitCatalog.okhttp.core)
    implementation(jopitCatalog.okhttp.interceptors)
}
