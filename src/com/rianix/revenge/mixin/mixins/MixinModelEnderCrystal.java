/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.ModelEnderCrystal
 *  net.minecraft.client.renderer.GlStateManager
 */
package com.rianix.revenge.mixin.mixins;

import com.rianix.revenge.Revenge;
import com.rianix.revenge.module.modules.render.CrystalScale;
import net.minecraft.client.model.ModelEnderCrystal;
import net.minecraft.client.renderer.GlStateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={ModelEnderCrystal.class})
public abstract class MixinModelEnderCrystal {
    @Redirect(method={"render"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/renderer/GlStateManager;scale(FFF)V"))
    public void render(float x, float y, float z) {
        if (Revenge.moduleManager.getModuleByClass(CrystalScale.class).isToggled()) {
            GlStateManager.scale((double)((double)x + Revenge.moduleManager.getModuleByClass(CrystalScale.class).x.getValue()), (double)((double)y + Revenge.moduleManager.getModuleByClass(CrystalScale.class).y.getValue()), (double)((double)z + Revenge.moduleManager.getModuleByClass(CrystalScale.class).z.getValue()));
        } else {
            GlStateManager.scale((float)x, (float)y, (float)z);
        }
    }
}

