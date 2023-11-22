allprojects {
    repositories {
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
        maven {
            url = uri("http://mobile.jopit.com.ar:8081/repository/maven-experimental/")
            isAllowInsecureProtocol = true
            credentials {
                username = System.getenv("NEXUS_JOPIT_USER")
                password = System.getenv("NEXUS_JOPIT_PASSWORD")
            }
        }
    }
}
