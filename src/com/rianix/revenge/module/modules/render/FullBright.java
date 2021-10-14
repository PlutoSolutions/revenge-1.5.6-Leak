/*
 * Decompiled with CFR 0.150.
 */
package com.rianix.revenge.module.modules.render;

import com.rianix.revenge.module.Module;

public class FullBright
extends Module {
    public FullBright() {
        super("FullBright", "Turns up brightness to see in the dark.", 0, Module.Category.RENDER);
    }

    @Override
    public void onUpdate() {
        FullBright.mc.gameSettings.gammaSetting = 100.0f;
    }

    @Override
    public void onDisable() {
        FullBright.mc.gameSettings.gammaSetting = 1.0f;
    }
}

