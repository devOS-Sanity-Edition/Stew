package one.devos.nautical.clientier.titlebar

import me.saharnooby.qoi.QOIDecoder
import me.saharnooby.qoi.QOIImage
import me.saharnooby.qoi.QOIUtilAWT
import net.minecraft.client.Minecraft
import one.devos.nautical.clientier.Clientier
import one.devos.nautical.clientier.config.Config
import one.devos.nautical.clientier.utils.base64ToBrotliInputStream
import one.devos.nautical.clientier.utils.brotliDecodedByteArrayToQOIDecodableInputStream
import one.devos.nautical.clientier.utils.brotliToByteArray
import org.brotli.dec.BrotliInputStream
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWImage
import org.lwjgl.stb.STBImage
import org.lwjgl.system.MemoryStack
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.nio.ByteBuffer
import javax.imageio.ImageIO
import kotlin.use

object QOIWindowIcon {
    fun QOIImageToByteArray(qoiImage: QOIImage): ByteArray { // QOI
        val outputStream = ByteArrayOutputStream()

        val bufferedImage = QOIUtilAWT.convertToBufferedImage(qoiImage)
        ImageIO.write(bufferedImage, "PNG", outputStream)
        return outputStream.toByteArray()
    }

    fun loadIconBytes(byteArray: ByteArray): ByteBuffer? {
        val buffer = ByteBuffer.allocateDirect(byteArray.size).put(byteArray).flip()
        val icon = STBImage.stbi_load_from_memory(buffer, intArrayOf(32), intArrayOf(32), intArrayOf(4), 4)

        return icon
    }

    fun setWindowIcon(windowHandle: Long, qoiImageByteArrayOutput: ByteArray) {
        var icon: ByteBuffer? = ByteBuffer.allocate(0)

        try {
            MemoryStack.stackPush().use { stack ->
                icon = loadIconBytes(qoiImageByteArrayOutput) // @TODO whYYYYYYYY

                val reason = STBImage.stbi_failure_reason()
                if (reason !== null) Clientier.LOGGER.error("[Clientier] Failed to load image from memory, time to blow my brains out: $reason")

                if (icon == null) { return }

                GLFWImage.malloc(1).use { icons ->
                    val iconImage = icons.get(0)
                    iconImage.set(32, 32, icon!!)
                    GLFW.glfwSetWindowIcon(windowHandle, icons)
                }
            }
        } catch (e: Exception) {
            Clientier.LOGGER.error("[Clientier] Failed to set window icon from QOI Image Data: $e")
        } finally {
            if (icon != null) {
                STBImage.stbi_image_free(icon)
            }
        }
    }

    fun setIcon(byteArray: ByteArray) {
        val gameWindowHandle: Long = Minecraft.getInstance().window.handle()
        setWindowIcon(gameWindowHandle, byteArray)
    }

    fun okayLetsDoThisShit() {
        val step1 = Config.modpackQOIBase64Data
        val step2: BrotliInputStream = base64ToBrotliInputStream(step1)
        val step3: ByteArray = brotliToByteArray(step2)
        val step4: InputStream = brotliDecodedByteArrayToQOIDecodableInputStream(step3)
        val step5: QOIImage = QOIDecoder.decode(step4, 4)
        val step6: ByteArray = QOIImageToByteArray(step5)
        val step7 = setIcon(step6)
    }
}