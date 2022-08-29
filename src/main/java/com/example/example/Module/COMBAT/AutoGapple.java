package com.example.examplemod.Module.COMBAT;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.Module.Module;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import yea.bushroot.clickgui.Setting;

import java.util.ArrayList;
import java.util.Objects;

public class AutoGapple extends Module {
    private boolean eating = false;

    public AutoGapple() {
        super("AutoGapple", Keyboard.KEY_NONE, Category.COMBAT);

        ArrayList<String> options = new ArrayList<>();

        options.add("NONE");
        options.add("Duel");

        ExampleMod.instance.settingsManager.rSetting(new Setting("Mode", this, options, "Mode"));
        ExampleMod.instance.settingsManager.rSetting(new Setting("HP", this, 14, 1, 20, false));
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent e) {
        String mode = ExampleMod.instance.settingsManager.getSettingByName(this.name, "Mode").getValString();
        double hp = ExampleMod.instance.settingsManager.getSettingByName(this.name, "HP").getValDouble();

        if ((double) (mc.player.getHealth() + mc.player.getAbsorptionAmount()) > hp && this.eating) {
            this.eating = false;
            this.stop();
            return;
        }
        if (!this.canEat()) {
            return;
        }
        if (this.isFood(mc.player.getHeldItemOffhand())) {
            if ((double) mc.player.getHealth() <= hp && this.canEat()) {
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
                this.eating = true;
            } else {
                if (Objects.equals(mode, "NONE")) {
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
                    this.eating = false;
                }
            }
        }
        if (!this.canEat()) {
            this.stop();
        }
    }

    public static boolean isNullOrEmptyStack(ItemStack itemStack) {
        return itemStack == null;
    }

    boolean isFood(ItemStack itemStack) {
        return !isNullOrEmptyStack(itemStack) && itemStack.getItem() instanceof ItemAppleGold;
    }

    public boolean canEat() {
        return mc.objectMouseOver == null || !(mc.objectMouseOver.entityHit instanceof EntityVillager);
    }

    void stop() {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
    }
}