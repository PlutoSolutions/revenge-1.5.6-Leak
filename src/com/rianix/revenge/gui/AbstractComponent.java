/*
 * Decompiled with CFR 0.150.
 */
package com.rianix.revenge.gui;

import com.rianix.revenge.Revenge;
import com.rianix.revenge.gui.IComponent;
import com.rianix.revenge.module.modules.main.ClickGuiModule;
import java.awt.Rectangle;

public abstract class AbstractComponent
implements IComponent {
    protected final Rectangle rect = new Rectangle();
    protected final ClickGuiModule clickGui = Revenge.moduleManager.getModuleByClass(ClickGuiModule.class);

    public AbstractComponent() {
    }

    public AbstractComponent(Rectangle rect) {
        this.rect.setRect(rect);
    }

    @Override
    public void handleButton(int btn) {
    }

    @Override
    public void keyTyped(int key, char ch) {
    }

    @Override
    public int getAbsoluteHeight() {
        return this.rect.height;
    }

    @Override
    public void addChild(IComponent component) {
    }

    @Override
    public Rectangle getRect() {
        return this.rect;
    }

    @Override
    public void setRect(Rectangle rect1) {
        this.rect.setRect(rect1);
    }
}

