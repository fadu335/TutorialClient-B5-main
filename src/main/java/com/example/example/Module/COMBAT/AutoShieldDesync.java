package com.example.examplemod.Module.COMBAT;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.Module.Module;
import com.example.examplemod.Utils.TimerUtil;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import yea.bushroot.clickgui.Setting;

public class AutoShieldDesync extends Module {
    public AutoShieldDesync() {
        super("AutoShieldDesync", Keyboard.KEY_NONE, Category.COMBAT);

        ExampleMod.instance.settingsManager.rSetting(new Setting("Delay", this, 200, 0, 900, true));
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent e) {
        double delay = ExampleMod.instance.settingsManager.getSettingByName(this.name, "Delay").getValDouble();
        final ItemStack offHand = mc.player.getHeldItemOffhand();

        if (offHand.getItem() == Items.SHIELD && TimerUtil.hasReached((float) delay)) {
            mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(900, 900, 900), EnumFacing.NORTH));
            mc.playerController.processRightClick(mc.player, mc.world, EnumHand.OFF_HAND);
            TimerUtil.reset();
        }
    }
}

