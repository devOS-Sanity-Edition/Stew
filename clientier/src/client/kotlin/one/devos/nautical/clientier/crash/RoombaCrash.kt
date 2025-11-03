package one.devos.nautical.clientier.crash

import net.fabricmc.loader.api.FabricLoader
import one.devos.nautical.clientier.Clientier
import java.io.File
import java.nio.file.Path
import java.util.*
import java.util.function.Consumer
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip

object RoombaCrash {
    val soundFile: Path = FabricLoader.getInstance().configDir.resolve("crash.wav")
    var clip: Clip? = null

    fun init() {
        getSound().ifPresent(Consumer { it: File? ->
            try {
                val audioInputStream: AudioInputStream = AudioSystem.getAudioInputStream(it)
                clip = AudioSystem.getClip()
                clip?.open(audioInputStream)

                if (clip!!.isOpen) {
                    Clientier.LOGGER.info("[Clientier] Roomba armed?")
                }
            } catch (exception: Exception) {
                Clientier.LOGGER.error("[Clientier] Failed to load audio $soundFile: $exception")
            }
        })
    }

    fun playRoombaFallingDownTheStairs() {
        if (clip != null) {
            clip?.start()
            try {
                Thread.sleep(clip!!.microsecondLength / 1000)
            } catch (e: Exception) {
                Clientier.LOGGER.error("[Clientier] Hm, couldn't play crash sound. ${e.message}")
            }
        }
    }

    fun getSound(): Optional<File> {
        try {
            return Optional.of(soundFile.toFile().absoluteFile)
        } catch (exception: Exception) {
            return Optional.empty()
        }
    }
}