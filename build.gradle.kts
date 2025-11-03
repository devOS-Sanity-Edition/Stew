@file:Suppress("UnstableApiUsage")

import net.fabricmc.loom.api.LoomGradleExtensionAPI

plugins {
    kotlin("jvm") version "2.2.21" apply false
    java
    `maven-publish`

    alias(common.plugins.fabric.loom) apply false
}

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://api.modrinth.com/maven")
        maven("https://maven.bawnorton.com/releases") // MixinSquared
        maven("https://maven.parchmentmc.org") // Parchment Mappings
        maven("https://maven.terraformersmc.com/") // EMI
        maven("https://mvn.devos.one/snapshots") // FMW and other stuff
        maven("https://pkgs.dev.azure.com/djtheredstoner/DevAuth/_packaging/public/maven/v1") // DevAuth
    }
}

// hacks exist here, be warned.
subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "maven-publish")
    apply(plugin = "java")
    apply(plugin = "fabric-loom")

    val loom = project.extensions.getByName<LoomGradleExtensionAPI>("loom") // hack

    val annotationProcessor: Configuration by configurations.getting
    val implementation: Configuration by configurations.getting
    val include: Configuration by configurations.getting
    val mappings: Configuration by configurations.getting
    val minecraft: Configuration by configurations.getting
    val modImplementation: Configuration by configurations.getting
    val modLocalRuntime: Configuration by configurations.getting
    val modRuntimeOnly: Configuration by configurations.getting

    val fabricApiVersion = rootProject.common.fabric.api.get().version
    val fabricLanguageKotlinVersion = rootProject.common.fabric.language.kotlin.get().version
    val fabricLoaderVersion = rootProject.common.fabric.loader.get().version
    val minecraftVersion = rootProject.common.minecraft.get().version
    val javaVersion = 21

    group = "one.devos.nautical"
    version = getModVersion(project.name)

    dependencies {
        minecraft(rootProject.common.minecraft)

        mappings(loom.layered {
            officialMojangMappings()
            parchment(rootProject.common.parchment.get())
        })

        modImplementation(rootProject.common.fabric.loader)
        modImplementation(rootProject.common.fabric.api)

        include(modImplementation(rootProject.common.fmw.get())!!)

        modLocalRuntime(rootProject.common.devauth)
        modLocalRuntime(rootProject.common.bundles.devenv)
    }

    loom.splitEnvironmentSourceSets()
    loom.runs {
        afterEvaluate {
            configureEach {
                vmArg("-javaagent:${configurations.compileClasspath.get().find { it.name.contains("sponge-mixin") }}")
                vmArg("-XX:+IgnoreUnrecognizedVMOptions") // in the case the below doesnt work bc that JVM doesnt have it
                vmArg("-XX:+AllowEnhancedClassRedefinition")
                property("mixin.hotSwap", "true")
                property("mixin.debug.export", "true")
            }
        }
    }

    java {
        withSourcesJar()

        toolchain {
            languageVersion = JavaLanguageVersion.of(javaVersion)
        }
    }

    tasks.processResources {


        val properties: Map<String, Any> by lazy {
            mapOf(
                // mod vers
                "version" to project.version,

                // dependency vers
                "fabric_api" to ">=$fabricApiVersion",
                "fabric_language_kotlin" to ">=$fabricLanguageKotlinVersion",
                "fabric_loader" to ">=$fabricLoaderVersion",
                "java" to ">=${javaVersion}",
                "minecraft" to "~$minecraftVersion",
            )
        }

        inputs.properties(properties)

        filesMatching("fabric.mod.json") {
            expand(properties)
        }
    }


    tasks.jar {
        from("LICENSE") {
            rename { "${it}_${project.base.archivesName.get()}" }
        }
    }

    publishing {
        publications {
            create<MavenPublication>("mavenJava") {
                from(components["java"])
            }
        }

        repositories {
            listOf("Releases", "Snapshots").forEach {
                maven("https://mvn.devos.one/${it.lowercase()}") {
                    name = "devOS$it"
                    credentials(PasswordCredentials::class)
                }
            }
        }
    }
}

fun getModVersion(projectName: String): String {
    val modVersion = project.property("${projectName}_version").toString()
    val gitExitCode = providers.exec { commandLine("git", "--version"); isIgnoreExitValue = true }.result.get().exitValue

    if (gitExitCode == 0) { // 0 = git is installed, anything else, prob not.
        val buildId = providers.exec { commandLine("git", "rev-parse", "--short", "HEAD")}.standardOutput.asText.get().trim()
        val dirtyStateCmd = providers.exec { commandLine("git", "status", "--porcelain") }.standardOutput.asText.get().trim()

        fun dirtyStateText(): String {
            return if (dirtyStateCmd.isEmpty()) {
                ""
            } else {
                "-dirty"
            }
        }

        return "$modVersion+rev.$buildId${dirtyStateText()}"
    } else {
        return "$modVersion+unknown"
    }
}