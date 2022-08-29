package com.example.examplemod.Module.RENDER;

import com.example.examplemod.Module.Module;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class WallHack extends Module {
    public WallHack() {
        super("WallHack", Keyboard.KEY_NONE, Category.RENDER);}

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post e) {
        GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
        GL11.glPolygonOffset(1.0f, -1100000.0f);
    }
}
