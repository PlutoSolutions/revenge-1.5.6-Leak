/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.settings.KeyBinding
 */
package com.rianix.revenge.module.modules.movement;

import com.rianix.revenge.module.Module;
import net.minecraft.client.settings.KeyBinding;

public class AutoWalk
extends Module {
    public AutoWalk() {
        super("AutoWalk", "Presses the move forward key.", 0, Module.Category.MOVEMENT);
    }

    @Override
    public void onUpdate() {
        KeyBinding.setKeyBindState((int)AutoWalk.mc.gameSettings.keyBindForward.getKeyCode(), (boolean)true);
    }
}

