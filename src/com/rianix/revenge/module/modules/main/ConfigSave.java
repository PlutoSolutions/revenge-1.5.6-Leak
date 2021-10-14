/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 */
package com.rianix.revenge.module.modules.main;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.rianix.revenge.command.Messages;
import com.rianix.revenge.manager.ConfigManager;
import com.rianix.revenge.module.Module;

public class ConfigSave
extends Module {
    public ConfigSave() {
        super("ConfigSave", "", 0, Module.Category.MAIN);
    }

    @Override
    public void onEnable() {
        ConfigManager.save();
        Messages.sendClientMessage((Object)ChatFormatting.GREEN + "Config saved successfully");
        this.toggle();
    }
}

