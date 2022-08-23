@Suppress("GradleDynamicVersion")
buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven {
            url = uri("http://jopit.ddns.net:8081/repository/maven-releases/")
            isAllowInsecureProtocol = true
            credentials {
                username = "gperre"
                password = "1234QWERasdf,"
            }
        }
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.2.2")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.42")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.0")
        classpath("com.gperre.jopit:publish:+")
        classpath("com.gperre.jopit:configuration:1.0.25")
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
