package com.example.examplemod.Module.MOVEMENT;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.Module.Module;
import net.minecraft.item.EnumAction;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import yea.bushroot.clickgui.Setting;

import java.sql.Wrapper;

public class NoSlow extends Module {
    public NoSlow() {
        super("NoSlow", Keyboard.KEY_NONE, Category.MOVEMENT);
        ExampleMod.instance.settingsManager.rSetting(new Setting("Speed", this, 0.0, 0.0, 5.0, false));
    }

    @SubscribeEvent
    public void onInputEvent(InputUpdateEvent inputUpdateEvent) {
        if (isUsingItem()) {
            if (mc.player.onGround && !mc.gameSettings.keyBindJump.isKeyDown()) {
                if (mc.player.ticksExisted % 2 == 0) {
                    mc.player.motionX = 0.46;
                    mc.player.motionZ= 0.46;
                }
            } else if ((double) mc.player.fallDistance > 0.2) {
                mc.player.motionX = 0.9100000262260437;
                mc.player.motionZ= 0.9100000262260437;
            }
    }

    }
    public boolean isUsingItem() {
        return mc.player.isHandActive() && (mc.player.getActiveItemStack().getItem().getItemUseAction(mc.player.getActiveItemStack()) == EnumAction.EAT || mc.player.getActiveItemStack().getItem().getItemUseAction(mc.player.getActiveItemStack()) == EnumAction.BLOCK || mc.player.getActiveItemStack().getItem().getItemUseAction(mc.player.getActiveItemStack()) == EnumAction.BOW || mc.player.getActiveItemStack().getItem().getItemUseAction(mc.player.getActiveItemStack()) == EnumAction.DRINK);
    }
}
