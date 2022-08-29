package com.example.examplemod.Module.RENDER;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.Module.Module;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class SkyColor extends Module {

    public SkyColor() {
        super("SkyColor", Keyboard.KEY_NONE, Category.RENDER);}

    @SubscribeEvent
    public void onFogRender(EntityViewRenderEvent.FogColors event) {
        boolean fog = ExampleMod.instance.settingsManager.getSettingByName(this.name, "Fog").getValBoolean();

        if (fog) {
            event.setRed(0 / 255f);
            event.setGreen(165 / 255f);
            event.setBlue(0 / 255f);
        }
    }

    @SubscribeEvent
    public void fog(EntityViewRenderEvent.FogDensity event) {
        boolean fog = ExampleMod.instance.settingsManager.getSettingByName(this.name, "Fog").getValBoolean();

        if (fog) {
            event.setDensity(0);
            event.setCanceled(true);
        }
    }
}
