/*
 * Decompiled with CFR 0.150.
 */
package com.rianix.revenge.gui;

import com.rianix.revenge.gui.AbstractComponent;
import com.rianix.revenge.gui.ContainerComponent;
import com.rianix.revenge.gui.IComponent;
import com.rianix.revenge.gui.Screen;
import com.rianix.revenge.module.Module;
import java.awt.Color;
import java.awt.Point;

public class ModuleComponent
extends AbstractComponent {
    private final Module module;
    private final ContainerComponent container = new ContainerComponent(2, 2, 4);
    private boolean opened = false;

    public ModuleComponent(Module module) {
        this.module = module;
    }

    @Override
    public void draw() {
        IComponent.fillRect(this.rect, this.module.isToggled() ? this.clickGui.color.getAWTValue(true) : new Color(255, 255, 255, 52));
        IComponent.drawString(this.module.getName(), new Point(this.rect.x + 1, this.rect.y + 2), Color.WHITE);
        if (this.opened) {
            this.container.setRect(this.rect);
            this.container.draw();
        }
    }

    @Override
    public void handleButton(int btn) {
        if (this.rect.contains(Screen.MOUSE)) {
            if (btn == 0) {
                this.module.toggle();
            } else if (btn == 1) {
                boolean bl = this.opened = !this.opened;
            }
        }
        if (this.opened) {
            this.container.handleButton(btn);
        }
    }

    @Override
    public void keyTyped(int key, char ch) {
        if (this.opened) {
            this.container.keyTyped(key, ch);
        }
    }

    @Override
    public int getAbsoluteHeight() {
        return this.rect.height + (this.opened ? this.container.getAbsoluteHeight() : 0);
    }

    @Override
    public void addChild(IComponent component) {
        this.container.addChild(component);
    }
}

