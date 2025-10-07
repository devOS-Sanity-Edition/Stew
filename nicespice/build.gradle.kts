repositories {
    maven("https://jitpack.io")
}

dependencies {
    modImplementation(rootProject.common.fabric.language.kotlin)
    include(implementation(nicespice.brigadier.kotlin.get())!!)
}
