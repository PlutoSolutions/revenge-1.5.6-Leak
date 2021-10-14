/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.renderer.GlStateManager
 */
package com.rianix.revenge.gui;

import com.rianix.revenge.Revenge;
import com.rianix.revenge.gui.BooleanComponent;
import com.rianix.revenge.gui.IComponent;
import com.rianix.revenge.gui.KeybindComponent;
import com.rianix.revenge.gui.ModeComponent;
import com.rianix.revenge.gui.ModuleComponent;
import com.rianix.revenge.gui.PanelComponent;
import com.rianix.revenge.gui.SliderDouble;
import com.rianix.revenge.gui.SliderInteger;
import com.rianix.revenge.gui.color.ColorComponent;
import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingBoolean;
import com.rianix.revenge.setting.settings.SettingColor;
import com.rianix.revenge.setting.settings.SettingDouble;
import com.rianix.revenge.setting.settings.SettingInteger;
import com.rianix.revenge.setting.settings.SettingMode;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;

public class Screen
extends GuiScreen {
    public static final Point MOUSE = new Point();
    private final List<IComponent> PANELS = new ArrayList<IComponent>();

    public Screen() {
        Point offset = new Point(10, 10);
        for (Module.Category cat : Module.Category.values()) {
            PanelComponent panel = new PanelComponent(new Rectangle(new Point(offset), new Dimension(100, 10)), cat.name());
            Revenge.moduleManager.getModsInCategory(cat).forEach(m -> {
                ModuleComponent module = new ModuleComponent((Module)m);
                Revenge.settingsManager.getSettingsInMod((Module)m).forEach(s -> {
                    switch (s.getType()) {
                        case INTEGER: {
                            module.addChild(new SliderInteger((SettingInteger)s));
                            break;
                        }
                        case BOOLEAN: {
                            module.addChild(new BooleanComponent((SettingBoolean)s));
                            break;
                        }
                        case DOUBLE: {
                            module.addChild(new SliderDouble((SettingDouble)s));
                            break;
                        }
                        case MODE: {
                            module.addChild(new ModeComponent((SettingMode)s));
                            break;
                        }
                        case COLOR: {
                            module.addChild(new ColorComponent((SettingColor)s));
                        }
                    }
                });
                module.addChild(new KeybindComponent((Module)m));
                panel.addChild(module);
            });
            this.PANELS.add(panel);
            offset.translate(120, 0);
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.matrixMode((int)5889);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        GlStateManager.ortho((double)0.0, (double)this.width, (double)this.height, (double)0.0, (double)-3000.0, (double)3000.0);
        GlStateManager.matrixMode((int)5888);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GlStateManager.shadeModel((int)7425);
        MOUSE.setLocation(mouseX, mouseY);
        this.PANELS.forEach(IComponent::draw);
        GlStateManager.shadeModel((int)7424);
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.matrixMode((int)5889);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode((int)5888);
        GlStateManager.popMatrix();
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        MOUSE.setLocation(mouseX, mouseY);
        this.PANELS.forEach(p -> p.handleButton(mouseButton));
    }

    protected void mouseReleased(int mouseX, int mouseY, int state) {
        MOUSE.setLocation(mouseX, mouseY);
        this.PANELS.forEach(p -> p.handleButton(-1));
    }

    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == 1) {
            this.mc.displayGuiScreen(null);
        }
        this.PANELS.forEach(p -> p.keyTyped(keyCode, typedChar));
    }
}

