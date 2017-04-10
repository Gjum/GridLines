package gjum.minecraft.forge.gridlines.config;

import gjum.minecraft.forge.gridlines.GridLinesMod;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.List;

public class ConfigGui extends GuiConfig {

    public ConfigGui(GuiScreen parent) {
        super(parent, getConfigElements(),
                GridLinesMod.MOD_ID, false, false, "GridLines config");
    }

    private static List<IConfigElement> getConfigElements() {
        Configuration config = GridLinesConfig.instance.config;
        return new ConfigElement(config.getCategory(GridLinesConfig.CATEGORY_MAIN)).getChildElements();
    }

}
