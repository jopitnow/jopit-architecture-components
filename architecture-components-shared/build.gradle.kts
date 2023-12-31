@file:Suppress("UnstableApiUsage")

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("jopit.configuration")
}

android {
    configurations {
        create("androidTestApi")
        create("androidTestDebugApi")
        create("androidTestReleaseApi")
        create("testApi")
        create("testDebugApi")
        create("testReleaseApi")
    }
}

kotlin {
    androidTarget()

    ios {
        binaries {
            framework {
                baseName = "shared"
            }
        }
    }
}

android {
    namespace = "com.gperre.jopit.architecture.components"
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
}
