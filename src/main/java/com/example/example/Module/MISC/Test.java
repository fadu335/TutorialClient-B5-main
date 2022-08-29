package com.example.examplemod.Module.MISC;

import com.example.examplemod.Module.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class Test extends Module {
    public Test() {
        super("Test", Keyboard.KEY_NONE, Category.MISC);
    }
    @SubscribeEvent
    public void OnUpdate(TickEvent.PlayerTickEvent e) {
        mc.player.fallDistance = 0;
    }
}

