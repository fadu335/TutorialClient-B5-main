package com.example.examplemod.Module.MOVEMENT;

import com.example.examplemod.Module.Module;
import com.example.examplemod.Utils.ChatUtils;
import org.lwjgl.input.Keyboard;

import java.util.concurrent.TimeUnit;

public class HightJump extends Module {
    public HightJump() {
        super("HighJump", Keyboard.KEY_J, Category.MOVEMENT);
    }

    @Override
    public void onEnable() {
        if (mc.player.onGround) {
            mc.player.jump();
        }
        new Thread(() -> {
            mc.player.motionY = 9f;
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mc.player.motionY = 8.742f;
            this.toggle();
        }).start();
    }
    }


