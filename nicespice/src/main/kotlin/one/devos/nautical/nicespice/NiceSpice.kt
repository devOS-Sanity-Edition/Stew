package one.devos.nautical.nicespice

import gay.asoji.fmw.FMW
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import net.minecraft.network.chat.Component
import net.minecraft.world.BossEvent
import one.devos.nautical.nicespice.commands.BossbarShenanigans
import one.devos.nautical.nicespice.commands.requestCommand
import one.devos.nautical.nicespice.commands.restartCommand
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object NiceSpice : ModInitializer {
    val MOD_ID: String = "nicespice"
    val LOGGER: Logger = LoggerFactory.getLogger(MOD_ID)
    val MOD_NAME: String = FMW.getName(MOD_ID)

    override fun onInitialize() {
        CommandRegistrationCallback.EVENT.register { dispatcher, registryAccess, environment ->
            requestCommand(dispatcher)
            restartCommand(dispatcher)
        }

        ServerLifecycleEvents.SERVER_STARTED.register { server ->
            BossbarShenanigans.dontSleepBossbar = server.customBossEvents.get(BossbarShenanigans.DONT_SLEEP_BOSSBAR_ID) ?: server.customBossEvents.create(
                BossbarShenanigans.DONT_SLEEP_BOSSBAR_ID, Component.empty())

            BossbarShenanigans.dontSleepBossbar.isVisible = false
            BossbarShenanigans.dontSleepBossbar.max = 12000
            BossbarShenanigans.dontSleepBossbar.color = BossEvent.BossBarColor.BLUE
        }

        ServerPlayConnectionEvents.JOIN.register { handler, sender, server ->
            BossbarShenanigans.dontSleepBossbar.addPlayer(handler.player)
        }
    }
}