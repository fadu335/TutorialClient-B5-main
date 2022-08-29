package com.example.examplemod.Module.RENDER;

import com.example.examplemod.Module.Module;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;

import static net.minecraft.client.gui.Gui.drawRect;

public class World extends Module {
    public World() {
        super("World", Keyboard.KEY_NONE, Category.RENDER);}

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post e) {
        drawRect(0, 0, 900, 500, new Color(0x836D003, true).hashCode());
    }
}
