/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 */
package com.rianix.revenge.module.modules.main;

import com.rianix.revenge.Revenge;
import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingColor;
import java.awt.Color;
import net.minecraft.client.gui.GuiScreen;

public class ClickGuiModule
extends Module {
    public final SettingColor color = this.register("Color", Color.GREEN, false);

    public ClickGuiModule() {
        super("ClickGui", "Displays Gui screen.", 54, Module.Category.MAIN);
    }

    @Override
    public void onEnable() {
        mc.displayGuiScreen((GuiScreen)Revenge.instance.clickGui);
        this.toggle();
    }
}

