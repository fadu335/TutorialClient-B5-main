package com.example.examplemod.Module.RENDER;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.Module.Module;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import yea.bushroot.clickgui.Setting;

import java.util.ArrayList;
import java.util.Objects;

public class AttackEffect extends Module {
    public AttackEffect() {
        super("AttackEffect", Keyboard.KEY_NONE, Category.RENDER);

        ArrayList<String> options = new ArrayList<>();

        options.add("Redstone");
        options.add("Critical");
        options.add("Magic");
        options.add("Cloud");
        options.add("Flame");
        options.add("Spell");
        options.add("SpellWitch");
        options.add("Totem");
        options.add("Happy");
        options.add("Angry");
        options.add("Water");
        options.add("Barrier");
        options.add("Snowball");
        options.add("Heart");
        options.add("Portal");

        ExampleMod.instance.settingsManager.rSetting(new Setting("Mode", this, options, "Mode"));
        ExampleMod.instance.settingsManager.rSetting(new Setting("Amount", this, 10, 1, 15, true));
        ExampleMod.instance.settingsManager.rSetting(new Setting("X2", this, false));
    }

    @SubscribeEvent
    public void onAttack(AttackEntityEvent e) {
        String mode = ExampleMod.instance.settingsManager.getSettingByName(this.name, "Mode").getValString();
        int amount = (int) ExampleMod.instance.settingsManager.getSettingByName(this.name, "Amount").getValDouble();
        boolean x2 = ExampleMod.instance.settingsManager.getSettingByName(this.name, "X2").getValBoolean();

        if (Objects.equals(mode, "Redstone")) {
            for (int i = 0; i < amount; i++) {
                mc.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, e.getTarget().posX, e.getTarget().posY + e.getTarget().height - 0.75, e.getTarget().posZ, 0, 0, 0, Block.getStateId(Blocks.REDSTONE_BLOCK.getDefaultState()));
                if (x2) {
                    mc.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, e.getTarget().posX, e.getTarget().posY + e.getTarget().height - 0.75, e.getTarget().posZ, 0, 0, 0, Block.getStateId(Blocks.REDSTONE_BLOCK.getDefaultState()));
                }
            }
        }

        if (Objects.equals(mode, "Critical")) {
            for (int i = 0; i < amount; i++) {
                mc.player.onCriticalHit(e.getTarget());
                if (x2) {
                    mc.player.onCriticalHit(e.getTarget());
                }
            }
        }

        if (Objects.equals(mode, "Magic")) {
            for (int i = 0; i < amount; i++) {
                mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.CRIT_MAGIC);
                if (x2) {
                    mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.CRIT_MAGIC);
                }
            }
        }

        if (Objects.equals(mode, "Cloud")) {
            for (int i = 0; i < amount; i++) {
                mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.CLOUD);
                if (x2) {
                    mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.CLOUD);
                }
            }
        }

        if (Objects.equals(mode, "Flame")) {
            for (int i = 0; i < amount; i++) {
                mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.FLAME);
                if (x2) {
                    mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.FLAME);
                }
            }
        }

        if (Objects.equals(mode, "Spell")) {
            for (int i = 0; i < amount; i++) {
                mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.SPELL);
                if (x2) {
                    mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.SPELL);
                }
            }
        }

        if (Objects.equals(mode, "SpellWitch")) {
            for (int i = 0; i < amount; i++) {
                mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.SPELL_WITCH);
                if (x2) {
                    mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.SPELL_WITCH);
                }
            }
        }

        if (Objects.equals(mode, "Totem")) {
            for (int i = 0; i < amount; i++) {
                mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.TOTEM);
                if (x2) {
                    mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.TOTEM);
                }
            }
        }

        if (Objects.equals(mode, "Happy")) {
            for (int i = 0; i < amount; i++) {
                mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.VILLAGER_HAPPY);
                if (x2) {
                    mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.VILLAGER_HAPPY);
                }
            }
        }

        if (Objects.equals(mode, "Angry")) {
            for (int i = 0; i < amount; i++) {
                mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.VILLAGER_ANGRY);
                if (x2) {
                    mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.VILLAGER_ANGRY);
                }
            }
        }

        if (Objects.equals(mode, "Water")) {
            for (int i = 0; i < amount; i++) {
                mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.WATER_SPLASH);
                if (x2) {
                    mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.WATER_SPLASH);
                }
            }
        }

        if (Objects.equals(mode, "Barrier")) {
            for (int i = 0; i < amount; i++) {
                mc.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, e.getTarget().posX, e.getTarget().posY + e.getTarget().height - 0.75, e.getTarget().posZ, 0, 0, 0, Block.getStateId(Blocks.BARRIER.getDefaultState()));
                if (x2) {
                    mc.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, e.getTarget().posX, e.getTarget().posY + e.getTarget().height - 0.75, e.getTarget().posZ, 0, 0, 0, Block.getStateId(Blocks.BARRIER.getDefaultState()));
                }
            }
        }

        if (Objects.equals(mode, "Snowball")) {
            for (int i = 0; i < amount; i++) {
                mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.SNOWBALL);
                if (x2) {
                    mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.SNOWBALL);
                }
            }
        }

        if (Objects.equals(mode, "Heart")) {
            for (int i = 0; i < amount; i++) {
                mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.HEART);
                if (x2) {
                    mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.HEART);
                }
            }
        }

        if (Objects.equals(mode, "Portal")) {
            for (int i = 0; i < amount; i++) {
                mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.PORTAL);
                if (x2) {
                    mc.effectRenderer.emitParticleAtEntity(e.getTarget(), EnumParticleTypes.PORTAL);
                }
            }
        }
    }
}
