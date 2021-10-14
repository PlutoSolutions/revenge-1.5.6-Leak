/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.MathHelper
 */
package com.rianix.revenge.gui;

import com.rianix.revenge.gui.AbstractComponent;
import com.rianix.revenge.gui.IComponent;
import com.rianix.revenge.gui.Screen;
import com.rianix.revenge.setting.settings.SettingDouble;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import net.minecraft.util.math.MathHelper;

public class SliderDouble
extends AbstractComponent {
    private final SettingDouble setting;
    private boolean sliding = false;

    public SliderDouble(SettingDouble setting) {
        this.setting = setting;
    }

    @Override
    public void draw() {
        double multiplier = (this.setting.getValue() - this.setting.getMin()) / (this.setting.getMax() - this.setting.getMin());
        IComponent.fillRect(this.rect, new Color(70, 70, 70, 140));
        IComponent.fillRect(new Rectangle(this.rect.x, this.rect.y, (int)((double)this.rect.width * multiplier), this.rect.height), this.clickGui.color.getAWTValue(true));
        IComponent.drawString(String.format("%s: %.2f", this.setting.getName(), this.setting.getValue()), new Point(this.rect.x + 1, this.rect.y + 2), Color.WHITE);
        if (this.sliding) {
            double diff = MathHelper.clamp((double)((Screen.MOUSE.getX() - this.rect.getX()) / (this.rect.getWidth() - 1.0)), (double)0.0, (double)1.0);
            this.setting.setValue((this.setting.getMax() - this.setting.getMin()) * diff + this.setting.getMin());
        }
    }

    @Override
    public void handleButton(int btn) {
        if (btn != -1 && this.rect.contains(Screen.MOUSE)) {
            this.sliding = true;
        } else if (this.sliding) {
            this.sliding = false;
        }
    }
}

