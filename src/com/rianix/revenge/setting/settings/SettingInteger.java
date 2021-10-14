/*
 * Decompiled with CFR 0.150.
 */
package com.rianix.revenge.setting.settings;

import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.Setting;

public class SettingInteger
extends Setting {
    public int value;
    public int min;
    public int max;

    public SettingInteger(String name, Module mod, int value, int min, int max) {
        this.name = name;
        this.mod = mod;
        this.value = value;
        this.min = min;
        this.max = max;
        this.type = Setting.Type.INTEGER;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getMin() {
        return this.min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return this.max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}

