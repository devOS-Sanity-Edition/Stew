package one.devos.nautical.clientier

import gay.asoji.fmw.FMW
import net.fabricmc.api.ClientModInitializer
import net.minecraft.client.Minecraft
import one.devos.nautical.clientier.config.Config
import one.devos.nautical.clientier.utils.UnsupIni
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Clientier : ClientModInitializer {
    val MOD_ID: String = "clientier"
    val LOGGER: Logger = LoggerFactory.getLogger(MOD_ID)
    val MOD_NAME: String = FMW.getName(MOD_ID)

    override fun onInitializeClient() {
        if (UnsupIni.unsupIniFile.exists()) {
            run {
                Config.modpackTitle = UnsupIni.brandingTitle ?: return@run
                Config.modpackQOIBase64Data = UnsupIni.brandingQOIBase64String ?: return@run
            }
        }

        Minecraft.getInstance().execute { Minecraft.getInstance().window.setTitle(Config.modpackTitle) }

        LOGGER.info("[${MOD_NAME}] Clientier v${FMW.getVersion(MOD_ID)} loaded!")
        LOGGER.info("[${MOD_NAME}] No it's totally not Minecraft.. totally..")
    }
}