repositories {
    maven("https://repo.essential.gg/repository/maven-public") // Essential's UniversalCraft and Elementa
}

dependencies {
    modImplementation(rootProject.common.fabric.language.kotlin)
    modImplementation(rootProject.common.mod.menu.get())
    modImplementation(files("localLibs/Vigilance-312-CUSTOM.jar")) // bestie i am at my fucking limit
    include(implementation(clientier.essential.elementa.get())!!)
    include(modImplementation(clientier.essential.universalcraft.get())!!)
    include(implementation(clientier.ini4j.get())!!)
    include(implementation(clientier.qoi.main.get())!!)
    include(implementation(clientier.qoi.awt.get())!!)
    include(implementation(clientier.brotli.get())!!)
    include(implementation(clientier.jna.main.get())!!)
    include(implementation(clientier.jna.platform.get())!!)
}

tasks.remapJar {  // bestie i am at my fucking limit
    nestedJars.from(files("localLibs/Vigilance-312-CUSTOM.jar"))
}

// normal processResources doesnt process for client sourceset so we have to do this again lmao
// if theres a better way pls tell me :sakaPlead:
tasks.processClientResources {
    val fabricApiVersion = rootProject.common.fabric.api.get().version
    val fabricLanguageKotlinVersion = rootProject.common.fabric.language.kotlin.get().version
    val fabricLoaderVersion = rootProject.common.fabric.loader.get().version
    val minecraftVersion = rootProject.common.minecraft.get().version
    val javaVersion = rootProject.java.sourceCompatibility.majorVersion

    val properties: Map<String, Any> = mapOf(
        // mod vers
        "version" to project.version,

        // dependency vers
        "fabric_api" to ">=$fabricApiVersion",
        "fabric_language_kotlin" to ">=$fabricLanguageKotlinVersion",
        "fabric_loader" to ">=$fabricLoaderVersion",
        "java" to ">=$javaVersion",
        "minecraft" to "~$minecraftVersion",
    )

    inputs.properties(properties)

    filesMatching("fabric.mod.json") {
        expand(properties)
    }
}