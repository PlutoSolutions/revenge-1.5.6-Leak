/*
 * Decompiled with CFR 0.150.
 */
package com.rianix.revenge.module.modules.main;

import com.rianix.revenge.Revenge;
import com.rianix.revenge.module.Module;

public class Font
extends Module {
    public Font() {
        super("Font", "", 0, Module.Category.MAIN);
    }

    @Override
    public void onEnable() {
        Revenge.fontManager.setCustomFont(true);
    }

    @Override
    public void onDisable() {
        Revenge.fontManager.setCustomFont(false);
    }
}

