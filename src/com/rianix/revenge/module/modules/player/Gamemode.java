/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.GameType
 */
package com.rianix.revenge.module.modules.player;

import com.rianix.revenge.module.Module;
import net.minecraft.world.GameType;

public class Gamemode
extends Module {
    public Gamemode() {
        super("Gamemode", "Fake gamemode 1.", 0, Module.Category.PLAYER);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        Gamemode.mc.playerController.setGameType(GameType.CREATIVE);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        Gamemode.mc.playerController.setGameType(GameType.SURVIVAL);
    }
}

