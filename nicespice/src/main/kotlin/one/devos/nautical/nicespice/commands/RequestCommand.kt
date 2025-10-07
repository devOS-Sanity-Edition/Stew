package one.devos.nautical.nicespice.commands

import com.mojang.brigadier.CommandDispatcher
import de.phyrone.brig.wrapper.literal
import de.phyrone.brig.wrapper.runs
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.ChatFormatting
import net.minecraft.commands.CommandSourceStack
import net.minecraft.network.chat.Component
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.entity.player.Player

fun requestCommand(dispatcher: CommandDispatcher<CommandSourceStack>) {
    var ticks = 0
    var isSleepDisabledActive = false

    dispatcher.literal("request") {
        literal("dontSleep") {
            runs {
                if (it.source.server.overworld().isBrightOutside) {
                    it.source.sendSystemMessage(Component.literal("You cannot run this command while it's still daytime!"))
                    return@runs
                }

                isSleepDisabledActive = true
                BossbarShenanigans.dontSleepBossbar.name = Component.literal("Sleep is currently disabled by ").append(it.source.playerOrException.name).append("; Progress until next day start")
                BossbarShenanigans.dontSleepBossbar.isVisible = true
                BossbarShenanigans.dontSleepBossbar.value = 24000 - (it.source.server.overworld().dayTime % 24000L).toInt()

                ticks = 7 * 20
                for (player in it.source.server.playerList.players) {

                    player.connection.send(ClientboundSetTitlesAnimationPacket(10, 120, 10))

                    player.connection.send(
                        ClientboundSetTitleTextPacket(
                            Component.literal("Hey! Please do not sleep!").withStyle(
                                ChatFormatting.RED
                            )
                        )
                    )
                    player.connection.send(
                        ClientboundSetSubtitleTextPacket(
                            Component.literal("Requested by ").append(it.source.playerOrException.name).withStyle(
                                ChatFormatting.GOLD
                            )
                        )
                    )

                    it.source.sendSuccess(
                        {
                            Component.literal(it.source.playerOrException.name.string).append(" has requested that players do not sleep!")
                        }, true
                    )
                }
            }

            ServerTickEvents.END_SERVER_TICK.register {
                if (!isSleepDisabledActive) { return@register }

                ticks--

                BossbarShenanigans.dontSleepBossbar.value = 24000 - (it.overworld().dayTime % 24000L).toInt()

                if (it.overworld().isBrightOutside) {
                    isSleepDisabledActive = false
                    BossbarShenanigans.dontSleepBossbar.isVisible = false
                }

                for (player in it.playerList.players) {
                    // harrassing peoples ears to get them to not sleep is very funny.
                    ServerTickEvents.END_SERVER_TICK.register {
                        when (ticks) {
                            6 * 20 -> player.playNotifySound(
                                SoundEvents.BELL_BLOCK,
                                SoundSource.MASTER,
                                1.0f,
                                10f,
                            )

                            5 * 20 -> player.playNotifySound(
                                SoundEvents.BELL_BLOCK,
                                SoundSource.MASTER,
                                1.0f,
                                10f,
                            )

                            4 * 20 -> player.playNotifySound(
                                SoundEvents.BELL_BLOCK,
                                SoundSource.MASTER,
                                1.0f,
                                10f,
                            )

                            3 * 20 -> player.playNotifySound(
                                SoundEvents.BELL_BLOCK,
                                SoundSource.MASTER,
                                1.0f,
                                10f,
                            )

                            2 * 20 -> player.playNotifySound(
                                SoundEvents.BELL_BLOCK,
                                SoundSource.MASTER,
                                1.0f,
                                10f,
                            )
                        }
                    }
                }
            }

            EntitySleepEvents.ALLOW_SLEEPING.register { player, sleepingPos ->
                if (isSleepDisabledActive) {
                    return@register Player.BedSleepingProblem.OTHER_PROBLEM
                }

                return@register null
            }
        }

        literal("attention") {
            runs {
                ticks = 7 * 20
                for (player in it.source.server.playerList.players) {
                    player.connection.send(ClientboundSetTitlesAnimationPacket(10, 120, 10))

                    player.connection.send(
                        ClientboundSetTitleTextPacket(
                            Component.literal("Hey! Look in chat!").withStyle(
                                ChatFormatting.RED
                            )
                        )
                    )
                    player.connection.send(
                        ClientboundSetSubtitleTextPacket(
                            Component.literal("Requested by ").append(it.source.playerOrException.name).withStyle(
                                ChatFormatting.GOLD
                            )
                        )
                    )

                    player.displayClientMessage(
                        Component.literal(it.source.playerOrException.name.string)
                            .append(" has requested that players read the chat above!"), false
                    )
                }
            }

            ServerTickEvents.END_SERVER_TICK.register {
                ticks--

                for (player in it.playerList.players) {
                    when (ticks) {
                        6 * 20 -> player.playNotifySound(
                            SoundEvents.ANVIL_FALL,
                            SoundSource.MASTER,
                            1.0f,
                            10f,
                        )

                        5 * 20 -> player.playNotifySound(
                            SoundEvents.ANVIL_FALL,
                            SoundSource.MASTER,
                            1.0f,
                            10f,
                        )

                        4 * 20 -> player.playNotifySound(
                            SoundEvents.ANVIL_FALL,
                            SoundSource.MASTER,
                            1.0f,
                            10f,
                        )

                        3 * 20 -> player.playNotifySound(
                            SoundEvents.ANVIL_FALL,
                            SoundSource.MASTER,
                            1.0f,
                            10f,
                        )
                        2 * 20 -> player.playNotifySound(
                            SoundEvents.ANVIL_FALL,
                            SoundSource.MASTER,
                            1.0f,
                            10f,
                        )
                    }
                }
            }
        }

        // i hate this, i feel it should be in the require block with the has permission nested as well but no that's
        // not how it fuckin works apparently what the fuck is this terrible implication
        require { hasPermission(4) }
        literal("serverRestart") {
            runs {
                for (player in it.source.server.playerList.players) {
                    player.playNotifySound(SoundEvents.ENDER_DRAGON_GROWL, SoundSource.MASTER, 1.0f, 1.0f)

                    player.connection.send(ClientboundSetTitlesAnimationPacket(10, 200, 10))

                    player.connection.send(
                        ClientboundSetTitleTextPacket(
                            Component.literal("Server Restart requested!").withStyle(
                                ChatFormatting.RED
                            )
                        )
                    )
                    player.connection.send(
                        ClientboundSetSubtitleTextPacket(
                            Component.literal("Prepare for server restart, if everyone agrees.").withStyle(
                                ChatFormatting.GOLD
                            )
                        )
                    )

                    it.source.sendSuccess(
                        { Component.literal("An admin has requested for a server restart! Please prepare, if everyone agrees.") },
                        false
                    )
                }
            }
        }
    }
}