package one.devos.nautical.clientier.config

import gay.asoji.fmw.FMW
import gg.essential.vigilance.Vigilant
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.Util
import one.devos.nautical.clientier.Clientier
import java.io.File

object Config : Vigilant(
    File(FabricLoader.getInstance().configDir.toFile(), "clientier.toml"),
    "${FMW.getName(Clientier.MOD_ID)} v${FMW.getVersion(Clientier.MOD_ID)}"
) {
    var modpackTitle: String = "devOS: Season Potluck"
    var modpackQOIBase64Data: String = "G1wEERWaAuJngd1w3RzZlgcMhy+Vi/PjMiybJpZEF+bHe2gYHIygh1CD42ohnuwG6AGQ+Ic1FiGLRnsR+eApET3Tktmdh0LfPfGuDUgnmpmNf6s2tcg7nMTLpVR1vIK07oI84yJdNEH9RMjm0akOVaaCVo+VLM9H4fME7laVA4DA8CoowPRu2e1tllxfH/3Khv//dTc32bK3p+sB4Ooqf1ucXLi43r9P4/JSZNfJhcPDtPQ8MDC8fp32dEPbuYHHPJc13cjNnbM0Dpap7zg5gYdXDSZakHXdvLdHsX2NnFlCsgRZXwdoChaPGxJA0JObHWhox6+4V4AQQLtMwcUtFJi0DHwltLc35NcvHRZWNU8oStFyR4vl3VK9E1S4agU1V2tDf9nayiIAw0BpdVKyCkQyp3LZv6HR/wDCyVl1Uio2/f9/s3vFsO6byJisFgfHmzdnRveM8+ZXmZdV20gDmv+KqRDBDD5FRc0mVdjZL9gd5bmu68ttOl78/JnWTohiCwWlYGMbe4/TUyoO9HrEPlsEpSBcAy9e5K2PH93c+Pv3ocYLxStF2Z6vvnzR+dSa+8/vRSah9d58nJI+MRXzodeqdi8vGl3a2HB8DMJd8OyZxkrqGDlT8sIhl2f8rqTmgox8NtHovMr7s4nRatTSSEhPMYqIgpRU70SEBPFTa3Eq5L2U0cLFY4kEMg33FQPjoQYA0pDSlfINv3/rwjtKaFveOTmBhtbr4ICoEgJYkL+AX1weHkAI4PU1VNTTDfED1VC085GEqysQblKR1lvDScPiXgGD1tYWFrEiFpRf1LP7Hu5K3q1FiPjpv39Hx2WN+f5d511Tem5jaanQEJSsPlhzfJz3BCSb6DTy4cPmIFDEAUSr2b9A7aAjlcXFfHB+Xlu7ACCtC4enGDmbcDgUtBWPwP29mV/DL1aOnmK8AbV3zA/mXQUMy57to8mn4WSVEDbWmpzhV+/uLncffwPRXwsG"
    var windows11DarkModeTitlebar: Boolean = true

    init {
        // all plans of using internationalization has fallen apart so we have to hard code for now, at least until
        // there's a way you can custom define in the config what a key is, so forgive me for hardcoding this mfer
        category("Home") {
            subcategory("Contributing") {
                button("GitHub", "Where the source code is!", "GitHub Page") {
                    Util.getPlatform().openUri("https://github.com/devOS-Sanity-Edition/Stew")
                }

                button("Report an Issue", "Found a bug with Clientier? Or you want to suggest something? Go file an issue, the button will take you to the issue tracker.", "Issues Page") {
                    Util.getPlatform().openUri("https://github.com/devOS-Sanity-Edition/Stew/issues")
                }
            }

            subcategory("Credits") {
                button("asojidev", "Creator of this mod, and trying their damn best-", "GitHub") {
                    Util.getPlatform().openUri("https://github.com/asoji")
                }

                button("BluSpring", "Contributed to Winterisms, and Clientier by extension", "GitHub") {
                    Util.getPlatform().openUri("https://github.com/BluSpring")
                }

                button("CephalonCosmic", "Fixed QOIImage data found in Winterisms", "GitHub") {
                    Util.getPlatform().openUri("https://github.com/CephalonCosmic")
                }

                button("Deftu", "Vigilance config assistance, and better server restart command implementation", "GitHub") {
                    Util.getPlatform().openUri("https://github.com/Deftu")
                }

                button("maximumpower55", "Main person behind devOS: Season Potluck", "GitHub") {
                    Util.getPlatform().openUri("https://github.com/maximumpower55")
                }
            }
        }

        category("User") {
            subcategory("Windows 11") {
                switch(::windows11DarkModeTitlebar,
                    "Dark Mode Titlebar",
                    "Makes your game's titlebar dark instead of light. Winter's testing shows this option applying is.. finicky.. so if it doesn't work uh..\n\n§6Only works on Windows 11. Requires game restart."
                )
            }
        }

        category("devOS Modpacks") {
            subcategory("Pack Settings") {

                text(::modpackTitle,
                    "Modpack Title",
                    "The name of the Modpack. Used for Window title and a few other things. Recommended to not change it unless you're making a brand new modpack, like devOS: Season 7 or something.\n\n§6Requires game restart."
                )

                paragraph(::modpackQOIBase64Data,
                    "Modpack Icon as QOI Data String",
                    "The Brotli-compressed QOI Base64 Data string for the pack icon, used by Unsup by default.\n\nQOI Data can be generated at https://qoi.y2k.diy/, just make sure it's compressed with Brotli, and encoded to Base64.\n\n§6Requires game restart."
                )
            }
        }

        setCategoryDescription("Home", "§6Howdy! Welcome to Clientier v${FMW.getVersion(Clientier.MOD_ID)}.\nThis version was built for devOS: Season Potluck mostly in mind.\n")
        setSubcategoryDescription("Home", "Credits", "Here's all the §6amazing§r people who have contributed to the development of Clientier, Stew, or devOS: Season Potluck")
        setSubcategoryDescription("devOS Modpacks", "Pack Settings", "§cWarning: §rPlease do not touch any of these settings. They're here as easy exposure for modpack creators, but unless you know what you're doing or have a good reason to change them, please do not change any of these settings.\n\n§6If an unsup.ini exists in your pack's game directory, that will override whatever you put here. Modify the unsup.ini, or don't have one if you want to change the pack title and/or icon. Whatever changes you make to this subcategory §c§lwill not§r§6 apply if an unsup.ini is present, otherwise, just restart your game.")

        initialize()
    }
}