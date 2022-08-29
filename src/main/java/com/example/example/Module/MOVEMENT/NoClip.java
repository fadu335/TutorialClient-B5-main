package com.example.examplemod.Module.MOVEMENT;


import com.example.examplemod.ExampleMod;
import com.example.examplemod.Module.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import yea.bushroot.clickgui.Setting;

public class NoClip extends Module {
    public NoClip() {
        super("NoClip", Keyboard.KEY_NONE, Category.MOVEMENT);

        ExampleMod.instance.settingsManager.rSetting(new Setting("Forward", this, true));
        ExampleMod.instance.settingsManager.rSetting(new Setting("Up", this, true));
        ExampleMod.instance.settingsManager.rSetting(new Setting("UpSpeed", this, 0.2, 0.1, 1.0, false));
        ExampleMod.instance.settingsManager.rSetting(new Setting("Down", this, true));
        ExampleMod.instance.settingsManager.rSetting(new Setting("DownSpeed", this, 0.2, 0.1, 1.0, false));
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        boolean forward = ExampleMod.instance.settingsManager.getSettingByName(this.name, "Forward").getValBoolean();
        boolean up = ExampleMod.instance.settingsManager.getSettingByName(this.name, "Up").getValBoolean();
        double upspeed = ExampleMod.instance.settingsManager.getSettingByName(this.name, "UpSpeed").getValDouble();
        boolean down = ExampleMod.instance.settingsManager.getSettingByName(this.name, "Down").getValBoolean();
        double downspeed = ExampleMod.instance.settingsManager.getSettingByName(this.name, "DownSpeed").getValDouble();

        if (forward) {
            if (mc.player.collidedHorizontally && mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown()) {
                mc.player.setPosition(mc.player.posX - Math.sin(mc.player.rotationYaw * 0.017453292) * 0.002494944989f, mc.player.posY, mc.player.posZ + Math.cos(mc.player.rotationYaw * 0.017453292) * 0.002494944989f);

            }
        }
        if (up) {
            if (mc.gameSettings.keyBindJump.isKeyDown()) {
                mc.player.setPosition(mc.player.posX, mc.player.posY + upspeed, mc.player.posZ);
            }
        }
        if (down) {
            if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                mc.player.setPosition(mc.player.posX, mc.player.posY - downspeed, mc.player.posZ);
            }
        }
    }
}

