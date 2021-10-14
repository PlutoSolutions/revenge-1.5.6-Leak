/*
 * Decompiled with CFR 0.150.
 */
package com.rianix.revenge.gui;

import com.rianix.revenge.gui.IComponent;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class ContainerComponent
implements IComponent {
    private final Rectangle rect = new Rectangle();
    private final int offsetX;
    private final int offsetY;
    private final int componentGap;
    protected final List<IComponent> children = new ArrayList<IComponent>();

    public ContainerComponent(int offsetX, int offsetY, int componentGap) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.componentGap = componentGap;
    }

    @Override
    public void draw() {
        IComponent.fillRect(this.rect, new Color(255, 255, 255, 52));
        this.children.forEach(IComponent::draw);
    }

    @Override
    public void handleButton(int btn) {
        this.children.forEach(c -> c.handleButton(btn));
    }

    @Override
    public void keyTyped(int key, char ch) {
        this.children.forEach(c -> c.keyTyped(key, ch));
    }

    @Override
    public int getAbsoluteHeight() {
        return this.children.size() > 0 ? this.children.stream().mapToInt(c -> c.getAbsoluteHeight() + this.componentGap).sum() - this.componentGap : 0;
    }

    @Override
    public void addChild(IComponent component) {
        this.children.add(component);
    }

    @Override
    public Rectangle getRect() {
        return this.rect;
    }

    @Override
    public void setRect(Rectangle rect1) {
        int offset = this.offsetY;
        for (IComponent comp : this.children) {
            Rectangle rect2 = new Rectangle(rect1.x + this.offsetX, rect1.y + rect1.height + offset, rect1.width - this.offsetX * 2, rect1.height);
            comp.setRect(rect2);
            offset += comp.getAbsoluteHeight() + this.componentGap;
        }
        this.rect.setRect(rect1.x, rect1.y + rect1.height, rect1.width, offset - this.componentGap + this.offsetY);
    }
}

