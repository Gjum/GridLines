package gjum.minecraft.forge.gridlines;

import gjum.minecraft.forge.gridlines.config.ConfigGui;
import gjum.minecraft.forge.gridlines.config.GridLinesConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class KeyHandler {
    private final KeyBinding anchorOnPlayer = new KeyBinding("Anchor on player", Keyboard.KEY_NONE, "GridLines");
    private final KeyBinding openConfigGui = new KeyBinding("Open GridLines config screen", Keyboard.KEY_NONE, "GridLines");
    private final KeyBinding toggleAllRender = new KeyBinding("Toggle render all grid lines", Keyboard.KEY_NONE, "GridLines");
    private final KeyBinding toggleDepthTest = new KeyBinding("Toggle depth test", Keyboard.KEY_NONE, "GridLines");

    private long lastCrash = 0;

    public KeyHandler() {
        ClientRegistry.registerKeyBinding(anchorOnPlayer);
        ClientRegistry.registerKeyBinding(openConfigGui);
        ClientRegistry.registerKeyBinding(toggleAllRender);
        ClientRegistry.registerKeyBinding(toggleDepthTest);

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        try {
            if (anchorOnPlayer.isPressed()) {
                EntityPlayerSP player = Minecraft.getMinecraft().player;
                // get the rounding right for negative coords
                GridLinesConfig.instance.setAnchor((int) Math.floor(player.posX), (int) Math.floor(player.posZ));
            }
            if (openConfigGui.isPressed()) {
                Minecraft.getMinecraft().displayGuiScreen(new ConfigGui(null));
            }
            if (toggleAllRender.isPressed()) {
                GridLinesConfig.instance.setEnabled(!GridLinesConfig.instance.enabled);
            }
            if (toggleDepthTest.isPressed()) {
                GridLinesConfig.instance.setDepthTest(!GridLinesConfig.instance.depthTest);
            }
        } catch (Exception e) {
            if (lastCrash < System.currentTimeMillis() - 5000) {
                lastCrash = System.currentTimeMillis();
                e.printStackTrace();
            }
        }
    }
}
