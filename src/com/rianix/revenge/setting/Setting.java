/*
 * Decompiled with CFR 0.150.
 */
package com.rianix.revenge.setting;

import com.rianix.revenge.module.Module;

public class Setting {
    public String name;
    public Module mod;
    public Type type;

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Module getMod() {
        return this.mod;
    }

    public void setMod(Module mod) {
        this.mod = mod;
    }

    public static enum Type {
        BOOLEAN,
        DOUBLE,
        INTEGER,
        MODE,
        COLOR;

    }
}

