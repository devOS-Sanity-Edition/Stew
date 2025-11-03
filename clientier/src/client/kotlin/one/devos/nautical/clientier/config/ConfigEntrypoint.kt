package one.devos.nautical.clientier.config

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi

// why the fuck isnt this just autohandled by Vigilance, what the fuck Essential devs lmao
class ConfigEntrypoint : ModMenuApi {
    override fun getModConfigScreenFactory() = ConfigScreenFactory { _ ->
        Config.gui()
    }
}