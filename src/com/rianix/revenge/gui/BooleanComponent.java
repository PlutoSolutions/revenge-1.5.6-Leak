/*
 * Decompiled with CFR 0.150.
 */
package com.rianix.revenge.gui;

import com.rianix.revenge.gui.AbstractComponent;
import com.rianix.revenge.gui.IComponent;
import com.rianix.revenge.gui.Screen;
import com.rianix.revenge.setting.settings.SettingBoolean;
import java.awt.Color;
import java.awt.Point;

public class BooleanComponent
extends AbstractComponent {
    private final SettingBoolean setting;

    public BooleanComponent(SettingBoolean setting) {
        this.setting = setting;
    }

    @Override
    public void draw() {
        IComponent.fillRect(this.rect, this.setting.getValue() ? this.clickGui.color.getAWTValue(true) : new Color(255, 255, 255, 52));
        IComponent.drawString(this.setting.getName(), new Point(this.rect.x + 1, this.rect.y + 2), Color.WHITE);
    }

    @Override
    public void handleButton(int btn) {
        if (this.rect.contains(Screen.MOUSE) && btn != -1) {
            this.setting.setValue(!this.setting.getValue());
        }
    }
}

