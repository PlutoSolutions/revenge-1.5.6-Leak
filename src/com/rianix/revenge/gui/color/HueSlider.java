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

public class HueSlider
extends AbstractComponent {
    private final SettingColor setting;
    private boolean sliding = false;

    public HueSlider(SettingColor setting) {
        this.setting = setting;
    }

    @Override
    public void draw() {
        ColorHolder ch = this.setting.getValue(false);
        IComponent.drawImage(this.rect, new ResourceLocation("minecraft:hue.png"));
        IComponent.drawLine(new Point((int)((float)this.rect.x + (float)this.rect.width * ch.getHue()), this.rect.y), new Point((int)((float)this.rect.x + (float)this.rect.width * ch.getHue()), this.rect.y + this.rect.height), 2, Color.BLACK);
        if (this.sliding) {
            double diff = MathHelper.clamp((double)((double)(Screen.MOUSE.x - this.rect.x) / ((double)this.rect.width - 1.0)), (double)0.0, (double)1.0);
            this.setting.setValue(new ColorHolder((float)diff, ch.getSaturation(), ch.getBrightness(), ch.getAlpha()));
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

