package one.devos.nautical.rainbethunder;


import gay.asoji.fmw.FMW;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RainBeThunder implements ModInitializer {
    public static final String MOD_ID = "rainbethunder";
    public static final String VERSION = FMW.getVersionString(MOD_ID);
    public static final String MOD_NAME = FMW.getName(MOD_ID);

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    @Override
    public void onInitialize() {
        LOGGER.info("[" + MOD_NAME + "] Version " + VERSION + " loaded.");
        LOGGER.info("[" + MOD_NAME + "] " + "You might want to get a bed ready for when it rains :^)");
    }
}
