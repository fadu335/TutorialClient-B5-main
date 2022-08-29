package com.example.examplemod.Module.MOVEMENT;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.Module.Module;
import com.example.examplemod.Utils.MovementUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import yea.bushroot.clickgui.Setting;

import java.util.ArrayList;
import java.util.Objects;

public class Jesus extends Module {
    private int waterTicks;

    public Jesus() {
        super("Jesus", Keyboard.KEY_NONE, Category.MOVEMENT);
        ArrayList<String> options = new ArrayList<>();

        options.add("MatrixNew");
    }
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {





         {



        BlockPos blockPos = new BlockPos(Minecraft.getMinecraft().player.posX, Minecraft.getMinecraft().player.posY - 0.1, Minecraft.getMinecraft().player.posZ);
        Block block = Minecraft.getMinecraft().world.getBlockState(blockPos).getBlock();


        BlockPos blockPos2;
        Block block2;
            blockPos2 = new BlockPos(mc.player.posX, mc.player.posY - 0.02, mc.player.posZ);
            block2 = mc.world.getBlockState(blockPos2).getBlock();
            if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY - 0.5, mc.player.posZ)).getBlock() == Blocks.WATER && mc.player.onGround) {
                mc.player.motionY = 0.2;
            }

            if (mc.player.isInWater()) {
                mc.player.setVelocity(0.0, 0.0, 0.0);
            }

            if (block2 instanceof BlockLiquid && mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY + 0.0311, mc.player.posZ)).getBlock() instanceof BlockAir && !mc.player.onGround) {
                mc.player.motionY = 0.0;
                mc.player.fallDistance = 1.0F;
                if (this.waterTicks == 1) {
                    if (MovementUtils.isMoving()) {
                        MovementUtils.setSpeed((float) 0.6);
                    }

                    this.waterTicks = 0;
                } else {
                    this.waterTicks = 1;
                }
            }


        }
    }}


