dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
    }

    versionCatalogs {
        create("common") {
            from(files("gradle/common.versions.toml"))
        }

        create("nicespice") {
            from(files("gradle/nicespice.versions.toml"))
        }

        create("clientier") {
            from(files("gradle/clientier.versions.toml"))
        }
    }
}

pluginManagement {
    repositories {
        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net/")
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    // Use the Foojay Toolchains plugin to automatically download JDKs required by subprojects.
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "Stew"

include("clientier")
include("creepermultidrop")
include("dudewhatsmygepeuwu")
include("nicespice")
include("rainbethunder")