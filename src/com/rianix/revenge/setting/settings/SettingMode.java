/*
 * Decompiled with CFR 0.150.
 */
package com.rianix.revenge.setting.settings;

import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.Setting;
import java.util.ArrayList;

public class SettingMode
extends Setting {
    public String value;
    public ArrayList<String> values;
    public int index;

    public SettingMode(String name, Module mod, ArrayList<String> values, String value) {
        this.name = name;
        this.mod = mod;
        this.values = values;
        this.value = value;
        this.type = Setting.Type.MODE;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ArrayList<String> getValues() {
        return this.values;
    }

    public void setValues(ArrayList<String> values) {
        this.values = values;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void increment() {
        ++this.index;
        if (this.index > this.values.size() - 1) {
            this.index = 0;
        }
        if (this.index < 0) {
            this.index = this.values.size() - 1;
        }
        this.value = this.values.get(this.index);
    }

    public void decrement() {
        --this.index;
        if (this.index < 0) {
            this.index = this.values.size() - 1;
        }
        this.value = this.values.get(this.index);
    }
}

