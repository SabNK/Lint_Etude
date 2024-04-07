dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal() //for dependency like org.jlleitschuh.gradle.ktlint
    }
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "build-logic"
include(":convention")
