package com.example.examplemod.Module.RENDER;

import com.example.examplemod.Module.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class NoWeather extends Module {
    public NoWeather() {
        super("NoWeather", Keyboard.KEY_NONE, Category.RENDER);}

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent e) {
        mc.world.setThunderStrength(0);
        mc.world.setRainStrength(0);
    }
}
