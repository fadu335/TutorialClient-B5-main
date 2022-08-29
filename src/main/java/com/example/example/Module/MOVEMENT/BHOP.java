package com.example.examplemod.Module.MOVEMENT;

import com.example.examplemod.Module.Module;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class BHOP extends Module {
    public BHOP() {
        super("Sunrise", Keyboard.KEY_NONE, Category.MOVEMENT);
    }

    @SubscribeEvent
    public void onPlayerTickEvent(TickEvent.PlayerTickEvent e) {
        if (mc.player.onGround) {
            double speed = 0.5;

            float yaw = mc.player.rotationYaw * 0.0174532920F;
            mc.player.motionX -= MathHelper.sin(yaw) * (speed / 7);
            mc.player.motionZ += MathHelper.cos(yaw) * (speed / 7);
        }
    }
}
