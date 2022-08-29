package com.example.examplemod.Module.MISC;


import com.example.examplemod.ExampleMod;
import com.example.examplemod.Module.Module;
import com.example.examplemod.Utils.TimerUtil;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import yea.bushroot.clickgui.Setting;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ChestStealer extends Module {
    private final ArrayList<BlockPos> invalidPositions = new ArrayList<>();

    public ChestStealer() {
        super("ChestStealer", Keyboard.KEY_NONE, Category.MISC);

        ExampleMod.instance.settingsManager.rSetting(new Setting("Delay", this, 50, 0, 200, true));
        ExampleMod.instance.settingsManager.rSetting(new Setting("Chest", this, true));
        ExampleMod.instance.settingsManager.rSetting(new Setting("EnderChest", this, false));
        ExampleMod.instance.settingsManager.rSetting(new Setting("Aura", this, false));
        ExampleMod.instance.settingsManager.rSetting(new Setting("AuraRange", this, 4, 1, 10, false));
        ExampleMod.instance.settingsManager.rSetting(new Setting("AuraWalls", this, true));
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        double delay = ExampleMod.instance.settingsManager.getSettingByName(this.name, "Delay").getValDouble();
        boolean aura = ExampleMod.instance.settingsManager.getSettingByName(this.name, "Aura").getValBoolean();
        double aurarange = ExampleMod.instance.settingsManager.getSettingByName(this.name, "AuraRange").getValDouble();
        boolean walls = ExampleMod.instance.settingsManager.getSettingByName(this.name, "Walls").getValBoolean();

        BlockPos pos = getSphere(getPlayerPos(), (float) aurarange, 6, false, true).stream().filter(this::IsValidBlockPos).min(Comparator.comparing(blockPos -> getDistanceOfEntityToBlock(mc.player, blockPos))).orElse(null);

        if (pos != null && aura && mc.currentScreen == null) {
            if (TimerUtil.hasReached(300)) {
                if (!mc.world.isAirBlock(pos.up(1))) {
                    this.invalidPositions.add(pos);
                }
                for (Entity en : mc.world.loadedEntityList) {
                    if (en instanceof EntityEnderCrystal) {
                        if (en.getPosition().getX() == pos.getX() && en.getPosition().getZ() == pos.getZ()) {
                            return;
                        }
                    }
                }
                if (!this.invalidPositions.contains(pos)) {
                    if (mc.world.rayTraceBlocks(new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ), new Vec3d(pos.getX(), pos.getY(), pos.getZ()), false, true, false) != null && !walls) {
                        return;
                    }

                    mc.playerController.processRightClickBlock(mc.player, mc.world, pos, EnumFacing.UP, new Vec3d(pos.getX(), pos.getY(), pos.getZ()), EnumHand.MAIN_HAND);
                    mc.player.swingArm(EnumHand.MAIN_HAND);
                    TimerUtil.reset();
                }
            }
        }

        if (mc.player.openContainer instanceof ContainerChest) {
            final ContainerChest container = (ContainerChest)mc.player.openContainer;

            for (int i = 0; i < container.inventorySlots.size(); ++i) {
                if (container.getLowerChestInventory().getStackInSlot(i).getItem() != Item.getItemById(0) && TimerUtil.hasReached((float) delay)) {
                    mc.playerController.windowClick(container.windowId, i, 0, ClickType.QUICK_MOVE, mc.player);
                    TimerUtil.reset();
                } else if (this.empty(container)) {
                    mc.player.closeScreen();
                }
            }
        }
    }

    public boolean empty(final Container container) {
        boolean voll = true;

        for (int slotAmount = ((container.inventorySlots.size() == 90) ? 54 : 27), i = 0; i < slotAmount; ++i) {
            if (container.getSlot(i).getHasStack()) {
                voll = false;
            }
        }
        return voll;
    }

    private boolean IsValidBlockPos(BlockPos pos) {
        boolean chest = ExampleMod.instance.settingsManager.getSettingByName(this.name, "Chest").getValBoolean();
        boolean enderchest = ExampleMod.instance.settingsManager.getSettingByName(this.name, "EnderChest").getValBoolean();

        IBlockState state = mc.world.getBlockState(pos);
        if ((state.getBlock() instanceof BlockChest && chest) || (state.getBlock() instanceof BlockEnderChest && enderchest))
            return mc.world.getBlockState(pos.up()).getBlock() == Blocks.AIR;
        return false;
    }

    public static List<BlockPos> getSphere(BlockPos blockPos, float offset, int range, boolean hollow, boolean sphere) {
        ArrayList<BlockPos> blockPosList = new ArrayList<>();
        int blockPosX = blockPos.getX();
        int blockPosY = blockPos.getY();
        int blockPosZ = blockPos.getZ();
        float x = blockPosX - offset;
        while (x <= blockPosX + offset) {
            float z = blockPosZ - offset;
            while (z <= blockPosZ + offset) {
                float y = sphere ? blockPosY - offset : blockPosY;
                do {
                    float f = sphere ? blockPosY + offset : blockPosY + range;
                    if (!(y < f))
                        break;

                    float dist = (blockPosX - x) * (blockPosX - x) + (blockPosZ - z) * (blockPosZ - z) + (sphere ? (blockPosY - y) * (blockPosY - y) : 0);
                    if (!(!(dist < offset * offset) || hollow && dist < (offset - 1 * (offset - 1)))) {
                        BlockPos pos = new BlockPos(x, y, z);
                        blockPosList.add(pos);
                    }
                    ++y;
                } while (true);
                ++z;
            }
            ++x;
        }
        return blockPosList;
    }

    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
    }

    public static double getDistance(double x, double y, double z, double x1, double y1, double z1) {
        double posX = x - x1;
        double posY = y - y1;
        double posZ = z - z1;
        return Math.sqrt(posX * posX + posY * posY + posZ * posZ);
    }

    public static double getDistanceOfEntityToBlock(Entity entity, BlockPos pos) {
        return getDistance(entity.posX, entity.posY, entity.posZ, pos.getX(), pos.getY(), pos.getZ());
    }
}
