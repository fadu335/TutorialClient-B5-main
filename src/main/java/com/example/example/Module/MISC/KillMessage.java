package com.example.examplemod.Module.MISC;

import com.EventTarget;
import com.example.examplemod.Module.COMBAT.KillAura;
import com.example.examplemod.Module.Module;
import com.example.examplemod.Utils.TimerUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.Random;

public class KillMessage extends Module {
    public KillMessage() {
        super("KillMessage", Keyboard.KEY_NONE, Category.MISC);
    }
    @EventTarget
    public void onUpdate(TickEvent.PlayerTickEvent e) {
        if ((KillAura.target.getHealth() <= 0.0f || KillAura.target.isDead) && KillAura.target instanceof EntityPlayer) {
            String[] messages = new String[]{"твой чит хуйня качай TutorialClient"};
            String finalText = messages[new Random().nextInt(messages.length)];
            if (TimerUtil.hasReached(200)) {
                mc.player.sendChatMessage(KillAura.target.getName() + "," + " " + finalText);
                TimerUtil.reset();
                //В тесте мб работает мб нет хз
            }
        }
    }
}
