package com.example.examplemod.Module.COMBAT;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.Module.Module;
import com.example.examplemod.Utils.TimerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import yea.bushroot.clickgui.Setting;

public class AutoShieldDestroy extends Module {
    public static boolean destroy = false;
    private int oldcurrentitem;

    public AutoShieldDestroy() {
        super("AutoShieldDestroy", Keyboard.KEY_NONE, Category.COMBAT);

        ExampleMod.instance.settingsManager.rSetting(new Setting("Delay", this, 600, 0, 900, true));
        ExampleMod.instance.settingsManager.rSetting(new Setting("SwapBack", this, true));
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent e) {
        double delay = ExampleMod.instance.settingsManager.getSettingByName(this.name, "Delay").getValDouble();
        boolean swapback = ExampleMod.instance.settingsManager.getSettingByName(this.name, "SwapBack").getValBoolean();

        for (EntityPlayer playerEntity : mc.world.playerEntities) {
            if (playerEntity != null && playerEntity != mc.player) {
                if (playerEntity.getHeldItemOffhand().getItem() instanceof ItemShield || playerEntity.getHeldItemMainhand().getItem() instanceof ItemShield) {
                    if (playerEntity.isHandActive()) {
                        if (findAxeInHotbar(mc) < 9 && findAxeInHotbar(mc) > 0) {
                            oldcurrentitem = mc.player.inventory.currentItem;
                            mc.player.inventory.currentItem = findAxeInHotbar(mc);
                            if (playerEntity.getHeldItemOffhand().getItem() instanceof ItemAxe) {
                                if (TimerUtil.hasReached((float) delay)) {
                                    mc.playerController.attackEntity(mc.player, playerEntity);
                                    TimerUtil.reset();
                                }
                            }
                        }
                        destroy = true;
                    } else {
                        destroy = false;
                        if (swapback) {
                            mc.player.inventory.currentItem = oldcurrentitem;
                        }
                    }
                } else {
                    destroy = false;
                }
            }
        }
    }

    private boolean isItemStackAxe(final ItemStack itemStack) {
        return itemStack.getItem() instanceof ItemAxe;
    }

    private int findAxeInHotbar(final Minecraft mc) {
        for (int index = 0; InventoryPlayer.isHotbar(index); index++) {
            if (isItemStackAxe(mc.player.inventory.getStackInSlot(index))) return index;
        }
        return -1;
    }

    @Override
    public void onDisable() {
        destroy = false;
    }
}
