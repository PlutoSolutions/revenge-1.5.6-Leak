/*
 * Decompiled with CFR 0.150.
 */
package com.rianix.revenge.module.modules.render;

import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingBoolean;
import com.rianix.revenge.setting.settings.SettingColor;
import com.rianix.revenge.setting.settings.SettingMode;
import java.awt.Color;
import java.util.ArrayList;

public class PlayerChams
extends Module {
    ArrayList<String> renderMode = new ArrayList();
    public SettingMode mode = this.register("Mode", this.renderMode, "Colored");
    public SettingBoolean xqz = this.register("XQZ", false);
    public SettingColor color = this.register("Color", Color.GREEN, false);
    public SettingColor friendsColor = this.register("FriendsColor", Color.PINK, false);

    public PlayerChams() {
        super("PlayerChams", "", 0, Module.Category.RENDER);
        this.renderMode.add("Colored");
        this.renderMode.add("Textured");
        this.renderMode.add("WallHack");
    }

    @Override
    public String getDisplayInfo() {
        if (this.mode.getValue().equals("Colored")) {
            return "Colored";
        }
        if (this.mode.getValue().equals("Textured")) {
            return "Textured";
        }
        if (this.mode.getValue().equals("WallHack")) {
            return "WallHack";
        }
        return null;
    }
}

