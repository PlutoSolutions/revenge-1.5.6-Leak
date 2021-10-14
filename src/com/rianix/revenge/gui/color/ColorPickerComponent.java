/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.MathHelper
 */
package com.rianix.revenge.gui.color;

import com.rianix.revenge.gui.AbstractComponent;
import com.rianix.revenge.gui.IComponent;
import com.rianix.revenge.gui.Screen;
import com.rianix.revenge.gui.color.ColorHolder;
import com.rianix.revenge.setting.settings.SettingColor;
import java.awt.Color;
import java.awt.Point;
import net.minecraft.util.math.MathHelper;

public class ColorPickerComponent
extends AbstractComponent {
    private final SettingColor setting;
    private final Point cursor = new Point();
    private boolean dragging = false;

    public ColorPickerComponent(SettingColor setting) {
        this.setting = setting;
    }

    @Override
    public void draw() {
        this.rect.setSize(this.rect.width, this.rect.width);
        ColorHolder ch = this.setting.getValue(false);
        if (this.dragging) {
            float s = MathHelper.clamp((float)((float)(Screen.MOUSE.x - this.rect.x) / ((float)this.rect.width - 1.0f)), (float)0.0f, (float)1.0f);
            float b = MathHelper.clamp((float)(1.0f + (float)(this.rect.y - Screen.MOUSE.y) / ((float)this.rect.height - 1.0f)), (float)0.0f, (float)1.0f);
            this.setting.setValue(new ColorHolder(ch.getHue(), s, b, ch.getAlpha()));
        }
        this.cursor.setLocation((float)this.rect.width * ch.getSaturation(), (float)this.rect.height - (float)this.rect.height * ch.getBrightness());
        Color c1 = Color.getHSBColor(ch.getHue(), 0.0f, 1.0f);
        Color c2 = Color.getHSBColor(ch.getHue(), 1.0f, 1.0f);
        Color c3 = new Color(0, 0, 0, 0);
        Color c4 = new Color(0, 0, 0);
        Color marker = Color.getHSBColor(ch.getHue() - 0.3f, ch.getSaturation() - 0.1f, ch.getBrightness() - 0.1f);
        IComponent.fillRect(this.rect, c1, c2, c2, c1);
        IComponent.fillRect(this.rect, c3, c3, c4, c4);
        IComponent.drawLine(new Point(this.rect.x + this.cursor.x, this.rect.y + this.cursor.y - 3), new Point(this.rect.x + this.cursor.x, this.rect.y + this.cursor.y + 3), 1, marker);
        IComponent.drawLine(new Point(this.rect.x + this.cursor.x - 3, this.rect.y + this.cursor.y), new Point(this.rect.x + this.cursor.x + 3, this.rect.y + this.cursor.y), 1, marker);
    }

    @Override
    public void handleButton(int btn) {
        this.rect.setSize(this.rect.width, this.rect.width);
        if (this.rect.contains(Screen.MOUSE) && btn == 0) {
            this.dragging = true;
        } else if (btn == -1 && this.dragging) {
            this.dragging = false;
        }
    }

    @Override
    public int getAbsoluteHeight() {
        return this.rect.width;
    }
}

