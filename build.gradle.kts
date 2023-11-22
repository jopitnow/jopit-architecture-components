@Suppress("GradleDynamicVersion")
buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven {
            url = uri("http://mobile.jopit.com.ar:8081/repository/maven-releases/")
            isAllowInsecureProtocol = true
            credentials {
                username = System.getenv("NEXUS_JOPIT_USER")
                password = System.getenv("NEXUS_JOPIT_PASSWORD")
            }
        }
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.1.4")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.48")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")
        classpath("com.gperre.jopit:publish:1.0.14")
        classpath("com.gperre.jopit:configuration:1.0.17")
    }
}

apply("gradle/repositories.gradle.kts")

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

val projectGroup: String by project
val libraryVersion: String by project

allprojects {
    group = projectGroup
    version = libraryVersion
}
