package com.example.examplemod.Module.COMBAT;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.Module.Module;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import yea.bushroot.clickgui.Setting;

public class Criticals extends Module {
    public Criticals() {
        super("Criticals", Keyboard.KEY_NONE, Category.COMBAT);

        ExampleMod.instance.settingsManager.rSetting(new Setting("Range", this, 0.10, 0.05, 0.2, false));
    }

    @SubscribeEvent
    public void onAttack(AttackEntityEvent e) {
        double range = ExampleMod.instance.settingsManager.getSettingByName(this.name, "Range").getValDouble();

        if (mc.player.onGround ) {
            mc.player.motionY = range;
        }
    }
}
