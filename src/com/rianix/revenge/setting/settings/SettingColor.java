/*
 * Decompiled with CFR 0.150.
 */
package com.rianix.revenge.setting.settings;

import com.rianix.revenge.gui.color.ColorHolder;
import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.Setting;
import java.awt.Color;

public class SettingColor
extends Setting {
    private ColorHolder value;
    private boolean rainbow;

    public SettingColor(String name, Module mod, Color value, boolean rainbow) {
        this.name = name;
        this.mod = mod;
        this.value = new ColorHolder(value);
        this.type = Setting.Type.COLOR;
        this.rainbow = rainbow;
    }

    public Color getAWTValue(boolean rainbowCheck) {
        if (rainbowCheck && this.rainbow) {
            Color color = Color.getHSBColor((float)(((double)System.currentTimeMillis() + (double)this.value.getHue() * 100.0 * 200.0) % 7200.0 / 7200.0), 0.5f, 1.0f);
            return new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)this.value.getAlpha());
        }
        return this.value.toAWTColor();
    }

    public ColorHolder getValue(boolean rainbowCheck) {
        if (rainbowCheck && this.rainbow) {
            return new ColorHolder(((float)System.currentTimeMillis() + this.value.getHue() * 200.0f) % 7200.0f / 7200.0f, 0.5f, 1.0f, this.value.getAlpha());
        }
        return this.value;
    }

    public void setAWTValue(Color value) {
        this.value.importFromAWTColor(value);
    }

    public void setValue(ColorHolder value) {
        this.value = value;
    }

    public boolean isRainbow() {
        return this.rainbow;
    }

    public void setRainbow(boolean rainbow) {
        this.rainbow = rainbow;
    }
}

