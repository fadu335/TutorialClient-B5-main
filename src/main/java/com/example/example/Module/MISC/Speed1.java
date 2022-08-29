package com.example.examplemod.Module.MISC;

import com.EventTarget;
import com.example.examplemod.Module.Module;
import com.example.examplemod.Utils.MovementUtils;
import com.example.examplemod.Utils.TimerUtil;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import static io.netty.internal.tcnative.SSLContext.getOptions;
//пока что не работает
public class Speed1 extends Module {
    public Speed1() {
        super("Speed1", Keyboard.KEY_NONE, Category.MISC);
    }
    @EventTarget
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        int eIndex = -1;
        int elytraCount = 0;
        for (int i = 0; i < 45; i++) {
            if (mc.player.inventory.getStackInSlot(i).getItem() == Items.ELYTRA && eIndex == -1) {
                eIndex = i;
    }
            if (mc.player.inventory.getStackInSlot(i).getItem() == Items.ELYTRA) {
                elytraCount++;


            }
            if (mc.gameSettings.keyBindJump.isKeyDown()) {
                return;
            }
            if (mc.player.ticksExisted % 20 == 0) {
                mc.playerController.windowClick(0, eIndex, 0, ClickType.PICKUP, mc.player);
                mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, mc.player);
                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
                mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, mc.player);
                mc.playerController.windowClick(0, eIndex, 0, ClickType.PICKUP, mc.player);
        }
            if (mc.gameSettings.keyBindJump.isKeyDown()) {
                return;
    }
            mc.player.motionZ *= 1.8D;
            mc.player.motionX *= 1.8D;
            MovementUtils.strafe();
            if (elytraCount == 0 && eIndex == -1) {
                if (mc.player.getHeldItemOffhand().getItem() != Items.ELYTRA) {
                    toggle();
                }
                }
            }
    }
    }
