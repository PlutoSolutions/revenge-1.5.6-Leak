/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package com.rianix.revenge.gui;

import com.rianix.revenge.gui.AbstractComponent;
import com.rianix.revenge.gui.IComponent;
import com.rianix.revenge.gui.Screen;
import com.rianix.revenge.module.Module;
import java.awt.Color;
import java.awt.Point;
import org.lwjgl.input.Keyboard;

public class KeybindComponent
extends AbstractComponent {
    private final Module module;
    private boolean listening = false;

    public KeybindComponent(Module module) {
        this.module = module;
    }

    @Override
    public void draw() {
        IComponent.fillRect(this.rect, this.listening ? this.clickGui.color.getAWTValue(true) : new Color(255, 255, 255, 52));
        IComponent.drawString("Bind: " + (this.listening ? "..." : Keyboard.getKeyName((int)this.module.getKey())), new Point(this.rect.x + 1, this.rect.y + 2), Color.WHITE);
    }

    @Override
    public void handleButton(int btn) {
        if (this.rect.contains(Screen.MOUSE) && btn != -1) {
            this.listening = !this.listening;
        }
    }

    @Override
    public void keyTyped(int key, char ch) {
        if (this.listening) {
            if (key == 14 || key == 211) {
                this.module.setKey(0);
                return;
            }
            this.module.setKey(key);
            this.listening = false;
        }
    }
}

