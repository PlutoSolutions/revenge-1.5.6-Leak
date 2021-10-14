/*
 * Decompiled with CFR 0.150.
 */
package com.rianix.revenge.gui;

import com.rianix.revenge.gui.AbstractComponent;
import com.rianix.revenge.gui.ContainerComponent;
import com.rianix.revenge.gui.IComponent;
import com.rianix.revenge.gui.Screen;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

public class PanelComponent
extends AbstractComponent {
    private final String name;
    private final ContainerComponent cont = new ContainerComponent(2, 2, 4);
    private final Point attachPoint = new Point();
    private boolean dragging = false;

    public PanelComponent(Rectangle rect, String name) {
        super(rect);
        this.name = name;
    }

    @Override
    public void draw() {
        IComponent.fillRect(this.rect, this.clickGui.color.getAWTValue(true));
        IComponent.drawString(this.name, new Point(this.rect.x + 1, this.rect.y + 2), Color.WHITE);
        this.cont.setRect(this.rect);
        this.cont.draw();
        if (this.dragging) {
            this.rect.translate(Screen.MOUSE.x - this.attachPoint.x, Screen.MOUSE.y - this.attachPoint.y);
            this.attachPoint.setLocation(Screen.MOUSE);
        }
    }

    @Override
    public void handleButton(int btn) {
        if (this.rect.contains(Screen.MOUSE) && btn != -1) {
            this.dragging = true;
            this.attachPoint.setLocation(Screen.MOUSE);
        } else if (this.dragging) {
            this.dragging = false;
        }
        this.cont.handleButton(btn);
    }

    @Override
    public void keyTyped(int key, char ch) {
        this.cont.keyTyped(key, ch);
    }

    @Override
    public int getAbsoluteHeight() {
        return this.rect.height + this.cont.getAbsoluteHeight();
    }

    @Override
    public void addChild(IComponent component) {
        this.cont.addChild(component);
    }
}

