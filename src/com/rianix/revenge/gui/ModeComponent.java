/*
 * Decompiled with CFR 0.150.
 */
package com.rianix.revenge.gui;

import com.rianix.revenge.gui.AbstractComponent;
import com.rianix.revenge.gui.IComponent;
import com.rianix.revenge.gui.Screen;
import com.rianix.revenge.setting.settings.SettingMode;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

public class ModeComponent
extends AbstractComponent {
    private final SettingMode setting;

    public ModeComponent(SettingMode setting) {
        this.setting = setting;
    }

    @Override
    public void draw() {
        IComponent.fillRect(this.rect, new Color(20, 20, 20, 120));
        IComponent.drawString(this.setting.getName() + ": " + this.setting.getValue(), new Point(this.rect.x + 1, this.rect.y + 2), Color.WHITE);
    }

    @Override
    public void handleButton(int btn) {
        if (this.rect.contains(Screen.MOUSE)) {
            if (btn == 0) {
                this.setting.increment();
            } else if (btn == 1) {
                this.setting.decrement();
            }
        }
    }

    @Override
    public int getAbsoluteHeight() {
        return this.rect.height + 0;
    }

    private static final class QuadPicker
    extends AbstractComponent {
        public QuadPicker(Rectangle rect) {
            super(rect);
        }

        @Override
        public void draw() {
        }
    }
}

