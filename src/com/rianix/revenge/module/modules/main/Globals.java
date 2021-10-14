/*
 * Decompiled with CFR 0.150.
 */
package com.rianix.revenge.module.modules.main;

import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingBoolean;
import com.rianix.revenge.setting.settings.SettingInteger;

public class Globals
extends Module {
    public float hue;
    public SettingBoolean rainbowPrefix = this.register("RainbowPrefix", false);
    public SettingInteger rainbowSpeed = this.register("RainbowSpeed", 20, 0, 100);

    public Globals() {
        super("Globals", "", 0, Module.Category.MAIN);
    }

    @Override
    public void onUpdate() {
        int colorSpeed = 101 - this.rainbowSpeed.getValue();
        this.hue = (float)(System.currentTimeMillis() % (360L * (long)colorSpeed)) / (360.0f * (float)colorSpeed);
        for (int i = 0; i <= 510; ++i) {
            this.hue += 0.0013071896f;
        }
    }
}

