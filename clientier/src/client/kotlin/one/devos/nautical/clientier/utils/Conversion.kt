package one.devos.nautical.clientier.utils

import me.saharnooby.qoi.QOIDecoder
import me.saharnooby.qoi.QOIUtil
import net.fabricmc.loader.api.FabricLoader
import one.devos.nautical.clientier.Clientier
import org.brotli.dec.BrotliInputStream
import java.io.File
import java.io.InputStream
import kotlin.io.encoding.Base64

// a shotgun is looking tasty right now
fun base64ToBrotliInputStream(base64String: String): BrotliInputStream {
    val decodedBase64 = Base64.decode(base64String)
    val brotliDecode = BrotliInputStream(decodedBase64.inputStream())

    return brotliDecode
}

fun brotliToByteArray(brotliInputStream: BrotliInputStream): ByteArray {
    return brotliInputStream.readBytes()
}

fun brotliDecodedByteArrayToQOIDecodableInputStream(brotliDecodedByteArray: ByteArray): InputStream {
    return brotliDecodedByteArray.inputStream()
}

// HOOOOOLY FUCKIN SHIT I DID IT I CANT FUCKING BELIEVE IT I CANT BELIEVE I DID IT I WROTE A REALLY SHITTY DECODER
fun altogetherNowTestFromBase64StringToFinalQOIImage(base64String: String) {
    val step1 = base64ToBrotliInputStream(base64String) // turn the base64 string into a brotli input stream
    val step2 = brotliToByteArray(step1) // turn the brotli input stream into a byte array
    val step3 = QOIDecoder.decode(brotliDecodedByteArrayToQOIDecodableInputStream(step2), 4) // decode the
    val step4 = QOIUtil.writeImage(step3, File(FabricLoader.getInstance().gameDir.toFile(), "test.qoi"))
    val step5 = Clientier.LOGGER.info("${QOIUtil.readImage(brotliDecodedByteArrayToQOIDecodableInputStream(step2)).width}, ${QOIUtil.readImage(brotliDecodedByteArrayToQOIDecodableInputStream(step2)).height}")
}