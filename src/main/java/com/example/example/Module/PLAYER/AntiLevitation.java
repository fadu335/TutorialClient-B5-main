package com.example.examplemod.Module.PLAYER;

import com.example.examplemod.Module.Module;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.Objects;

public class AntiLevitation extends Module {

    public AntiLevitation() {
        super("AntiLevitation", Keyboard.KEY_NONE, Category.PLAYER);
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent e) {
        mc.player.removePotionEffect(Objects.requireNonNull(Potion.getPotionById(25)));
    }
}
