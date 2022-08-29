package com.example.examplemod.Module.RENDER;

import com.example.examplemod.Module.Module;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class Trails extends Module {
    ArrayList<Point> points = new ArrayList<>();

    public Trails() {
        super("Trails", Keyboard.KEY_NONE, Category.RENDER);}

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent e) {
        if (this.toggled) {
            points.removeIf(p -> p.age >= 100);

            float x = (float) (mc.player.lastTickPosX + mc.player.posX - mc.player.lastTickPosX);
            float y = (float) (mc.player.lastTickPosY + mc.player.posY - mc.player.lastTickPosY);
            float z = (float) (mc.player.lastTickPosZ + mc.player.posZ - mc.player.lastTickPosZ);

            points.add(new Point(x, y, z));

            GL11.glPushMatrix();
            GL11.glDisable(GL_ALPHA_TEST);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_LINE_SMOOTH);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(GL11.GL_CULL_FACE);
            for (final Point t : points) {
                if (points.indexOf(t) >= points.size() - 1) continue;

                Point temp = points.get(points.indexOf(t) + 1);
                glBegin(GL_QUAD_STRIP);
                final double x2 = t.x - mc.getRenderManager().viewerPosX;
                final double y2 = t.y - mc.getRenderManager().viewerPosY;
                final double z2 = t.z - mc.getRenderManager().viewerPosZ;

                final double x1 = temp.x - mc.getRenderManager().viewerPosX;
                final double y1 = temp.y - mc.getRenderManager().viewerPosY;
                final double z1 = temp.z - mc.getRenderManager().viewerPosZ;

                glVertex3d(x2, y2 + mc.player.height - 0.1, z2);
                GlStateManager.color(0 / 255F, 165 / 255F, 0 / 255F, 155 / 255F);
                glVertex3d(x2, y2 + 0.2, z2);
                glVertex3d(x1, y1 + mc.player.height - 0.1, z1);
                glVertex3d(x1, y1 + 0.2, z1);
                glEnd();
                ++t.age;
            }
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL_ALPHA_TEST);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_LINE_SMOOTH);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glPopMatrix();
        }
    }

    @Override
    public void onDisable() {
        points.clear();
    }

    static class Point {
        public final float x, y, z;

        public float age = 0;

        public Point(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}