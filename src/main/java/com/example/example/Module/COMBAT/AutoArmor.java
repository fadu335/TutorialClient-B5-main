package com.example.examplemod.Module.COMBAT;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.Module.Module;
import com.example.examplemod.Utils.TimerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import yea.bushroot.clickgui.Setting;

public class AutoArmor extends Module {
    public AutoArmor() {
        super("AutoArmor", Keyboard.KEY_NONE, Category.COMBAT);

        ExampleMod.instance.settingsManager.rSetting(new Setting("Delay", this, 200, 0, 900, true));
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent e) {
        if (shouldEquip(mc)) {
            equip(mc);
            TimerUtil.reset();
        }
    }

    private void equip(final Minecraft mc) {
        int[] armorSlots = new int[]{-1, -1, -1, -1}, armorValues = new int[]{-1, -1, -1, -1};

        for (int type = 0; type < 4; type++) {
            ItemStack current = mc.player.inventory.armorItemInSlot(type);
            if (!current.isEmpty() && current.getItem() instanceof ItemArmor) {
                armorValues[type] = getArmorValue(current);
            }
        }

        for (int slot = 9; slot < 45; slot++) {
            ItemStack itemStack = mc.player.inventoryContainer.getSlot(slot).getStack();
            if (itemStack.isEmpty() || !(itemStack.getItem() instanceof ItemArmor) || itemStack.getCount() > 1 || (EnchantmentHelper.hasBindingCurse(itemStack)))
                continue;

            ItemArmor armor = (ItemArmor) itemStack.getItem();

            int type = armor.armorType.ordinal() - 2;
            if (type == 2 && mc.player.inventory.armorItemInSlot(type).getItem().equals(Items.ELYTRA))
                continue;

            int value = getArmorValue(itemStack);
            if (value > armorValues[type]) {
                armorSlots[type] = slot;
                armorValues[type] = value;
            }
        }

        for (int type = 0; type < 4; type++) {
            int slot = armorSlots[type];
            if (slot == -1)
                continue;

            ItemStack current = mc.player.inventory.armorItemInSlot(type);
            if (!current.isEmpty() || mc.player.inventory.getFirstEmptyStack() != -1) {
                mc.playerController.windowClick(0, 8 - type, 0, ClickType.QUICK_MOVE, mc.player);
                mc.playerController.windowClick(0, slot, 0, ClickType.QUICK_MOVE, mc.player);
                break;
            }
        }
    }

    private boolean shouldEquip(final Minecraft mc) {
        double delay = ExampleMod.instance.settingsManager.getSettingByName(this.name, "Delay").getValDouble();

        boolean inInventory = mc.currentScreen instanceof GuiContainer;
        boolean hasDelayFinished = TimerUtil.hasReached((float) delay);
        boolean isCreative = mc.player.isCreative();
        return !isCreative && hasDelayFinished && !inInventory;
    }

    private int getArmorValue(ItemStack itemStack) {
        if (!itemStack.isEmpty() && itemStack.getItem() instanceof ItemArmor) {
            int reductionAmount = ((ItemArmor) itemStack.getItem()).damageReduceAmount;
            int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.PROTECTION, itemStack);
            return reductionAmount + enchantmentLevel;
        }
        return -1;
    }
}
