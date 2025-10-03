package one.devos.nautical.creepermultidrop;

import gay.asoji.fmw.FMW;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreeperMultidrop implements ModInitializer {
    public static final String MOD_ID = "creepermultidrop";
    public static final String VERSION = FMW.getVersionString(MOD_ID);
    public static final String MOD_NAME = FMW.getName(MOD_ID);

    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

    @Override
    public void onInitialize() {
        LOGGER.info("[" + MOD_NAME + "] Version " + VERSION + " loaded." );
        LOGGER.info("[" + MOD_NAME + "] " + "Want head?");
    }
}
