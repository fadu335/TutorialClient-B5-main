package com.example.examplemod.Module.CLIENT;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.Module.Module;
import org.lwjgl.input.Keyboard;
import yea.bushroot.clickgui.Setting;

import java.util.ArrayList;

public class ClickGUI extends Module {
    public ClickGUI() {
        super("ClickGUI", Keyboard.KEY_NONE, Category.CLIENT);
        ArrayList<String> girl = new ArrayList<>();

        girl.add("Girl1");
        girl.add("Girl2");
        girl.add("Girl3");
        girl.add("Girl4");
        girl.add("Girl5");
        girl.add("Girl6");
        girl.add("Girl7");
        girl.add("Girl8");
        girl.add("Girl9");
        girl.add("Girl10");
        girl.add("Girl11");
        girl.add("Girl12");
        girl.add("Girl13");
        girl.add("Girl14");
        girl.add("Girl15");
        girl.add("Girl16");
        girl.add("Girl17");
        girl.add("Girl18");
        girl.add("Girl19");
        girl.add("Girl20");
        girl.add("Girl21");

        ArrayList<String> color = new ArrayList<>();

        color.add("Client");
        color.add("Custom");
        color.add("Rainbow");

        ExampleMod.instance.settingsManager.rSetting(new Setting("Girl", this, girl, "Girl"));
        ExampleMod.instance.settingsManager.rSetting(new Setting("Color", this, color, "Color"));
        ExampleMod.instance.settingsManager.rSetting(new Setting("BackGround", this, true));
        ExampleMod.instance.settingsManager.rSetting(new Setting("Blur", this, true));
        ExampleMod.instance.settingsManager.rSetting(new Setting("Girl", this, true));
        ExampleMod.instance.settingsManager.rSetting(new Setting("GirlWidth", this, 530, 0, 600, true));
        ExampleMod.instance.settingsManager.rSetting(new Setting("GirlHeight", this, 100, 0, 250, true));
        ExampleMod.instance.settingsManager.rSetting(new Setting("Rainbow", this, false));

        ExampleMod.instance.settingsManager.rSetting(new Setting("Rainbow", this, false));
    }
}
