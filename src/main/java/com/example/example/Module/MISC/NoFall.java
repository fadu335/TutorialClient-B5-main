package com.example.examplemod.Module.MISC;

import com.example.examplemod.Module.Module;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class NoFall extends Module {
    public NoFall() {
        super("NoFall", Keyboard.KEY_NONE, Category.MISC);
    }

    @SubscribeEvent
    public void OnUpdate(TickEvent.PlayerTickEvent e) {
        if (mc.player.fallDistance >= 3) {
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, true));
            mc.player.fallDistance = 0f;
        }
    }
}
