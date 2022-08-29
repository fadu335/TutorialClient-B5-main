package com.example.examplemod.Module.COMBAT;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.Module.Module;

import com.example.examplemod.events.PacketEvent;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Objects;

public class Velocity extends Module {
    public Velocity() {
        super("Velocity", Keyboard.KEY_NONE, Category.COMBAT);
        ArrayList<String> options = new ArrayList<>();
        options.add("Matrix");
        options.add("Packet");
    }
    @SubscribeEvent
    public void onPacketReceive(PacketEvent.PacketReceiveEvent e) {
        String mode = ExampleMod.instance.settingsManager.getSettingByName(this.name, "Mode").getValString();
        if (Objects.equals(mode, "Matrix")) {
            if (mc.player.hurtTime > 8) {
                mc.player.onGround = true;
        }
        }
        if (Objects.equals(mode, "Packet")) {
            SPacketEntityVelocity velocity;
            if (e.getPacket() instanceof SPacketEntityVelocity || e.getPacket() instanceof SPacketExplosion) {
                e.setCanceled(true);
        }
    }

    }
}

