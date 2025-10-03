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