/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiMainMenu
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.util.text.TextFormatting
 */
package com.rianix.revenge.mixin.mixins;

import com.rianix.revenge.Revenge;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.text.TextFormatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={GuiMainMenu.class})
public class MixinGuiMainMenu
extends GuiScreen {
    @Inject(method={"drawScreen"}, at={@At(value="TAIL")}, cancellable=true)
    public void drawText(CallbackInfo ci) {
        Revenge.fontManager.drawStringWithShadow((Object)TextFormatting.WHITE + "revenge " + (Object)TextFormatting.GRAY + "1.5.6", 1.0f, 1.0f, -51);
    }
}

