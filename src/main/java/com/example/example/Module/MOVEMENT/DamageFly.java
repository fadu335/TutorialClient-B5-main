package com.example.examplemod.Module.MOVEMENT;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.Module.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import yea.bushroot.clickgui.Setting;

import java.util.ArrayList;
import java.util.Objects;

public class DamageFly extends Module {
    public DamageFly() {
        super("DamageFly", Keyboard.KEY_NONE, Category.MOVEMENT);

        ArrayList<String> options = new ArrayList<>();

        options.add("Matrix");
        options.add("MatrixUp");
        options.add("MatrixBow");

        ExampleMod.instance.settingsManager.rSetting(new Setting("Mode", this, options, "Mode"));
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        String mode = ExampleMod.instance.settingsManager.getSettingByName(this.name, "Mode").getValString();

        if (Objects.equals(mode, "Matrix")) {
            if (mc.player.hurtTime > 0) {
                mc.player.motionX = 5f;
                mc.player.motionY = 0.6f;
                mc.player.motionZ = 0.3f;
            }
        }
        if (Objects.equals(mode, "MatrixUp")) {
            if (mc.player.hurtTime > 0) {
                mc.player.motionY = 0.5f;
            }
        }
        if (Objects.equals(mode, "MatrixBow")) {
            if (mc.player.hurtTime > 0) {
                mc.player.motionY = 0.06f;
            }
        }
    }
}
