package com.example.examplemod.Utils;

import net.minecraft.util.math.MathHelper;

import static com.example.examplemod.Module.Module.mc;

public class MovementUtils {
    public static final double WALK_SPEED = 0.221;
    public static void setMotion(double speed) {
        double forward = mc.player.movementInput.moveForward;
        double strafe = mc.player.movementInput.moveStrafe;
        float yaw = mc.player.rotationYaw;
        if (forward == 0 && strafe == 0) {
            mc.player.motionX = 0;
            mc.player.motionZ = 0;
        } else {
            if (forward != 0) {
                if (strafe > 0) {
                    yaw += (float) (forward > 0 ? -45 : 45);
                } else if (strafe < 0) {
                    yaw += (float) (forward > 0 ? 45 : -45);
                }
                strafe = 0;
                if (forward > 0) {
                    forward = 1;
                } else if (forward < 0) {
                    forward = -1;
                }
            }
            double sin = MathHelper.sin((float) Math.toRadians(yaw + 90));
            double cos = MathHelper.cos((float) Math.toRadians(yaw + 90));
            mc.player.motionX = forward * speed * cos + strafe * speed * sin;
            mc.player.motionZ = forward * speed * sin - strafe * speed * cos;
        }
    }
    public static void setSpeed(double speed) {
        float f = mc.player.movementInput.moveForward;
        float f1 = mc.player.movementInput.moveStrafe;
        float f2 = mc.player.rotationYaw;

        if (f == 0.0F && f1 == 0.0F) {
            mc.player.motionX = 0.0D;
            mc.player.motionZ = 0.0D;
        } else if (f != 0.0F) {
            if (f1 >= 1.0F) {
                f2 += (f > 0.0F ? -35 : 35);
                f1 = 0.0F;
            } else if (f1 <= -1.0F) {
                f2 += (f > 0.0F ? 35 : -35);
                f1 = 0.0F;
            }
            if (f > 0.0F) {
                f = 1.0F;
        } else if (f < 0.0F);
            f = -1.0F;
        }
    }

    private double f2;
    double d0 = Math.cos(Math.toRadians(f2 + 90.0F));
    double d1 = Math.sin(Math.toRadians(f2 + 90.0F));

    public static boolean isMoving() {
        return mc.player.movementInput.moveStrafe != 0.0 || mc.player.movementInput.moveForward != 0.0;

    }



    public static float getSpeed() {
        return (float) Math.sqrt(mc.player.motionX * mc.player.motionX + mc.player.motionZ * mc.player.motionZ);

}
    public static void strafe() {
        if (mc.gameSettings.keyBindBack.isKeyDown()) {
            return;
}
    }

}


