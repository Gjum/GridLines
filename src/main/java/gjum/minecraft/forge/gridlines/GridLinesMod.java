package gjum.minecraft.forge.gridlines;

import gjum.minecraft.forge.gridlines.config.GridLinesConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(
        modid = GridLinesMod.MOD_ID,
        name = GridLinesMod.MOD_NAME,
        version = GridLinesMod.VERSION,
        guiFactory = "gjum.minecraft.forge.gridlines.config.ConfigGuiFactory",
        clientSideOnly = true)
public class GridLinesMod {

    public static final String MOD_ID = "gridlines";
    public static final String MOD_NAME = "GridLines";
    public static final String VERSION = "@VERSION@";
    public static final String BUILD_TIME = "@BUILD_TIME@";

    public static Logger logger;
    private long lastCrash = 0;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        File configFile = event.getSuggestedConfigurationFile();
        logger.info("Loading config from " + configFile);
        gjum.minecraft.forge.gridlines.config.GridLinesConfig.instance.load(configFile);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        logger.info(String.format("%s version %s built at %s", MOD_NAME, VERSION, BUILD_TIME));

        MinecraftForge.EVENT_BUS.register(this);
        new Renderer();
        new KeyHandler();
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        try {
            if (event.getModID().equals(GridLinesMod.MOD_ID)) {
                GridLinesConfig.instance.afterGuiSave();
            }
        } catch (Exception e) {
            if (lastCrash < System.currentTimeMillis() - 5000) {
                lastCrash = System.currentTimeMillis();
                e.printStackTrace();
            }
        }
    }
}
