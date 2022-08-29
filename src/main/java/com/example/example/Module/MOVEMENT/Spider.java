package com.example.examplemod.Module.MOVEMENT;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.Module.Module;
import com.example.examplemod.Utils.TimerUtil;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import yea.bushroot.clickgui.Setting;

import java.util.ArrayList;
import java.util.Objects;

public class Spider extends Module {
    public Spider() {
        super("Spider", Keyboard.KEY_NONE, Category.MOVEMENT);

        ArrayList<String> options = new ArrayList<>();

        options.add("Default");
        options.add("Jump");
        options.add("SunRise");

        ExampleMod.instance.settingsManager.rSetting(new Setting("Mode", this, options, "Mode"));
        ExampleMod.instance.settingsManager.rSetting(new Setting("Motion", this, 0.3, 0.1, 2, false));
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        String mode = ExampleMod.instance.settingsManager.getSettingByName(this.name, "Mode").getValString();
        double motion = ExampleMod.instance.settingsManager.getSettingByName(this.name, "Motion").getValDouble();

        if (Objects.equals(mode, "Default")) {
            if (mc.player.collidedHorizontally) {
                mc.player.motionY = motion;
            }
        }

        if (Objects.equals(mode, "Jump")) {
            if (mc.player.collidedHorizontally && !mc.player.collidedVertically) {
                if (mc.player.moveStrafing == 0 && mc.player.moveForward > 0) {
                    if (mc.player.motionY < -0.225) {
                        mc.player.jump();
                        mc.player.setSprinting(false);
                        mc.player.onGround = true;
                        if (mc.player.motionY > 1) {
                            mc.player.motionY += 1.01;
                        } else {
                            mc.player.motionY /= 1.01;
                        }
                    }
                }
            }
        }

        if (Objects.equals(mode, "SunRise")) {
            if (mc.playerController.getCurrentGameType() == GameType.ADVENTURE && TimerUtil.hasReached(1000)) {
                TimerUtil.reset();
            }
            if (mc.player.collidedHorizontally) {
                if (mc.playerController.getCurrentGameType() == GameType.ADVENTURE) {
                } else {
                    int block = -1;
                    for (int i = 0; i < 9; i++) {
                        ItemStack s = mc.player.inventory.getStackInSlot(i);
                        if (s.getItem() instanceof ItemBlock) {
                            block = i;
                            break;
                        }
                    }
                    if (block == -1 && TimerUtil.hasReached(1000)) {
                        TimerUtil.reset();
                        return;
                    }
                    if (TimerUtil.hasReached(1F * 55)) {
                        try {
                            if (block != -1 && mc.objectMouseOver != null && mc.objectMouseOver.hitVec != null && mc.objectMouseOver.getBlockPos() != null && mc.objectMouseOver.sideHit != null) {
                                mc.player.connection.sendPacket(new CPacketHeldItemChange(block));
                                float prevPitch = mc.player.rotationPitch;
                                mc.player.rotationPitch = -60;
                                mc.entityRenderer.getMouseOver(1);
                                Vec3d facing = mc.objectMouseOver.hitVec;
                                BlockPos stack = mc.objectMouseOver.getBlockPos();
                                float f = (float) (facing.x - (double) stack.getX());
                                float f1 = (float) (facing.y - (double) stack.getY());
                                float f2 = (float) (facing.z - (double) stack.getZ());
                                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
                                if (mc.world.getBlockState(new BlockPos(mc.player).add(0, 2, 0)).getBlock() == Blocks.AIR) {
                                    mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(stack, mc.objectMouseOver.sideHit, EnumHand.MAIN_HAND, f, f1, f2));
                                } else {
                                    mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, stack, mc.objectMouseOver.sideHit));
                                    mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, stack, mc.objectMouseOver.sideHit));
                                }
                                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                                mc.player.rotationPitch = prevPitch;
                                mc.entityRenderer.getMouseOver(1);
                                mc.player.connection.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem));
                                mc.player.onGround = true;
                                mc.player.collidedVertically = true;
                                mc.player.collidedHorizontally = true;
                                mc.player.isAirBorne = true;
                                mc.player.jump();
                                TimerUtil.reset();
                            }
                        } catch (Exception event) {
                            event.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
