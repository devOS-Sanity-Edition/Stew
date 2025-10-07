package one.devos.nautical.nicespice.commands

import net.minecraft.resources.ResourceLocation
import net.minecraft.server.bossevents.CustomBossEvent
import one.devos.nautical.nicespice.NiceSpice

object BossbarShenanigans {
    val DONT_SLEEP_BOSSBAR_ID = ResourceLocation.fromNamespaceAndPath(NiceSpice.MOD_ID, "dont_sleep")
    lateinit var dontSleepBossbar: CustomBossEvent
}