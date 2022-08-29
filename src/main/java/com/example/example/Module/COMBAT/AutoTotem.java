package com.example.examplemod.Module.COMBAT;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.Module.Module;
import com.example.examplemod.Utils.TimerUtil;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import yea.bushroot.clickgui.Setting;

public class AutoTotem extends Module {
    private int oldtotemslot;

    public AutoTotem() {
        super("AutoTotem", Keyboard.KEY_NONE, Category.COMBAT);

        ExampleMod.instance.settingsManager.rSetting(new Setting("Delay", this, 250, 0, 900, true));
        ExampleMod.instance.settingsManager.rSetting(new Setting("HPCheck", this, true));
        ExampleMod.instance.settingsManager.rSetting(new Setting("HP", this, 5, 1, 20, false));
        ExampleMod.instance.settingsManager.rSetting(new Setting("CrystalsCheck", this, true));
        ExampleMod.instance.settingsManager.rSetting(new Setting("CrystalsDistance", this, 7, 1, 15, false));
        ExampleMod.instance.settingsManager.rSetting(new Setting("FallCheck", this, true));
        ExampleMod.instance.settingsManager.rSetting(new Setting("FallDistance", this, 10, 5, 100, true));
        ExampleMod.instance.settingsManager.rSetting(new Setting("SwapBack", this, true));
        ExampleMod.instance.settingsManager.rSetting(new Setting("IgnoreGUI", this, true));
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent e) {
        double delay = ExampleMod.instance.settingsManager.getSettingByName(this.name, "Delay").getValDouble();
        boolean hpcheck = ExampleMod.instance.settingsManager.getSettingByName(this.name, "HPCheck").getValBoolean();
        double hp = ExampleMod.instance.settingsManager.getSettingByName(this.name, "HP").getValDouble();
        boolean crystalscheck = ExampleMod.instance.settingsManager.getSettingByName(this.name, "CrystalsCheck").getValBoolean();
        double crystalsdistance = ExampleMod.instance.settingsManager.getSettingByName(this.name, "CrystalsDistance").getValDouble();
        boolean fallcheck = ExampleMod.instance.settingsManager.getSettingByName(this.name, "FallCheck").getValBoolean();
        double falldistance = ExampleMod.instance.settingsManager.getSettingByName(this.name, "FallDistance").getValDouble();
        boolean swapback = ExampleMod.instance.settingsManager.getSettingByName(this.name, "SwapBack").getValBoolean();
        boolean ignoregui = ExampleMod.instance.settingsManager.getSettingByName(this.name, "IgnoreGUI").getValBoolean();

        final ItemStack offHand = mc.player.getHeldItemOffhand();
        final int totemslot = getTotemSlot();

        if (mc.currentScreen instanceof GuiInventory && !ignoregui) {
            return;
        }
        if (hpcheck) {
            if (mc.player.getHealth() <= hp) {
                if (offHand.getItem() == Items.TOTEM_OF_UNDYING) {
                    return;
                }
                if (totemslot != -1 && TimerUtil.hasReached((float) delay)) {
                    oldtotemslot = getTotemSlot();
                    mc.playerController.windowClick(0, totemslot, 0, ClickType.PICKUP, mc.player);
                    mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
                    TimerUtil.reset();
                }
            } else {
                if (totemslot != oldtotemslot && TimerUtil.hasReached((float) delay) && swapback) {
                    mc.playerController.windowClick(0, 45, 1, ClickType.PICKUP, mc.player);
                    mc.playerController.windowClick(0, oldtotemslot, 1, ClickType.PICKUP, mc.player);
                    TimerUtil.reset();
                }
            }
        }

        if (crystalscheck) {
            for (Entity EntityEnderCrystal : mc.world.loadedEntityList) {
                if (EntityEnderCrystal instanceof EntityEnderCrystal) {
                    if (mc.player.getDistance(EntityEnderCrystal) <= crystalsdistance) {
                        if (offHand.getItem() == Items.TOTEM_OF_UNDYING) {
                            return;
                        }
                        if (totemslot != -1 && TimerUtil.hasReached((float) delay)) {
                            mc.playerController.windowClick(mc.player.inventoryContainer.windowId, totemslot, 0, ClickType.PICKUP, mc.player);
                            mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 45, 0, ClickType.PICKUP, mc.player);
                            TimerUtil.reset();
                        }
                    } else {
                        if (totemslot != oldtotemslot && TimerUtil.hasReached((float) delay) && swapback) {
                            mc.playerController.windowClick(0, 45, 1, ClickType.PICKUP, mc.player);
                            mc.playerController.windowClick(0, oldtotemslot, 1, ClickType.PICKUP, mc.player);
                            TimerUtil.reset();
                        }
                    }
                }
            }
        }

        if (fallcheck) {
            if (mc.player.fallDistance >= falldistance) {
                if (offHand.getItem() == Items.TOTEM_OF_UNDYING) {
                    return;
                }
                if (totemslot != -1 && TimerUtil.hasReached((float) delay)) {
                    mc.playerController.windowClick(mc.player.inventoryContainer.windowId, totemslot, 0, ClickType.PICKUP, mc.player);
                    mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 45, 0, ClickType.PICKUP, mc.player);
                    TimerUtil.reset();
                }
            } else {
                if (totemslot != oldtotemslot && TimerUtil.hasReached((float) delay) && swapback) {
                    mc.playerController.windowClick(0, 45, 1, ClickType.PICKUP, mc.player);
                    mc.playerController.windowClick(0, oldtotemslot, 1, ClickType.PICKUP, mc.player);
                    TimerUtil.reset();
                }
            }
        }
    }

    private int getTotemSlot() {
        for (int i = 0; i < 36; i++) {
            final Item item = mc.player.inventory.getStackInSlot(i).getItem();
            if (item == Items.TOTEM_OF_UNDYING) {
                if (i < 9) {
                    i += 36;
                }
                return i;
            }
        }
        return -1;
    }
}