allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io" ) }
        maven {
            url = uri("http://jopit.ddns.net:8081/repository/maven-releases/")
            isAllowInsecureProtocol = true
            credentials {
                username = "gperre"
                password = "1234QWERasdf,"
            }

        }
        maven {
            url = uri("http://jopit.ddns.net:8081/repository/maven-snapshots/")
            isAllowInsecureProtocol = true
            credentials {
                username = "gperre"
                password = "1234QWERasdf,"
            }
        }
        maven {
            url = uri("http://jopit.ddns.net:8081/repository/maven-experimental/")
            isAllowInsecureProtocol = true
            credentials {
                username = "gperre"
                password = "1234QWERasdf,"
            }
        }
    }
}
