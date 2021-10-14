/*
 * Decompiled with CFR 0.150.
 */
package com.rianix.revenge.gui.color;

import com.rianix.revenge.gui.AbstractComponent;
import com.rianix.revenge.gui.IComponent;
import com.rianix.revenge.gui.Screen;
import com.rianix.revenge.setting.settings.SettingColor;
import java.awt.Color;
import java.awt.Point;

public class RainbowComponent
extends AbstractComponent {
    private final SettingColor setting;

    public RainbowComponent(SettingColor setting) {
        this.setting = setting;
    }

    @Override
    public void draw() {
        IComponent.fillRect(this.rect, this.setting.isRainbow() ? this.clickGui.color.getAWTValue(true) : new Color(255, 255, 255, 52));
        IComponent.drawString("Rainbow", new Point(this.rect.x + 1, this.rect.y + 2), Color.WHITE);
    }

    @Override
    public void handleButton(int btn) {
        if (this.rect.contains(Screen.MOUSE) && btn != -1) {
            this.setting.setRainbow(!this.setting.isRainbow());
        }
    }
}

