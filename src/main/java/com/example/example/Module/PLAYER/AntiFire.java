package com.example.examplemod.Module.PLAYER;

import com.example.examplemod.Module.Module;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class AntiFire extends Module {
    public AntiFire() {
        super("AntiFire", Keyboard.KEY_NONE, Category.PLAYER);
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent e) {

        mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
        mc.player.setSneaking(true);
    }
}
