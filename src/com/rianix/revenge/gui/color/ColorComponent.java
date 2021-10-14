/*
 * Decompiled with CFR 0.150.
 */
package com.rianix.revenge.gui.color;

import com.rianix.revenge.gui.AbstractComponent;
import com.rianix.revenge.gui.ContainerComponent;
import com.rianix.revenge.gui.IComponent;
import com.rianix.revenge.gui.Screen;
import com.rianix.revenge.gui.color.AlphaSlider;
import com.rianix.revenge.gui.color.ColorPickerComponent;
import com.rianix.revenge.gui.color.HueSlider;
import com.rianix.revenge.gui.color.RainbowComponent;
import com.rianix.revenge.setting.settings.SettingColor;
import java.awt.Color;

public class ColorComponent
extends AbstractComponent {
    private final SettingColor setting;
    private final ContainerComponent container = new ContainerComponent(2, 2, 4){

        @Override
        public void draw() {
            this.children.forEach(IComponent::draw);
        }
    };
    private boolean opened = false;

    public ColorComponent(SettingColor setting) {
        this.setting = setting;
        this.container.addChild(new ColorPickerComponent(setting));
        this.container.addChild(new HueSlider(setting));
        this.container.addChild(new AlphaSlider(setting));
        this.container.addChild(new RainbowComponent(setting));
    }

    @Override
    public void draw() {
        IComponent.fillRect(this.rect, this.setting.getAWTValue(true));
        IComponent.drawString(this.setting.getName(), this.rect.getLocation(), Color.WHITE);
        if (this.opened) {
            this.container.setRect(this.rect);
            this.container.draw();
        }
    }

    @Override
    public void handleButton(int btn) {
        if (this.rect.contains(Screen.MOUSE) && btn != -1) {
            this.opened = !this.opened;
            return;
        }
        if (this.opened) {
            this.container.setRect(this.rect);
            this.container.handleButton(btn);
        }
    }

    @Override
    public int getAbsoluteHeight() {
        return this.rect.height + (this.opened ? this.container.getAbsoluteHeight() + 2 : 0);
    }
}

