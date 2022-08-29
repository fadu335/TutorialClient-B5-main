package com.example.examplemod.Module.COMBAT;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.Module.Module;
import com.example.examplemod.Utils.FriendsUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import yea.bushroot.clickgui.Setting;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class KillAura extends Module {

    public static EntityLivingBase target;

    public KillAura() {
            super("KillAura", Keyboard.KEY_NONE, Category.COMBAT);

            ExampleMod.instance.settingsManager.rSetting(new Setting("Range", this, 3.8, 1, 10, false));
            ExampleMod.instance.settingsManager.rSetting(new Setting("OnlyCriticals", this, true));
            ExampleMod.instance.settingsManager.rSetting(new Setting("CriticalsFallDistance", this, 0.2, 0.2, 0.4, false));
            ExampleMod.instance.settingsManager.rSetting(new Setting("Walls", this, true));
            ExampleMod.instance.settingsManager.rSetting(new Setting("Players", this, true));
            ExampleMod.instance.settingsManager.rSetting(new Setting("Mobs", this, false));
            ExampleMod.instance.settingsManager.rSetting(new Setting("Invisibles", this, false));
            ExampleMod.instance.settingsManager.rSetting(new Setting("IgnoreFriends", this, true));
        }

        public static boolean canSeeEntityAtFov(Entity entityIn, float scope) {
            double diffX = entityIn.posX - mc.player.posX;
            double diffZ = entityIn.posZ - mc.player.posZ;
            float newYaw = (float) (Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0);
            double difference = angleDifference(newYaw, mc.player.rotationYaw);
            return difference <= scope;
        }

        public static double angleDifference(double a, double b) {
            float yaw360 = (float) (Math.abs(a - b) % 360.0);
            if (yaw360 > 180.0f) {
                yaw360 = 360.0f - yaw360;
            }
            return yaw360;
        }

        public static EntityLivingBase getSortEntities() {
            double range = ExampleMod.instance.settingsManager.getSettingByName("KillAura", "Range").getValDouble();

            List<EntityLivingBase> entity = new ArrayList<>();
            for (Entity e : mc.world.loadedEntityList) {
                if (e instanceof EntityLivingBase) {
                    EntityLivingBase player = (EntityLivingBase) e;
                    if (mc.player.getDistance(player) < range && (canAssist(player))) {
                        if (player.getHealth() > 0) {
                            entity.add(player);
                        } else {
                            entity.remove(player);
                        }
                    }
                }
            }

            entity.sort((o1, o2) -> (int) (o1.getHealth() - o2.getHealth()));

            if (entity.isEmpty())
                return null;

            return entity.get(0);
        }

        public static boolean canAssist(EntityLivingBase player) {
            boolean players = ExampleMod.instance.settingsManager.getSettingByName("KillAura", "Players").getValBoolean();
            boolean walls = ExampleMod.instance.settingsManager.getSettingByName("KillAura", "Walls").getValBoolean();
            boolean invisibles = ExampleMod.instance.settingsManager.getSettingByName("KillAura", "Invisibles").getValBoolean();
            boolean mobs = ExampleMod.instance.settingsManager.getSettingByName("KillAura", "Mobs").getValBoolean();

            if (player instanceof EntityPlayer || player instanceof EntityAnimal || player instanceof EntityMob || player instanceof EntityVillager) {
                if (player instanceof EntityPlayer && !players) {
                    return false;
                }
                if (player instanceof EntityAnimal && !mobs) {
                    return false;
                }
                if (player instanceof EntityMob && !mobs) {
                    return false;
                }
                if (player instanceof EntityVillager && !mobs) {
                    return false;
                }
            }
            if (player.isInvisible() && !invisibles) {
                return false;
            }
            if (!canSeeEntityAtFov(player, 90)) {
                return false;
            }
            if (!player.canEntityBeSeen(mc.player)) {
                return walls;
            }
            if (player instanceof EntityArmorStand) {
                return false;
            }
            return player != mc.player;
        }

        @SubscribeEvent
        public void onUpdate(TickEvent.PlayerTickEvent e) {
            double range = ExampleMod.instance.settingsManager.getSettingByName("KillAura", "Range").getValDouble();
            boolean onlycriticals = ExampleMod.instance.settingsManager.getSettingByName(this.name, "OnlyCriticals").getValBoolean();
            double criticalsfalldistance = ExampleMod.instance.settingsManager.getSettingByName(this.name, "CriticalsFallDistance").getValDouble();
            boolean ignorefriends = ExampleMod.instance.settingsManager.getSettingByName(this.name, "IgnoreFriends").getValBoolean();

            EntityLivingBase entity = getSortEntities();
            if (entity != null) {
                for (EntityPlayer playerEntity : mc.world.playerEntities) {
                    if (playerEntity != null && playerEntity != mc.player) {
                        if (entity == playerEntity && FriendsUtil.isFriend((EntityPlayer) entity) && ignorefriends) {}
                        else {
                            if (mc.player.getDistance(entity) <= range) {
                                if (entity != mc.player) {
                                    float[] rots = getRotationsForAssist(entity);
                                    if (canAssist(entity) && entity.getHealth() > 0) {
                                        mc.player.rotationYaw = rots[0];
                                        mc.player.rotationPitch = rots[1];
                                    }
                                }
                            }
                            if (!AutoShieldDestroy.destroy) {
                                if (onlycriticals) {
                                    if (mc.player.motionY <= -criticalsfalldistance && mc.player.posY != mc.player.prevPosY) {
                                        if (mc.player.getCooledAttackStrength(0) == 1) {
                                            mc.playerController.attackEntity(mc.player, entity);
                                            mc.player.swingArm(EnumHand.MAIN_HAND);
                                            mc.player.resetCooldown();
                                        }
                                    }
                                } else {
                                    if (mc.player.getCooledAttackStrength(0) == 1) {
                                        mc.playerController.attackEntity(mc.player, entity);
                                        mc.player.swingArm(EnumHand.MAIN_HAND);
                                        mc.player.resetCooldown();
                                    }
                                }
                            }
                        }
                    } else {
                        if (mc.player.getDistance(entity) <= range) {
                            if (entity != mc.player) {
                                float[] rots = getRotationsForAssist(entity);
                                if (canAssist(entity) && entity.getHealth() > 0) {
                                    mc.player.rotationYaw = rots[0];
                                    mc.player.rotationPitch = rots[1];
                                }
                            }
                        }
                        if (!AutoShieldDestroy.destroy) {
                            if (onlycriticals) {
                                if (mc.player.motionY <= -criticalsfalldistance && mc.player.posY != mc.player.prevPosY) {
                                    if (mc.player.getCooledAttackStrength(0) == 1) {
                                        mc.playerController.attackEntity(mc.player, entity);
                                        mc.player.swingArm(EnumHand.MAIN_HAND);
                                        mc.player.resetCooldown();
                                    }
                                }
                            } else {
                                if (mc.player.getCooledAttackStrength(0) == 1) {
                                    mc.playerController.attackEntity(mc.player, entity);
                                    mc.player.swingArm(EnumHand.MAIN_HAND);
                                    mc.player.resetCooldown();
                                }
                            }
                        }
                    }
                }
            }
        }

        private float[] getRotationsForAssist(EntityLivingBase entityIn) {
            float yaw = UpdateRotation(getFixedRotation(mc.player.rotationYaw + randomizeFloat(-1, 1)), getRotation(entityIn)[0], 1F * 10);
            float pitch = UpdateRotation(getFixedRotation(mc.player.rotationPitch + randomizeFloat(-1, 1)), getRotation(entityIn)[1], 1F * 10);
            return new float[]{yaw, pitch};
        }

        private float[] getRotation(Entity e) {
            float aimPoint;
            aimPoint = 0.5f;

            double xDelta = e.posX + (e.posX - e.prevPosX) * (float) 0.5 - mc.player.posX - mc.player.motionX * (float) 0.5;
            double zDelta = e.posZ + (e.posZ - e.prevPosZ) * (float) 0.5 - mc.player.posZ - mc.player.motionZ * (float) 0.5;
            double diffY = e.posY + e.getEyeHeight() - (mc.player.posY + mc.player.getEyeHeight() + aimPoint);

            double distance = MathHelper.sqrt(xDelta * xDelta + zDelta * zDelta);

            float yaw = (float) ((MathHelper.atan2(zDelta, xDelta) * 180.0D / Math.PI) - 90.0F) + randomizeFloat(-1F, 1F);
            float pitch = ((float) (-(MathHelper.atan2(diffY, distance) * 180.0D / Math.PI))) + randomizeFloat(-1F, 1F);

            yaw = (mc.player.rotationYaw + getFixedRotation(MathHelper.wrapDegrees(yaw - mc.player.rotationYaw)));
            pitch = mc.player.rotationPitch + getFixedRotation(MathHelper.wrapDegrees(pitch - mc.player.rotationPitch));
            pitch = MathHelper.clamp(pitch, -90F, 90F);
            return new float[]{yaw, pitch};
        }

        public static float UpdateRotation(float current, float newValue, float speed) {
            float f = MathHelper.wrapDegrees(newValue - current);
            if (f > speed) {
                f = speed;
            }
            if (f < -speed) {
                f = -speed;
            }
            return current + f;
        }

        public static float getFixedRotation(float rot) {
            return getDeltaMouse(rot) * getGCDValue();
        }

        public static float getGCDValue() {
            return (float) (getGCD() * 0.15);
        }

        public static float getGCD() {
            float f1;
            return (f1 = (float) (mc.gameSettings.mouseSensitivity * 0.6 + 0.2)) * f1 * f1 * 8;
        }

        public static float getDeltaMouse(float delta) {
            return Math.round(delta / getGCDValue());
        }

        public static float randomizeFloat(float min, float max) {
            return (float) (min + (max - min) * Math.random());
        }
    }
