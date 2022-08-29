package com.example.examplemod.Module.RENDER;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.Module.Module;
import com.example.examplemod.events.RenderHeldItemEvent;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import yea.bushroot.clickgui.Setting;

public class ViewModel extends Module {
    public ViewModel() {
        super("ViewModel", Keyboard.KEY_NONE, Category.RENDER);

        ExampleMod.instance.settingsManager.rSetting(new Setting("Position X", this, 0, -10, 10, false));
        ExampleMod.instance.settingsManager.rSetting(new Setting("Position Y", this, -0.23, -10, 10, false));
        ExampleMod.instance.settingsManager.rSetting(new Setting("Position Z", this, -0.45, -10, 10, false));
    }
    @SubscribeEvent
    public void onRender(RenderSpecificHandEvent e) {
        double x, y, z;
        x = ExampleMod.instance.settingsManager.getSettingByName(this.name, "Position X").getValDouble();
        y = ExampleMod.instance.settingsManager.getSettingByName(this.name, "Position Y").getValDouble();
        z = ExampleMod.instance.settingsManager.getSettingByName(this.name, "Position Z").getValDouble();
        GL11.glTranslated(x, y, z);

    }
}

