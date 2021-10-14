/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiNewChat
 */
package com.rianix.revenge.mixin.mixins;

import com.rianix.revenge.Revenge;
import com.rianix.revenge.module.modules.main.Globals;
import java.awt.Color;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiNewChat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={GuiNewChat.class})
public class MixinGuiNewChat
extends Gui {
    @Redirect(method={"drawChat"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/FontRenderer;drawStringWithShadow(Ljava/lang/String;FFI)I"))
    private int drawStringWithShadow(FontRenderer fontRenderer, String text, float x, float y, int color) {
        if (text.contains("\u00a7+")) {
            float colorSpeed = 101 - Revenge.moduleManager.getModuleByClass(Globals.class).rainbowSpeed.getValue();
            Revenge.fontManager.drawRainbowString(text, x, y, Color.HSBtoRGB(Revenge.moduleManager.getModuleByClass(Globals.class).hue, 1.0f, 1.0f), 100.0f, true);
        } else {
            Revenge.fontManager.drawStringWithShadow(text, x, y, color);
        }
        return 0;
    }
}

