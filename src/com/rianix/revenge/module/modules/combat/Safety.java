/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 */
package com.rianix.revenge.module.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.rianix.revenge.module.Module;
import com.rianix.revenge.util.PlayerUtil;

public class Safety
extends Module {
    boolean safety = false;

    public Safety() {
        super("Safety", "", 0, Module.Category.COMBAT);
    }

    @Override
    public void onUpdate() {
        this.safety = Safety.mc.world.getBlockState(Safety.mc.player.getPosition()).getMaterial().isSolid() || PlayerUtil.isInHole();
    }

    @Override
    public String getDisplayInfo() {
        if (this.safety) {
            return (Object)ChatFormatting.GREEN + "SAFE";
        }
        return (Object)ChatFormatting.RED + "UNSAFE";
    }
}

