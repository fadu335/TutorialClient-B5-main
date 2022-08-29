package com.example.examplemod.Module.COMBAT;


import com.example.examplemod.ExampleMod;
import com.example.examplemod.Module.Module;
import com.example.examplemod.events.PacketEvent;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import yea.bushroot.clickgui.Setting;

public class SuperBow extends Module {
    public SuperBow() {
        super("SuperBow", Keyboard.KEY_NONE, Category.COMBAT);

        ExampleMod.instance.settingsManager.rSetting(new Setting("Power", this, 100, 1, 100, true));
    }

    @SubscribeEvent
    public void onPacketReceive(PacketEvent.PacketReceiveEvent e) {
        double power = ExampleMod.instance.settingsManager.getSettingByName(this.name, "Power").getValDouble();

        if (e.getPacket() instanceof CPacketPlayerDigging && mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemBow && ((CPacketPlayerDigging) e.getPacket()).getAction() == CPacketPlayerDigging.Action.RELEASE_USE_ITEM) {
            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SPRINTING));
            for (int i = 0; i < power; i++) {
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.0E-10, mc.player.posZ, false));
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY - 1.0E-10, mc.player.posZ, true));
            }
        }
    }
}
