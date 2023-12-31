pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
        maven {
            url = uri("http://mobile.jopit.com.ar:8081/repository/maven-releases/")
            isAllowInsecureProtocol = true
            credentials {
                username = System.getenv("NEXUS_JOPIT_USER")
                password = System.getenv("NEXUS_JOPIT_PASSWORD")
            }
        }
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven {
            url = uri("http://mobile.jopit.com.ar:8081/repository/maven-releases/")
            isAllowInsecureProtocol = true
            credentials {
                username = System.getenv("NEXUS_JOPIT_USER")
                password = System.getenv("NEXUS_JOPIT_PASSWORD")
            }
        }
        maven {
            url = uri("http://mobile.jopit.com.ar:8081/repository/maven-experimental/")
            isAllowInsecureProtocol = true
            credentials {
                username = System.getenv("NEXUS_JOPIT_USER")
                password = System.getenv("NEXUS_JOPIT_PASSWORD")
            }
        }
        versionCatalogs {
            create("jopitCatalog") {
                from("com.gperre.jopit:jopit-version-catalog:+")
            }
        }
    }
}

rootProject.name = "jopit-architecture-components"
include(":architecture-components-android")
include(":architecture-components-shared")
