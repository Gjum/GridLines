package gjum.minecraft.forge.gridlines.config;


import gjum.minecraft.forge.gridlines.GridLinesMod;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;
import java.util.Set;

public class GridLinesConfig {
    public static final String CATEGORY_MAIN = "Main";

    public static final GridLinesConfig instance = new GridLinesConfig();

    public Configuration config;

    public boolean depthTest;
    public boolean enabled;
    public int interval;
    public GridPattern gridPattern;
    public int renderDistance;
    public int xAnchor, zAnchor;

    private Property propDepthTest, propEnabled, propInterval, propPattern, propRenderDistance, propXAnchor, propZAnchor;

    private GridLinesConfig() {
    }

    public void load(File configFile) {
        config = new Configuration(configFile, GridLinesMod.VERSION);

        syncProperties();
        final ConfigCategory categoryMain = config.getCategory(CATEGORY_MAIN);
        final Set<String> confKeys = categoryMain.keySet();

        config.load();

        if (!config.getDefinedConfigVersion().equals(config.getLoadedConfigVersion())) {
            // clear config from old entries
            // otherwise they would clutter the gui
            final Set<String> unusedConfKeys = categoryMain.keySet();
            unusedConfKeys.removeAll(confKeys);
            for (String confKey : unusedConfKeys) {
                categoryMain.remove(confKey);
            }
        }

        syncProperties();
        syncValues();
    }

    public void afterGuiSave() {
        syncProperties();
        syncValues();
    }

    public void setEnabled(boolean enabled) {
        syncProperties();
        propEnabled.set(enabled);
        syncValues();
    }

    public void setDepthTest(boolean depthTest) {
        syncProperties();
        propDepthTest.set(depthTest);
        syncValues();
    }

    public void setAnchor(int x, int z) {
        syncProperties();
        propXAnchor.set(x);
        propZAnchor.set(z);
        syncValues();
    }

    /**
     * no idea why this has to be called so often, ideally the prop* would stay the same,
     * but it looks like they get disassociated from the config sometimes and setting them no longer has any effect
     */
    private void syncProperties() {
        propDepthTest = config.get(CATEGORY_MAIN, "depth test", true, "hide behind blocks instead of showing through them");
        propEnabled = config.get(CATEGORY_MAIN, "enabled", true, "Enable/disable all overlays");
        propInterval = config.get(CATEGORY_MAIN, "grid interval", 23);
        propPattern = config.get(CATEGORY_MAIN, "grid pattern", GridPattern.SQUARE.name, "what kind of grid", GridPattern.names);
        propRenderDistance = config.get(CATEGORY_MAIN, "render distance", 48);
        propXAnchor = config.get(CATEGORY_MAIN, "x anchor", 0);
        propZAnchor = config.get(CATEGORY_MAIN, "z anchor", 0);
    }

    /**
     * called every time a prop is changed, to apply the new values to the fields and to save the values to the config file
     */
    private void syncValues() {
        depthTest = propDepthTest.getBoolean();
        enabled = propEnabled.getBoolean();
        interval = propInterval.getInt();
        gridPattern = GridPattern.fromName(propPattern.getString());
        renderDistance = propRenderDistance.getInt();
        xAnchor = propXAnchor.getInt();
        zAnchor = propZAnchor.getInt();

        if (config.hasChanged()) {
            config.save();
            syncProperties();
            GridLinesMod.logger.info("Saved " + GridLinesMod.MOD_NAME + " config.");
        }
    }

}
