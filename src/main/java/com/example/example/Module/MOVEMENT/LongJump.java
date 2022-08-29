package com.example.examplemod.Module.MOVEMENT;

import com.example.examplemod.Module.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class LongJump extends Module {
    public LongJump() {
        super("LongJump", Keyboard.KEY_NONE, Category.MOVEMENT);}

    @SubscribeEvent
    public void OnUpdate(TickEvent.PlayerTickEvent e) {
        mc.player.cameraPitch = 1F;
        mc.player.cameraYaw = 4F;
        toggle();

    }
}