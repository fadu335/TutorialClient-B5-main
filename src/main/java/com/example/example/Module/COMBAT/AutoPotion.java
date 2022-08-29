package com.example.examplemod.Module.COMBAT;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.Module.Module;
import com.example.examplemod.Utils.TimerUtil;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import yea.bushroot.clickgui.Setting;

import java.util.Objects;

public class AutoPotion extends Module {
    public AutoPotion() {
        super("AutoPotion", Keyboard.KEY_NONE, Category.COMBAT);

        ExampleMod.instance.settingsManager.rSetting(new Setting("Delay", this, 150, 0, 900, true));
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent e) {
        if (!mc.player.onGround) {
            return;
        }
        if (!mc.player.isPotionActive((Objects.requireNonNull(Potion.getPotionById(5)))) && isPotionOnHotBar(Potions.STRENGTH)) {
            throwPot(Potions.STRENGTH);
        } else if (!mc.player.isPotionActive((Objects.requireNonNull(Potion.getPotionById(1)))) && isPotionOnHotBar(Potions.SPEED)) {
            throwPot(Potions.SPEED);
        } else if (!mc.player.isPotionActive((Objects.requireNonNull(Potion.getPotionById(12)))) && isPotionOnHotBar(Potions.FIRERES)) {
            throwPot(Potions.FIRERES);
        }
    }

    public void throwPot(Potions potion) {
        double delay = ExampleMod.instance.settingsManager.getSettingByName(this.name, "Delay").getValDouble();

        int slot = getPotionSlot(potion);
        if (TimerUtil.hasReached((float) delay)) {
            mc.player.connection.sendPacket(new CPacketHeldItemChange(slot));
            mc.playerController.updateController();
            mc.player.connection.sendPacket(new CPacketPlayer.Rotation(mc.player.rotationYaw, 90, mc.player.onGround));
            mc.playerController.updateController();
            mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
            mc.playerController.updateController();
            mc.player.connection.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem));
            TimerUtil.reset();
        }
    }

    public int getPotionSlot(Potions potion) {
        for (int i = 0; i < 9; ++i) {
            if (this.isStackPotion(mc.player.inventory.getStackInSlot(i), potion))
                return i;
        }
        return -1;
    }

    public boolean isPotionOnHotBar(Potions potions) {
        for (int i = 0; i < 9; ++i) {
            if (isStackPotion(mc.player.inventory.getStackInSlot(i), potions)) {
                return true;
            }
        }
        return false;
    }

    public boolean isStackPotion(ItemStack stack, Potions potion) {
        if (stack == null)
            return false;

        Item item = stack.getItem();

        if (item == Items.SPLASH_POTION) {
            int id = 5;

            switch (potion) {
                case STRENGTH: {
                    id = 5;
                    break;
                }
                case SPEED: {
                    id = 1;
                    break;
                }
                case FIRERES: {
                    id = 12;
                    break;
                }
            }

            for (PotionEffect effect : PotionUtils.getEffectsFromStack(stack)) {
                if (effect.getPotion() == Potion.getPotionById(id)) {
                    return true;
                }
            }
        }
        return false;
    }

    public enum Potions {
        STRENGTH, SPEED, FIRERES
    }
}
