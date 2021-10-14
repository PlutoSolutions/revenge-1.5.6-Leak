/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.ResourceLocation
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class AlphaSlider
extends AbstractComponent {
    private final SettingColor setting;
    private boolean sliding = false;

    public AlphaSlider(SettingColor setting) {
        this.setting = setting;
    }

    @Override
    public void draw() {
        Color color = this.setting.getAWTValue(true);
        double multiplier = (double)color.getAlpha() / 255.0;
        IComponent.drawImage(this.rect, new ResourceLocation("minecraft:alpha.png"));
        IComponent.fillRect(this.rect, new Color(0, 0, 0, 0), new Color(color.getRed(), color.getGreen(), color.getBlue(), 255), new Color(color.getRed(), color.getGreen(), color.getBlue(), 255), new Color(0, 0, 0, 0));
        IComponent.drawLine(new Point((int)((double)this.rect.x + (double)this.rect.width * multiplier), this.rect.y), new Point((int)((double)this.rect.x + (double)this.rect.width * multiplier), this.rect.y + this.rect.height), 2, Color.BLACK);
        if (this.sliding) {
            double diff = MathHelper.clamp((double)((Screen.MOUSE.getX() - this.rect.getX()) / (this.rect.getWidth() - 1.0)), (double)0.0, (double)1.0);
            ColorHolder ch = this.setting.getValue(false);
            this.setting.setValue(new ColorHolder(ch.getHue(), ch.getSaturation(), ch.getBrightness(), (int)(255.0 * diff)));
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

