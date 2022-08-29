package com.example.examplemod.Module.MOVEMENT;

import com.EventTarget;
import com.example.examplemod.ExampleMod;
import com.example.examplemod.Module.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import yea.bushroot.clickgui.Setting;

import java.util.ArrayList;
import java.util.Objects;

public class AirJump extends Module {
    public AirJump() {
        super("AirJump", Keyboard.KEY_NONE, Category.MOVEMENT);
    }
    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent e) {
        if(mc.gameSettings.keyBindJump.isKeyDown()) {
            mc.player.jump();
        }
    }
}
